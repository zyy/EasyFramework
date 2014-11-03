/**
 * Copyright (c) 2011-2014, yycoder 692895299@qq.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.easy.i18n;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import cn.easy.core.Const;

public class I18N {

	private static String baseName;
	private static Locale defaultLocale = Locale.getDefault();
	private static int i18nMaxAgeOfCookie = Const.DEFAULT_I18N_MAX_AGE_OF_COOKIE;
	private static NullResourceBundle NULL_RESOURCE_BUNDLE = new NullResourceBundle();
	private static ConcurrentHashMap<String, ResourceBundle> bundlesMap = new ConcurrentHashMap<String, ResourceBundle>();

	private static volatile I18N i18N;

	private I18N() {
	}

	public static I18N getInstance() {
		if (i18N == null)
			synchronized (I18N.class) {
				if (i18N == null)
					i18N = new I18N();
			}
		return i18N;
	}

	private static ResourceBundle getResourceBundle(Locale locale) {
		String resourceBundleKey = getresourceBundleKey(locale);
		ResourceBundle resourceBundle = bundlesMap.get(resourceBundleKey);
		if (resourceBundle == null) {
			try {
				resourceBundle = ResourceBundle.getBundle(baseName, locale);
				bundlesMap.put(resourceBundleKey, resourceBundle);
			} catch (MissingResourceException e) {
				resourceBundle = NULL_RESOURCE_BUNDLE;
			}
		}
		return resourceBundle;
	}

	/**
	 * 将来只改这里就可以了: resourceBundleKey的生成规则
	 */
	private static String getresourceBundleKey(Locale locale) {
		return baseName + locale.toString();
	}

	public static String getText(String key) {
		return getResourceBundle(defaultLocale).getString(key);
	}

	public static String getText(String key, String defaultValue) {
		String result = getResourceBundle(defaultLocale).getString(key);
		return result != null ? result : defaultValue;
	}

	public static Locale getDefaultLocale() {
		return defaultLocale;
	}

	public static int getI18nMaxAgeOfCookie() {
		return i18nMaxAgeOfCookie;
	}

	public static String getText(String key, Locale locale) {
		return getResourceBundle(locale).getString(key);
	}

	public static String getText(String key, String defaultValue, Locale locale) {
		String result = getResourceBundle(locale).getString(key);
		return result != null ? result : defaultValue;
	}

	public static Locale localeFromString(String localeStr) {
		if ((localeStr == null) || (localeStr.trim().length() == 0)
				|| ("_".equals(localeStr))) {
			return defaultLocale;
		}

		int index = localeStr.indexOf('_');
		if (index < 0) {
			return new Locale(localeStr);
		}

		String language = localeStr.substring(0, index);
		if (index == localeStr.length()) {
			return new Locale(language);
		}

		localeStr = localeStr.substring(index + 1);
		index = localeStr.indexOf('_');
		if (index < 0) {
			return new Locale(language, localeStr);
		}

		String country = localeStr.substring(0, index);
		if (index == localeStr.length()) {
			return new Locale(language, country);
		}

		localeStr = localeStr.substring(index + 1);
		return new Locale(language, country, localeStr);
	}

	public static void init(String baseName, Locale defaultLocale,
			Integer i18nMaxAgeOfCookie) {
		I18N.baseName = baseName;
		if (defaultLocale != null)
			I18N.defaultLocale = defaultLocale;
		if (i18nMaxAgeOfCookie != null)
			I18N.i18nMaxAgeOfCookie = i18nMaxAgeOfCookie;
	}

	private static class NullResourceBundle extends ResourceBundle {

		@Override
		protected Object handleGetObject(String key) {
			return null;
		}

		@Override
		public Enumeration<String> getKeys() {
			return null;
		}
	}

}

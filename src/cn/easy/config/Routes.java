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
package cn.easy.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.easy.core.Controller;
import cn.easy.util.StringUtil;

public abstract class Routes {

	private final Map<String, Class<? extends Controller>> map = new HashMap<String, Class<? extends Controller>>();
	private final Map<String, String> viewPathMap = new HashMap<String, String>();
	private static String baseViewPath;

	public abstract void config();

	public Routes add(Routes routes) {
		if (routes != null) {
			routes.config();
			map.putAll(routes.map);
			viewPathMap.putAll(routes.viewPathMap);
		}
		return this;
	}

	public Routes add(String controllerKey,
			Class<? extends Controller> controllerClass, String viewPath) {
		if (StringUtil.isEmpty(controllerKey))
			throw new IllegalArgumentException(
					"The controllerKey can not be empty");
		if (map.containsKey(controllerKey))
			throw new IllegalArgumentException(
					"The contorllerKey already exist" + controllerKey);
		if (!controllerKey.startsWith("/"))
			controllerKey = "/" + controllerKey;
		map.put(controllerKey, controllerClass);

		if (StringUtil.isEmpty(viewPath))
			viewPath = controllerKey;
		if (!viewPath.startsWith("/"))
			viewPath = "/" + viewPath;
		if (!viewPath.endsWith("/"))
			viewPath = viewPath + "/";
		if (baseViewPath != null)
			viewPath = baseViewPath + viewPath;
		
		viewPathMap.put(controllerKey, viewPath);
		return this;
	}
	
	public Routes add(String controllerkey, Class<? extends Controller> controllerClass) {
		return add(controllerkey, controllerClass, controllerkey);
	}
	
	public static void setBaseViewPath(String baseViewPath) {
		if (StringUtil.isEmpty(baseViewPath))
			throw new IllegalArgumentException(
					"The baseViewPath can not be empty");

		if (!baseViewPath.startsWith("/"))
			baseViewPath = "/" + baseViewPath;

		if (baseViewPath.endsWith("/"))
			baseViewPath = baseViewPath.substring(0, baseViewPath.length() - 1);

		Routes.baseViewPath = baseViewPath;
	}

	public String getViewPath(String controllerKey) {
		return viewPathMap.get(controllerKey);
	}

	public Set<Entry<String, Class<? extends Controller>>> getEntrySet() {
		return map.entrySet();
	}
}

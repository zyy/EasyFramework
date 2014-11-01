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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cn.easy.util.PathUtil;
import cn.easy.util.StringUtil;

public abstract class EasyConfig {
	public abstract void configConstants(Constants constants);

	public abstract void configHandlers(Handlers handlers);

	public abstract void configInterceptors(Interceptors interceptors);

	public abstract void configPlugins(Plugins plugins);

	public abstract void configRoutes(Routes routes);

	public void afterEasyFrameworkStart() {
	}

	public void beforeEasyFrameworkStop() {
	}

	private Properties properties;
	
	public Properties loadPropertyFile(String filePath) {
		if (StringUtil.isEmpty(filePath))
			throw new IllegalArgumentException("Properties file path can not be empty");
		String fileFullPath;
		if (filePath.startsWith(File.separator)) 
			fileFullPath = PathUtil.getWebRootPath() + File.separator + "WEB-INF" + filePath;
		else 
			fileFullPath = PathUtil.getWebRootPath() + File.separator + "WEB-INF" + File.separator + filePath;
		
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(fileFullPath));
			Properties p = new Properties();
			p.load(inputStream);
			properties = p;
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Properties file not found" + fileFullPath);
		} catch (IOException e) {
			throw new IllegalArgumentException("Properties file can not be loading: " + fileFullPath);
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		if (properties == null)
			throw new RuntimeException("Properties file loading failed: " + fileFullPath);
		return properties;
	}
	
	public String getProperty(String key) {
		checkPropertyLoading();
		return properties.getProperty(key);
	}
	
	public String getProperty(String key, String defaultValue) {
		checkPropertyLoading();
		return properties.getProperty(key, defaultValue);
	}

	public Integer getPropertyToInt(String key) {
		checkPropertyLoading();
		String result = properties.getProperty(key);
		if (result != null)
			return Integer.parseInt(result);
		return null;
	}
	
	public Integer getPropertyToInt(String key, Integer defaultValue) {
		Integer result = getPropertyToInt(key);
		return result != null ? result : defaultValue;
	}
	
	public Boolean getPropertyToBoolean(String key) {
		checkPropertyLoading();
		String result = properties.getProperty(key);
		if (result != null) {
			if (result.trim().equalsIgnoreCase("true"))
				return true;
			else if (result.trim().equalsIgnoreCase("false"))
				return false;
		}
		return null;
	}
	
	public Boolean getPropertyToBoolean(String key, boolean defaultValue) {
		Boolean result = getPropertyToBoolean(key);
		return result != null ? result : defaultValue;
	}
	
	private void checkPropertyLoading() {
		if (properties == null)
			throw new RuntimeException("You must load properties file by invoking loadPropertyFile(String) method in configConstant(Constants) method before.");
	}

}

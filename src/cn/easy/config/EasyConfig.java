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

import java.util.Properties;

public abstract class EasyConfig {
	public abstract void configConstants(Constants constants);

	public abstract void configHandlers(Handlers handlers);

	public abstract void configInterceptors();

	public abstract void configPlugins();

	public abstract void configRoutes();
	
	public void afterEasyFrameworkStart(){}
	
	public void beforeEasyFrameworkStop(){}
	
	private Properties properties;
}

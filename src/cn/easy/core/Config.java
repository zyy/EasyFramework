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
package cn.easy.core;

import java.util.List;

import cn.easy.config.Constants;
import cn.easy.config.EasyConfig;
import cn.easy.config.Handlers;
import cn.easy.config.Interceptors;
import cn.easy.config.Plugins;
import cn.easy.config.Routes;
import cn.easy.log.Logger;
import cn.easy.plugin.IPlugin;
import cn.easy.plugin.activerecord.ActiveRecordPlugin;

public class Config {

	private static final Constants constants = new Constants();
	private static final Handlers handlers = new Handlers();
	private static final Plugins plugins = new Plugins();
	private static final Interceptors interceptors = new Interceptors();
	private static final Routes routes = new Routes() {

		@Override
		public void config() {

		}
	};
	private static Logger log;

	public static Constants getConstants() {
		return constants;
	}

	public static Handlers getHandlers() {
		return handlers;
	}

	public static Plugins getPlugins() {
		return plugins;
	}

	public static Interceptors getInterceptors() {
		return interceptors;
	}

	public static Routes getRoutes() {
		return routes;
	}

	public static void configEasyFramework(EasyConfig easyConfig) {
		easyConfig.configConstant(constants);
		initLoggerFactory();
		easyConfig.configHandler(handlers);
		easyConfig.configInterceptor(interceptors);
		easyConfig.configPlugin(plugins);
		startPlugins();
		easyConfig.configRoute(routes);
	}

	private static void startPlugins() {
		List<IPlugin> pluginList = plugins.getPluginList();
		if (pluginList != null) {
			for (IPlugin plugin : pluginList) {
				try {
					if (plugin instanceof ActiveRecordPlugin) {
						ActiveRecordPlugin arp = (ActiveRecordPlugin) plugin;
						if (arp.getDevMode() == null)
							arp.setDevMode(constants.getDevMode());
					}

					boolean isSuccess = plugin.start();
					if (!isSuccess) {
						String message = "Plugin start eroor "
								+ plugin.getClass().getName();
						log.error(message);
						throw new RuntimeException(message);
					}
				} catch (Exception e) {
					String message = "Plugin start error: "
							+ plugin.getClass().getName() + ". \n"
							+ e.getMessage();
					log.error(message, e);
					throw new RuntimeException(message, e);
				}
			}
		}
	}

	private static void initLoggerFactory() {
		Logger.init();
		log = Logger.getLogger(Config.class);
		EasyFilter.initLogger();
	}
}

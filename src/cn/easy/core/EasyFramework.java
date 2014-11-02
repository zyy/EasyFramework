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

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import cn.easy.config.Constants;
import cn.easy.config.EasyConfig;
import cn.easy.handler.Handler;
import cn.easy.handler.HandlerFactory;
import cn.easy.i18n.I18N;
import cn.easy.plugin.IPlugin;
import cn.easy.render.RenderFactory;
import cn.easy.server.IServer;
import cn.easy.server.ServerFactory;
import cn.easy.token.ITokenCache;
import cn.easy.token.TokenManager;
import cn.easy.upload.OreillyCos;
import cn.easy.util.PathUtil;

public class EasyFramework {
	private static IServer server;
	private static EasyFramework easyFramework = new EasyFramework();
	private ServletContext servletContext;
	private String contextPath = "";
	private Constants constants;
	private Handler handler;
	private ActionMapping actionMapping;

	private EasyFramework() {
	}

	Handler getHandler() {
		return handler;
	}

	public static EasyFramework getInstance() {
		return easyFramework;
	}

	public static void main(String[] args) {
		if (args == null || args.length <= 0) {
			server = ServerFactory.getServer();
			server.start();
		}
	}

	public boolean init(EasyConfig easyConfig, ServletContext servletContext) {
		this.servletContext = servletContext;
		this.contextPath = servletContext.getContextPath();

		initPathUtil();

		Config.configEasyFramework(easyConfig);
		constants = Config.getConstants();

		initActionMapping();
		initHandler();
		initRender();
		initOreillyCos();
		initI18n();
		initTokenManager();

		return true;
	}

	private void initTokenManager() {
		ITokenCache tokenCache = constants.getTokenCache();
		if (tokenCache != null)
			TokenManager.init(tokenCache);
	}

	private void initI18n() {
		String i18nResourceBaseName = constants.getI18nResourceBaseName();
		if (i18nResourceBaseName != null) {
			I18N.init(i18nResourceBaseName, constants.getI18nDefaultLocale(),
					constants.getI18nMaxAgeOfCookie());
		}
	}

	private void initOreillyCos() {
		Constants ct = constants;
		if (OreillyCos.isMultipartSupported()) {
			String uploadedFileSaveDirectory = ct
					.getUploadedFileSaveDirectory();
			if (uploadedFileSaveDirectory == null
					|| "".equals(uploadedFileSaveDirectory.trim())) {
				uploadedFileSaveDirectory = PathUtil.getWebRootPath()
						+ File.separator + "upload" + File.separator;
				ct.setUploadedFileSaveDirectory(uploadedFileSaveDirectory);

				/*
				 * File file = new File(uploadedFileSaveDirectory); if
				 * (!file.exists()) file.mkdirs();
				 */
			}
			OreillyCos.init(uploadedFileSaveDirectory, ct.getMaxPostSize(),
					ct.getEncoding());
		}
	}

	private void initRender() {
		RenderFactory renderFactory = RenderFactory.getInstance();
		renderFactory.init(constants, servletContext);
	}

	private void initHandler() {
		Handler actionHandler = new ActionHandler(actionMapping, constants);
		handler = HandlerFactory.getHandler(Config.getHandlers()
				.getHandlerList(), actionHandler);
	}

	private void initActionMapping() {
		actionMapping = new ActionMapping(Config.getRoutes(),
				Config.getInterceptors());
		actionMapping.buildActionMapping();
	}

	private void initPathUtil() {
		PathUtil.setWebRootPath(servletContext.getRealPath("/"));
	}

	public void stopPlugins() {
		List<IPlugin> plugins = Config.getPlugins().getPluginList();
		if (plugins != null) {
			for (int i = plugins.size() - 1; i >= 0; i--) { // stop plugins
				boolean success = false;
				try {
					success = plugins.get(i).stop();
				} catch (Exception e) {
					success = false;
					e.printStackTrace();
				}
				if (!success) {
					System.err.println("Plugin stop error: "
							+ plugins.get(i).getClass().getName());
				}
			}
		}
	}
	
	public ServletContext getServletContext() {
		return this.servletContext;
	}
	
	public static void start() {
		server = ServerFactory.getServer();
		server.start();
	}
	
	public static void start(String webAppDir, int port, String context, int scanIntervalSeconds) {
		server = ServerFactory.getServer(webAppDir, port, context, scanIntervalSeconds);
		server.start();
	}
	
	public static void stop() {
		server.stop();
	}
	
	public List<String> getAllActionKeys() {
		return actionMapping.getAllActionKeys();
	}
	
	public Constants getConstants() {
		return Config.getConstants();
	}
	
	public Action getAction(String url, String[] urlPara) {
		return actionMapping.getAction(url, urlPara);
	}
	
	public String getContextPath() {
		return contextPath;
	}

}

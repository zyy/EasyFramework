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

import javax.servlet.ServletContext;

import cn.easy.config.Constants;
import cn.easy.config.EasyConfig;
import cn.easy.server.IServer;
import cn.easy.server.ServerFactory;
import cn.easy.util.PathUtil;

public class EasyFramework {
	private static IServer server;
	private static EasyFramework easyFramework = new EasyFramework();
	private ServletContext servletContext;
	private String contextPath = "";
	private Constants constants;

	private EasyFramework() {
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
		
	}

	private void initI18n() {
		
	}

	private void initOreillyCos() {
		
	}

	private void initRender() {
		
	}

	private void initHandler() {
		
	}

	private void initActionMapping() {
		
	}

	private void initPathUtil() {
		PathUtil.setWebRootPath(servletContext.getRealPath("/"));
	}

}

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
package cn.easy.server;

import cn.easy.util.PathUtil;

public class ServerFactory {
	private static final int DEFAULT_SERVER_PORT = 80;
	private static final int DEFAULT_SERVER_SCANINTERNALSECONDS = 5;

	public static IServer getServer(int port, String webAppDir,
			String contextPath, int scanInternalSeconds) {
		return new JettyServer(port, webAppDir, contextPath,
				scanInternalSeconds);
	}

	public static IServer getServer() {
		return getServer(DEFAULT_SERVER_PORT, getWebAppDir(), "/",
				DEFAULT_SERVER_SCANINTERNALSECONDS);
	}

	private static String getWebAppDir() {
		String rootClassPath = PathUtil.getRootClassPath();
		String[] temp = null;
		if (rootClassPath.indexOf("\\WEB-INF\\") != -1)
			temp = rootClassPath.split("\\\\");
		else if (rootClassPath.indexOf("/WEB-INF/") != -1)
			temp = rootClassPath.split("/");
		else 
			throw new RuntimeException("WEB-INF derectory not found");
		return temp[temp.length - 3];
	}

	public static IServer getServer(String webAppDir, int port, String context,
			int scanIntervalSeconds) {
		// TODO Auto-generated method stub
		return null;
	}
}

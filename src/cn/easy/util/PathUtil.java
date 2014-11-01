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
package cn.easy.util;

import java.io.File;
import java.net.URISyntaxException;

public class PathUtil {

	private static String webRootPath;
	private static String rootClassPath;

	public static String getRootClassPath() {
		if (rootClassPath == null)
			try {
				String path = PathUtil.class.getClassLoader().getResource("")
						.toURI().getPath();
				rootClassPath = new File(path).getAbsolutePath();
			} catch (URISyntaxException e) {
				String path = PathUtil.class.getClassLoader().getResource("")
						.getPath();
				rootClassPath = new File(path).getAbsolutePath();
			}
		return rootClassPath;
	}

	public static String getWebRootPath() {
		if (webRootPath == null)
			webRootPath = detectWebRootPath();;
		return webRootPath;
	}
	
	public static void setWebRootPath(String webRootPath) {
		if (webRootPath == null)
			return ;
		
		if (webRootPath.endsWith(File.separator))
			webRootPath = webRootPath.substring(0, webRootPath.length() - 1);
		PathUtil.webRootPath = webRootPath;
	}
	
	private static String detectWebRootPath() {
		try {
			String path = PathUtil.class.getResource("/").toURI().getPath();
			return new File(path).getParentFile().getParentFile().getCanonicalPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

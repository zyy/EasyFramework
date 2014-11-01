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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import cn.easy.config.EasyConfig;
import cn.easy.log.Logger;

public class EasyFilter implements Filter {
	private EasyConfig easyConfig;
	private final static EasyFramework easyFramework = EasyFramework
			.getInstance();
	private static Logger log;

	public void destroy() {
		System.out.println("-->Call EasyFilter destroy method");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("-->EasyFilter get new request to filter");
	}

	public void init(FilterConfig config) throws ServletException {
		System.out.println("-->Call EasyFilter init method");
		createEasyConfig(config.getInitParameter("classConfig"));

		if (easyFramework.init(easyConfig, config.getServletContext()) == false)
			throw new RuntimeException("EasyFramework init error");
	}

	private void createEasyConfig(String classConfig) {
		if (classConfig == null)
			throw new RuntimeException(
					"Please set classConfig parameter of EasyFilter in web.xml");

		try {
			Object tmp = Class.forName(classConfig).newInstance();
			if (tmp instanceof EasyConfig)
				easyConfig = (EasyConfig) tmp;
			else
				throw new RuntimeException("Can not create instance of class "
						+ classConfig + "Please check the config in web.xml");
		} catch (InstantiationException e) {
			throw new RuntimeException("Can not create instance of class "
					+ classConfig, e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Can not create instance of class "
					+ classConfig, e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Class not found " + classConfig, e);
		}
	}

	public static void initLogger() {
		log = Logger.getLogger(EasyFilter.class);
	}

}

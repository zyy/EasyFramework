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
package cn.easy.render;

import java.io.IOException;

import cn.easy.core.EasyFramework;

public class RedirectRender extends Render {

	private static final long serialVersionUID = -3400356394631345397L;
	private String url;
	private boolean withQueryString;
	private static final String contextPath = getContxtPath();

	static String getContxtPath() {
		String cp = EasyFramework.getInstance().getContextPath();
		return ("".equals(cp) || "/".equals(cp)) ? null : cp;
	}

	public RedirectRender(String url) {
		this.url = url;
		this.withQueryString = false;
	}

	public RedirectRender(String url, boolean withQueryString) {
		this.url = url;
		this.withQueryString = withQueryString;
	}

	public void render() {
		if (contextPath != null && url.indexOf("://") == -1)
			url = contextPath + url;

		if (withQueryString) {
			String queryString = request.getQueryString();
			if (queryString != null)
				if (url.indexOf("?") == -1)
					url = url + "?" + queryString;
				else
					url = url + "&" + queryString;
		}

		try {
			response.sendRedirect(url); // always 302
		} catch (IOException e) {
			throw new RenderException(e);
		}
	}

}

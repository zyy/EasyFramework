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

import java.io.File;

import javax.servlet.ServletContext;

import cn.easy.config.Constants;

public class RenderFactory {
	
	private static RenderFactory renderFactory = new RenderFactory();

	public static RenderFactory getInstance() {
		return renderFactory;
	}
	
	public static void setMainRenderFactory(IMainRenderFactory mainRenderFactory) {
		// TODO Auto-generated method stub
		
	}

	public static void setErrorRenderFactory(
			IErrorRenderFactory errorRenderFactory) {
		// TODO Auto-generated method stub
		
	}

	public Render getErrorRender(int errorCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getDefaultRender(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getErrorRender(int errorCode, String errorView) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getRender(String view) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getJspRender(String view) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getFreeMarkerRender(String view) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getVelocityRender(String view) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getJsonRender(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getJsonRender() {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getJsonRender(String[] attrs) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getJsonRender(String jsonText) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getJsonRender(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getTextRender(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getTextRender(String text, String contentType) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getFileRender(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getFileRender(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getRedirectRender(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getRedirectRender(String url, boolean withQueryString) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getRedirect301Render(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getRedirect301Render(String url, boolean withQueryString) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getNullRender() {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getJavascriptRender(String javascriptText) {
		// TODO Auto-generated method stub
		return null;
	}

	public Render getHtmlRender(String htmlText) {
		// TODO Auto-generated method stub
		return null;
	}

	public void init(Constants constants, ServletContext servletContext) {
		// TODO Auto-generated method stub
		
	}

}

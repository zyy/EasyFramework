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

	public Render getErrorRender(int errorCode, String errorView) {
		// TODO Auto-generated method stub
		return null;
	}

}

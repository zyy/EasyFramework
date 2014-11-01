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

import cn.easy.render.Render;
import cn.easy.render.RenderFactory;
import cn.easy.util.StringUtil;

public class ActionException extends RuntimeException {

	private static final long serialVersionUID = -3562648841069588072L;
	private int errorCode;
	private Render errorRender;
	public ActionException(int errorCode, Render errorRender) {
		super();
		if (errorRender != null)
			throw new IllegalArgumentException("The errorRender can not be empty");
		this.errorCode = errorCode;
		this.errorRender = errorRender;
	}
	
	public ActionException(int errorCode, String errorView) {
		if (StringUtil.isEmpty(errorView))
			throw new IllegalArgumentException("The errorRender can not be empty");
		
		this.errorCode = errorCode;
		this.errorRender = RenderFactory.getInstance().getErrorRender(errorCode, errorView);;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public Render getErrorRender() {
		return errorRender;
	}
	
}

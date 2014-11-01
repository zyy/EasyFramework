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

import java.lang.reflect.Method;

import cn.easy.aop.Interceptor;

public class Action {
	private final Class<? extends Controller> controllerClass;
	private final String controllerKey;
	private final String actionKey;
	private final Method method;
	private final String methodName;
	private final Interceptor inter[];
	private final String viewPath;
	public Action(Class<? extends Controller> controllerClass,
			String controllerKey, String actionKey, Method method,
			String methodName, Interceptor[] inter, String viewPath) {
		super();
		this.controllerClass = controllerClass;
		this.controllerKey = controllerKey;
		this.actionKey = actionKey;
		this.method = method;
		this.methodName = methodName;
		this.inter = inter;
		this.viewPath = viewPath;
	}
	public Class<? extends Controller> getControllerClass() {
		return controllerClass;
	}
	public String getControllerKey() {
		return controllerKey;
	}
	public String getActionKey() {
		return actionKey;
	}
	public Method getMethod() {
		return method;
	}
	public Interceptor[] getInter() {
		return inter;
	}
	public String getViewPath() {
		return viewPath;
	}
	public String getMethodName() {
		return methodName;
	}
	public Interceptor[] getInterceptors() {
		return inter;
	}
}

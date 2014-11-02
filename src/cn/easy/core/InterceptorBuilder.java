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

public class InterceptorBuilder {

	public void addToInterceptorsMap(Interceptor[] defaultInters) {
		// TODO Auto-generated method stub
		
	}

	public Interceptor[] buildControllerInterceptors(
			Class<? extends Controller> controllerClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public Interceptor[] buildMethodInterceptors(Method method) {
		// TODO Auto-generated method stub
		return null;
	}

	public Interceptor[] buildActionInterceptors(Interceptor[] defaultInters,
			Interceptor[] controllerInters,
			Class<? extends Controller> controllerClass,
			Interceptor[] methodInters, Method method) {
		// TODO Auto-generated method stub
		return null;
	}

}

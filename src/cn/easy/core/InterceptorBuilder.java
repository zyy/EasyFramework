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
import java.util.HashMap;
import java.util.Map;

import cn.easy.aop.Before;
import cn.easy.aop.ClearInterceptor;
import cn.easy.aop.ClearLayer;
import cn.easy.aop.Interceptor;

public class InterceptorBuilder {

	private final static Interceptor[] NULL_INTERCEPTOR_ARRAY = new Interceptor[0];
	private Map<Class<Interceptor>, Interceptor> intersMap = new HashMap<Class<Interceptor>, Interceptor>();

	@SuppressWarnings("unchecked")
	public void addToInterceptorsMap(Interceptor[] defaultInters) {
		if (defaultInters == null)
			return;
		for (Interceptor interceptor : defaultInters) {
			intersMap.put((Class<Interceptor>) interceptor.getClass(),
					interceptor);
		}
	}

	public Interceptor[] buildControllerInterceptors(
			Class<? extends Controller> controllerClass) {
		Before before = controllerClass.getAnnotation(Before.class);
		return before != null ? createInterceptors(before)
				: NULL_INTERCEPTOR_ARRAY;
	}

	public Interceptor[] buildMethodInterceptors(Method method) {
		Before before = method.getAnnotation(Before.class);
		return before != null ? createInterceptors(before)
				: NULL_INTERCEPTOR_ARRAY;
	}

	public Interceptor[] buildActionInterceptors(Interceptor[] defaultInters,
			Interceptor[] controllerInters,
			Class<? extends Controller> controllerClass,
			Interceptor[] methodInters, Method method) {
		ClearLayer controllerClearLayer = getControllerClearType(controllerClass);
		if (controllerClearLayer != null)
			defaultInters = NULL_INTERCEPTOR_ARRAY;

		ClearLayer methodClearLayer = getMethodClearType(method);
		if (methodClearLayer != null) {
			controllerInters = NULL_INTERCEPTOR_ARRAY;
			if (methodClearLayer == ClearLayer.ALL)
				defaultInters = NULL_INTERCEPTOR_ARRAY;
		}

		int size = defaultInters.length + controllerInters.length
				+ methodInters.length;
		if (size == 0)
			return NULL_INTERCEPTOR_ARRAY;

		Interceptor[] result = new Interceptor[size];
		int index = 0;
		for (int i = 0; i < defaultInters.length; i++) {
			result[index++] = defaultInters[i];
		}
		for (int i = 0; i < controllerInters.length; i++) {
			result[index++] = controllerInters[i];
		}
		for (int i = 0; i < methodInters.length; i++) {
			result[index++] = methodInters[i];
		}

		return result;
	}

	private ClearLayer getMethodClearType(Method method) {
		ClearInterceptor clearInterceptor = method
				.getAnnotation(ClearInterceptor.class);
		return clearInterceptor != null ? clearInterceptor.value() : null;
	}

	private ClearLayer getControllerClearType(
			Class<? extends Controller> controllerClass) {
		ClearInterceptor clearInterceptor = controllerClass
				.getAnnotation(ClearInterceptor.class);
		return clearInterceptor != null ? clearInterceptor.value() : null;
	}

	@SuppressWarnings("unchecked")
	private Interceptor[] createInterceptors(Before before) {
		Interceptor[] result = null;
		Class<Interceptor>[] interceptorClasses = (Class<Interceptor>[]) before
				.value();
		if (interceptorClasses != null && interceptorClasses.length > 0) {
			result = new Interceptor[interceptorClasses.length];
			for (int i = 0; i < result.length; i++) {
				result[i] = intersMap.get(interceptorClasses[i]);
				if (result[i] != null)
					continue;

				try {
					result[i] = interceptorClasses[i].newInstance();
					intersMap.put(interceptorClasses[i], result[i]);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		return result;
	}
}

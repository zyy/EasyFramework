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
package cn.easy.config;

import java.util.ArrayList;
import java.util.List;

import cn.easy.aop.Interceptor;

public final class Interceptors {
	private List<Interceptor> interceptorList = new ArrayList<Interceptor>();

	public Interceptors add(Interceptor interceptor) {
		if (interceptor != null)
			interceptorList.add(interceptor);
		return this;
	}

	public Interceptor[] getInterceptorList() {
		Interceptor[] interceptors = interceptorList
				.toArray(new Interceptor[interceptorList.size()]);
		return interceptors == null ? new Interceptor[0] : interceptors;
	}
}

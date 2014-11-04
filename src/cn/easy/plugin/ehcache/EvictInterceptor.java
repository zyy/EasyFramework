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
package cn.easy.plugin.ehcache;

import cn.easy.aop.Interceptor;
import cn.easy.core.ActionInvocation;

public class EvictInterceptor implements Interceptor {

	final public void intercept(ActionInvocation ai) {
		ai.invoke();
		CacheUtil.removeAll(buildCacheName(ai));
	}

	private String buildCacheName(ActionInvocation ai) {
		CacheName cacheName = ai.getMethod().getAnnotation(CacheName.class);
		if (cacheName != null)
			return cacheName.value();

		cacheName = ai.getController().getClass()
				.getAnnotation(CacheName.class);
		if (cacheName == null)
			throw new RuntimeException(
					"EvictInterceptor need CacheName annotation in controller.");
		return cacheName.value();
	}

}

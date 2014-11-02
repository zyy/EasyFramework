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

import java.util.HashMap;
import java.util.Map;

import cn.easy.config.Interceptors;
import cn.easy.config.Routes;

public final class ActionMapping {

	private static final String SLASH = "/";
	private Routes routes;
	private Interceptors interceptors;
	
	private final Map<String, Action> mapping = new HashMap<String, Action>();

	public ActionMapping(Routes routes, Interceptors interceptors) {
		super();
		this.routes = routes;
		this.interceptors = interceptors;
	}

	public Action getAction(String target, String[] urlPra) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

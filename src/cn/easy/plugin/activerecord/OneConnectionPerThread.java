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

package cn.easy.plugin.activerecord;

import java.sql.Connection;

import cn.easy.aop.Interceptor;
import cn.easy.core.ActionInvocation;

/**
 * One Connection Per Thread for one request.<br>
 * warning: can not use this interceptor with transaction feature like Tx,
 * Db.tx(...)
 */
public class OneConnectionPerThread implements Interceptor {

	public void intercept(ActionInvocation invocation) {
		Connection conn = null;
		try {
			conn = DbUtil.config.getConnection();
			DbUtil.config.setThreadLocalConnection(conn);
			invocation.invoke();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DbUtil.config.removeThreadLocalConnection();
			DbUtil.config.close(conn);
		}
	}
}

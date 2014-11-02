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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.easy.handler.Handler;
import cn.easy.log.Logger;
import cn.easy.render.Render;
import cn.easy.render.RenderFactory;

final class ActionHandler extends Handler {

	private final boolean devMode;
	private final ActionMapping actionMapping;
	private static final RenderFactory renderFactory = RenderFactory
			.getInstance();
	private static final Logger log = Logger.getLogger(ActionHandler.class);

	public ActionHandler(boolean devMode, ActionMapping actionMapping) {
		super();
		this.devMode = devMode;
		this.actionMapping = actionMapping;
	}

	@Override
	public final void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		if (target.indexOf(".") != -1)
			return;

		isHandled[0] = true;
		String[] urlPara = { null };
		Action action = actionMapping.getAction(target, urlPara);
		if (action == null) {
			if (log.isWarnEnabled()) {
				String query = request.getQueryString();
				log.warn("404 Action not found"
						+ (query == null ? target : target + "?" + query));
			}
			return;
		}

		try {
			Controller controller = action.getControllerClass().newInstance();
			controller.init(request, response, urlPara[0]);

			if (devMode) {
				boolean isMultipartRequest = ActionReporter
						.reportCommonRequest(controller, action);
				new ActionInvocation(action, controller).invoke();
				if (isMultipartRequest)
					ActionReporter.reportMultipartRequest(controller, action);
			} else {
				new ActionInvocation(action, controller).invoke();
			}

			Render render = controller.getRender();
			if (render instanceof ActionRender) {
				String actionUrl = ((ActionRender) render).getActionUrl();
				if (target.equals(actionUrl))
					throw new RuntimeException(
							"The forward action url is the same as before.");
				else
					handle(target, request, response, isHandled);
				return;
			}

			if (render == null)
				render = renderFactory.getDefaultRender(action.getViewPath()
						+ action.getMethodName());
			render.setContext(request, response, action.getViewPath()).render();
		} catch (ActionException e) {
			int errorCode = e.getErrorCode();
			if (errorCode == 404 && log.isWarnEnabled()) {
				String qs = request.getQueryString();
				log.warn("404 Not Found: " + (qs == null ? target : target + "?" + qs));
			}
			else if (errorCode == 401 && log.isWarnEnabled()) {
				String qs = request.getQueryString();
				log.warn("401 Unauthorized: " + (qs == null ? target : target + "?" + qs));
			}
			else if (errorCode == 403 && log.isWarnEnabled()) {
				String qs = request.getQueryString();
				log.warn("403 Forbidden: " + (qs == null ? target : target + "?" + qs));
			}
			else if (log.isErrorEnabled()) {
				String qs = request.getQueryString();
				log.error(qs == null ? target : target + "?" + qs, e);
			}
			e.getErrorRender().setContext(request, response).render();
		} catch (Exception e) {
			if (log.isWarnEnabled()) {
				String query = request.getQueryString();
				log.error(query == null ? target : target + "?" + query, e);
			}
			renderFactory.getErrorRender(500).setContext(request, response)
					.render();
		}
	}

}

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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import cn.easy.plugin.activerecord.CPI;
import cn.easy.plugin.activerecord.Model;
import cn.easy.plugin.activerecord.Page;
import cn.easy.plugin.activerecord.Record;

public class JspRender extends Render {

	private static final long serialVersionUID = 1763455601057725587L;

	private transient static boolean isSupportActiveRecord = true;

	public static void setSupportActiveRecord(boolean supportActiveRecord) {
		JspRender.isSupportActiveRecord = supportActiveRecord;
	}

	public JspRender(String view) {
		this.view = view;
	}

	public void render() {
		// 在 jsp 页面使用如下指令则无需再指字符集, 否则是重复指定了,与页面指定的不一致时还会出乱码
		// <%@ page language="java" contentType="text/html; charset=UTF-8"
		// pageEncoding="UTF-8"%>
		// response.setContentType(contentType);
		// response.setCharacterEncoding(encoding);

		try {
			if (isSupportActiveRecord)
				supportActiveRecord(request);
			request.getRequestDispatcher(view).forward(request, response);
		} catch (Exception e) {
			throw new RenderException(e);
		}
	}

	private static int DEPTH = 8;

	private void supportActiveRecord(HttpServletRequest request) {
		for (Enumeration<String> attrs = request.getAttributeNames(); attrs
				.hasMoreElements();) {
			String key = attrs.nextElement();
			Object value = request.getAttribute(key);
			request.setAttribute(key, handleObject(value, DEPTH));
		}
	}

	@SuppressWarnings("rawtypes")
	private Object handleObject(Object value, int depth) {
		if (value == null || (depth--) <= 0)
			return value;

		if (value instanceof List)
			return handleList((List) value, depth);
		else if (value instanceof Model)
			return handleMap(CPI.getAttrs((Model) value), depth);
		else if (value instanceof Record)
			return handleMap(((Record) value).getColumns(), depth);
		else if (value instanceof Map)
			return handleMap((Map) value, depth);
		else if (value instanceof Page)
			return handlePage((Page) value, depth);
		else if (value instanceof Object[])
			return handleArray((Object[]) value, depth);
		else
			return value;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map handleMap(Map map, int depth) {
		if (map == null || map.size() == 0)
			return map;

		Map<Object, Object> result = map;
		for (Map.Entry<Object, Object> e : result.entrySet()) {
			Object key = e.getKey();
			Object value = e.getValue();
			value = handleObject(value, depth);
			result.put(key, value);
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List handleList(List list, int depth) {
		if (list == null || list.size() == 0)
			return list;

		List result = new ArrayList(list.size());
		for (Object value : list)
			result.add(handleObject(value, depth));
		return result;
	}

	@SuppressWarnings("rawtypes")
	private Object handlePage(Page page, int depth) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", handleList(page.getList(), depth));
		result.put("pageNumber", page.getPageNumber());
		result.put("pageSize", page.getPageSize());
		result.put("totalPage", page.getTotalPage());
		result.put("totalRow", page.getTotalRow());
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List handleArray(Object[] array, int depth) {
		if (array == null || array.length == 0)
			return new ArrayList(0);

		List result = new ArrayList(array.length);
		for (int i = 0; i < array.length; i++)
			result.add(handleObject(array[i], depth));
		return result;
	}

}

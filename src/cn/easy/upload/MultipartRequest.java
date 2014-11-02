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
package cn.easy.upload;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MultipartRequest extends HttpServletRequestWrapper {

	public MultipartRequest(HttpServletRequest request) {
		super(request);
	}

	public MultipartRequest(HttpServletRequest request, String saveDirectory) {
		super(request);
	}

	public MultipartRequest(HttpServletRequest request, String saveDirectory,
			int maxPostSize) {
		super(request);
	}

	public MultipartRequest(HttpServletRequest request, String saveDirectory,
			Integer maxPostSize, String encoding) {
		super(request);
	}

	public List<UploadFile> getFiles() {
		return null;
	}

	public static void init(String saveDirectory, int maxPostSize,
			String encoding) {
		// TODO Auto-generated method stub
		
	}


}

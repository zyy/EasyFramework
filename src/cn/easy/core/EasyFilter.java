package cn.easy.core;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EasyFilter implements Filter {

	public void destroy() {
		System.out.println("-->call destroy method");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("-->get new request to filter");
	}

	public void init(FilterConfig config) throws ServletException {
		System.out.println("-->call init method");
	}

}

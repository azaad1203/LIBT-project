package com.task3.redirect;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
/**
 * 
 * 
 *
 */
public class ForwardRequestFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getServletPath();
		req.getRequestDispatcher("/control" + path).forward(request, response);
		
	}
	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}
	@Override
	public void destroy() {
		
	}
}

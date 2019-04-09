package com.task3.redirect;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.task3.security.CheckAuthorization;

public class LoginHomePageFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String path = req.getServletPath();
		if(path.equals("/index.html")){
		CheckAuthorization ca = new CheckAuthorization(req , res);
			if(ca.confirmIdentity()){
				res.sendRedirect("admin-panel.jsp");
				
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}

}

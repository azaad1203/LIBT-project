package com.task3.process;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class LoginCommand extends TaskCommand{
	private String username = "admin";
	private String password = "test";

	@Override
	public void execute() {
		if(getRequest().getServletContext().getAttribute("activeSessionId") != null){
			getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			String urname = getRequest().getParameter("username");
			String pw = getRequest().getParameter("password");
			if(urname.equals(username) && pw.equals(password)){
				setRedirectPage("admin-panel.jsp");
				HttpSession session = getRequest().getSession();
				session.setAttribute("username", username);
				_registerSession(session);
				_createCookie(session.getId());
			} else {
				getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
	}
	
	private void _createCookie(String sid){
		Cookie cookie =	new Cookie("sid", sid);
		cookie.setMaxAge(-1);
		getResponse().addCookie(cookie);
	}
	
	private void _registerSession(HttpSession session){
		HashMap<String, HttpSession> activeSessions = null;
		String sessionId = session.getId();
		if(session.getServletContext().getAttribute("activeSessions")!=null){
			activeSessions = (HashMap<String, HttpSession>) session.getServletContext().getAttribute("activeSessions");
			activeSessions.put(sessionId, session);
		} else {
			activeSessions = new HashMap<String, HttpSession>();
			activeSessions.put(sessionId, session);
			session.getServletContext().setAttribute("activeSessions", activeSessions);
		}
	}
	
	@Override
	public boolean isAuthorized() {
		return false;
	}
	

}

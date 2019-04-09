package com.task3.process;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LogoutCommand extends TaskCommand{

	@Override
	public void execute() {
		setRedirectPage("index.html");
		HashMap<String, HttpSession> activeSessions = (HashMap<String, HttpSession>) getRequest().getServletContext().getAttribute("activeSessions");
		String sid = _getSidAndDestroyCookie();
		_destroySession(activeSessions, sid);
	}

	@Override
	public boolean isAuthorized() {
		return true;
	}
	
	private String _getSidAndDestroyCookie(){
		Cookie[] cookies = getRequest().getCookies();
		String sid = null;
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("sid")){
				sid = cookie.getValue();
				cookie.setMaxAge(0);
				getResponse().addCookie(cookie);
			}
		}
		return sid;
	}
	
	private void _destroySession(HashMap<String, HttpSession> activeSessions, String sid){
		try{
			activeSessions.remove(sid);
			getRequest().getServletContext().setAttribute("activeSessions", activeSessions);
		} catch(NullPointerException e) {
			e.printStackTrace();
			getResponse().setStatus(HttpServletResponse.SC_BAD_GATEWAY);
		}
		
	}

}

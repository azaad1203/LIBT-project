package com.task3.security;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckAuthorization {
    private HttpServletRequest request;
    private  HttpServletResponse response;
    private HashMap<String, HttpSession> activeSessions = null;
	public CheckAuthorization(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
	}
	
	public boolean confirmIdentity(){
		Cookie[] cookies = request.getCookies();
		String sid = null;
        activeSessions = (HashMap<String, HttpSession>) request.getServletContext().getAttribute("activeSessions");
		try{
	        for(Cookie cookie: cookies){
				if(cookie.getName().equals("sid")){
					sid = cookie.getValue();
					break;
				}
			}
		} catch(NullPointerException e) {
			return false;
		}
		
		if(sid != null && activeSessions != null){
			HttpSession session = activeSessions.get(sid);
			
			if(session == null){
				return false;
			} else {
				return true;
			}
			
		} else {
			return false;
		}
}
}

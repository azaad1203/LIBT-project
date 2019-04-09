package com.task3.process;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;



public class ValidateLoginCommand extends TaskCommand{
	private String username = "admin";
	private String password = "test";
	@Override
	public void execute() {
		setRedirect(false);
		try {
			getResponse().setContentType("text/plain"); 
			getResponse().setCharacterEncoding("UTF-8");
			String urname = getRequest().getParameter("username");
			String pw = getRequest().getParameter("password");
			PrintWriter out;
			out = getResponse().getWriter();
			String msg = "";
			
			if(urname.equals(username) && pw.equals(password)){
				msg = "ok" ;
			} else {
				msg += "<ul>" ;
				msg += "<li>Please enter correct user name</li>" ;
				msg += "<li>Please enter correct password</li>" ;
				msg += "</ul>" ;
			}
			out.print(msg);
			out.close();
		} catch(IOException e) {
			e.printStackTrace();
			getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		}
		
		
	}
	
	@Override
	public boolean isAuthorized() {
		return false;
	}

}

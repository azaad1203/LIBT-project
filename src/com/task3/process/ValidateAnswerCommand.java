package com.task3.process;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;



public class ValidateAnswerCommand extends TaskCommand{

	@Override
	public void execute() {
		setRedirect(false);
		try {
			getResponse().setContentType("text/plain"); 
			getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = getResponse().getWriter();
			String msg = "";
			String title = getRequest().getParameter("title");
			String description = getRequest().getParameter("description");
			if("".equals(description)){
				msg += "<li>Please enter your answer</li>" ;
			}
			if(description.length() > 2000){
				msg += "<li>Title should be lesser than 2000 character.</li>" ;
			}
			if(! "".equals(msg)){
				msg = "<ul>" + msg;
				msg += "</ul>";
			} else {
				msg = "ok";
			}
			out.print(msg);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		}
		
		
	}
	
	@Override
	public boolean isAuthorized() {
		return false;
	}

}


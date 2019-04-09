package com.task3.process;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;



public class ValidateQuestionCommand extends TaskCommand{

	@Override
	public void execute() {
		setRedirect(false);
		try {
			getResponse().setContentType("text/plain"); 
			getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = getResponse().getWriter();
			String msg = "";
			String emailFormat = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
			String e_validate = getRequest().getParameter("e_validate");
			String name = getRequest().getParameter("name");
			String email = getRequest().getParameter("email");
			String title = getRequest().getParameter("title");
			String description = getRequest().getParameter("description");
			boolean emptyValidateMode;
			
			if("1".equals(e_validate)){
				emptyValidateMode = true;
			} else if("0".equals(e_validate)) {
				emptyValidateMode = false;
			} else {
				emptyValidateMode = true;
				getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			
			if(emptyValidateMode == true){
				if("".equals(name) || name == null ){
					msg += "<li>Please enter name</li>" ;
				}
				if("".equals(email) || email == null ){
					msg += "<li>Please enter email</li>" ;
				}
				if("".equals(title) || title == null ){
					msg += "<li>Please enter title</li>" ;
				}
				if("".equals(description) || description == null ){
					msg += "<li>Please enter description</li>" ;
				}
			}
			
			if(name.length() > 50){
				msg += "<li>Name should be lesser than 50 character.</li>" ;
			}
			if(email.length() > 100){
				msg += "<li>Email should be lesser than 100 character.</li>" ;
			}
			if(title.length() > 300 ){
				msg += "<li>Title should be lesser than 300 character.</li>" ;
			}
			if(description.length() > 2000){
				msg += "<li>Description should be lesser than 2000 character.</li>" ;
			}
			if(!email.matches(emailFormat)){
				msg += "<li>Please enter valid email</li>" ;
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

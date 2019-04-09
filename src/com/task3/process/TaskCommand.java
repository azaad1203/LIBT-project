package com.task3.process;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.task3.command.Command;
import com.task3.content.Content;
import com.task3.data.ColumnType;
import com.task3.security.CheckAuthorization;

public abstract class TaskCommand implements Command{
    private HttpServletRequest request;
    private  HttpServletResponse response;
    private String redirectPage;
    private boolean redirect;
	private String errorMessage = null;
	private HashMap<String, HttpSession> activeSessions;

	public abstract boolean isAuthorized();
    public void init(HttpServletRequest request, HttpServletResponse response) {
        this.setRequest(request);
        this.setResponse(response);
        this.setRedirect(true);
        if(isAuthorized()){
        	CheckAuthorization ca = new CheckAuthorization(request, response);
        	if(!ca.confirmIdentity()){
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				setErrorMessage("Opps ! you have not logged in.");
        	}
        }
    }
    

	public boolean isRedirect() {
		return redirect;
	}

	protected void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

    public void setRedirectPage(String page) {
    	redirectPage = page;
    }

    public String getRedirectPage() {
        return redirectPage;
    }


    public HttpServletRequest getRequest() {
        return request;
    }

    private void setRequest(HttpServletRequest request) {
        this.request = request;
    }


	public HttpServletResponse getResponse() {
		return response;
	}


	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	
	protected Content _getContentFromColumnArray(ColumnType[] columns, Content content){
		DateFormat dateFormat = Content.getDateFormat();
		Field[] fields = content.getClass().getDeclaredFields();
		for(ColumnType column : columns){
			for (Field field : fields) {
				field.setAccessible(true);
				if(column.getName().toLowerCase().equals(field.getName().toLowerCase())){
					if(field.getType().equals(String.class)){
						try {
							field.set(content, column.getStringValue());
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						}
					} else if(field.getType().equals(Boolean.class)){
						try {
							int temp_value = Integer.parseInt(column.getStringValue());
							Boolean value = null;
							switch(temp_value){
								case 0:
									value = false;
									break;
								case 1:
									value = true;
									break;
								}
							field.set(content, value);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						}
					}  else if(field.getType().equals(Date.class)){
							try {
								field.set(content, dateFormat.parse(column.getStringValue()));
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}
				}
			 }	
			return content;
		}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}

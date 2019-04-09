package com.task3.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.XmlException;

import com.task3.command.CommandFactory;
import com.task3.display.PageCommand;
import com.task3.process.TaskCommand;

public class FrontController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public void init() throws ServletException{
		super.init();
	}
	
	private void processIdempotentRequest(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, XmlException{
		String request_url =  request.getPathInfo();
		String commandName = getCommandNameByPath(request_url);
		PageCommand command = null;
			try {
				if(CommandFactory.getCommandByName(commandName, "display") instanceof PageCommand){
					command = (PageCommand) CommandFactory.getCommandByName(commandName, "display");
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "Oops ! The server encountered a temporary error and could not complete your request.");
			} catch (ClassCastException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}

		
		try{
			command.init(request, response);
			if(response.getStatus() >= 400 && response.getStatus() < 600){
				response.sendError(response.getStatus(), command.getErrorMessage());
			} else {
				command.execute();
				if(response.getStatus() >= 400 && response.getStatus() < 600){
					response.sendError(response.getStatus(), command.getErrorMessage());
				} else{
			        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(command.getForwordPage());
			        requestDispatcher.forward(request, response);	
				}
			}
		} catch(NullPointerException e) {
	        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(request_url);
	        requestDispatcher.forward(request, response);	
		}
	}
	
	private void processNonIdempotentRequest(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, XmlException{
		String request_url =  request.getPathInfo();
		String commandName = getCommandNameByPath(request_url);
		TaskCommand command = null;
		try {
			if(CommandFactory.getCommandByName(commandName, "process") instanceof TaskCommand){
				command = (TaskCommand) CommandFactory.getCommandByName(commandName, "process");
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Oops ! The server encountered a temporary error and could not complete your request.");
		} catch (ClassCastException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		try{
			command.init(request, response);
			if(response.getStatus() >= 400 && response.getStatus() < 600){
				response.sendError(response.getStatus(), command.getErrorMessage());
			} else {
				command.execute();
				if(response.getStatus() >= 400 && response.getStatus() < 600){
					response.sendError(response.getStatus(), command.getErrorMessage());
				} else if(command.isRedirect()){
					response.sendRedirect(command.getRedirectPage());
		        }
			}
		} catch(NullPointerException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);	
		}
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException{
		try {
			processIdempotentRequest(request, response);
		} catch (XmlException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);	
		}

	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException{
		try {
			processNonIdempotentRequest(request, response);
		} catch (XmlException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);	
		}
	}
	public void destroy(){
		
	}
	
	private String getCommandNameByPath(String request_url){
		request_url = request_url.substring(1);
		request_url = request_url.replace(".jsp", "");
		String[] temp = request_url.split("-|_");
		String commandName = "";	
 			if(temp.length >= 2){
 				for(int i = 0 ; i < temp.length ; i++){
 					commandName += capitalize(temp[i]);
 				}
 			} else {
 	 			commandName = capitalize(request_url);
 			}
 			return commandName;
	}
	
	private String capitalize(String line){
	  return Character.toUpperCase(line.charAt(0)) + line.substring(1).toLowerCase();
	}
}

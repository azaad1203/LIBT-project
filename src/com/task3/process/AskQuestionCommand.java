package com.task3.process;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.XmlException;

import com.task3.content.Question;
import com.task3.content.XmlDAO;

public class AskQuestionCommand extends TaskCommand{

	@Override
	public void execute() {
		setRedirectPage("view-question.jsp");
		XmlDAO dao = new XmlDAO(getRequest());
		
		Map<String, String> values = new HashMap<String, String>();
		values.put("id", UUID.randomUUID().toString());
		values.put("name", getRequest().getParameter("name"));
		values.put("email", getRequest().getParameter("email"));
		values.put("title", getRequest().getParameter("title"));
		values.put("description", getRequest().getParameter("description"));
		values.put("postDate", Question.getDateFormat().format(new Date()));
		values.put("modifiedDate", Question.getDateFormat().format(new Date()));
		values.put("updateDate", Question.getDateFormat().format(new Date()));
		values.put("modified", "0");
		values.put("answerId", "");
		
		try {
			dao.insert("question", values);
		} catch (IOException e) {
			e.printStackTrace();
			getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		} catch (XmlException e) {
			e.printStackTrace();
			getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		}
		 
		
	}
	
	@Override
	public boolean isAuthorized() {
		return false;
	}

	
	

}

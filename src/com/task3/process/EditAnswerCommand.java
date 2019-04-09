package com.task3.process;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.XmlException;

import com.task3.content.Content;
import com.task3.content.XmlDAO;

public class EditAnswerCommand extends TaskCommand{

	@Override
	public void execute() {
		setRedirectPage("admin-panel.jsp");
		XmlDAO dao = new XmlDAO(getRequest());
		
		Map<String, String> answer_values = new HashMap<String, String>();
		Map<String, String> question_values = new HashMap<String, String>();
		
		_putValues(answer_values, "description", getRequest().getParameter("description"));
		if(answer_values.size()>0){
			_putValues(question_values, "updateDate", Content.getDateFormat().format(new Date()));
			_putValues(answer_values, "modifiedDate", Content.getDateFormat().format(new Date()));
			_putValues(answer_values, "modified", "1");
		}
		
		
		if(getRequest().getParameter("id") != null){
			try {
				dao.update("question", question_values, "answerId == "+ getRequest().getParameter("id") );
				dao.update("answer", answer_values, "id == "+ getRequest().getParameter("id") );
			} catch (IOException e) {
				e.printStackTrace();
				getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			} catch (XmlException e) {
				e.printStackTrace();
				getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			}
		}
	}
	
	private void _putValues(Map<String, String> values,String key, String parameter){
		if(!"".equals(parameter) && parameter != null){
			values.put(key, parameter);
		}
	}
	
	@Override
	public boolean isAuthorized() {
		return true;
	}


}

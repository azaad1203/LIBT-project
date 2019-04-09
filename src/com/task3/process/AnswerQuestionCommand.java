package com.task3.process;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.XmlException;

import com.task3.content.Answer;
import com.task3.content.Question;
import com.task3.content.XmlDAO;
import com.task3.data.RecordType;

public class AnswerQuestionCommand extends TaskCommand{
	@Override
	public void execute() {
		setRedirectPage("admin-panel.jsp");
		XmlDAO dao = new XmlDAO(getRequest());
		
		RecordType record = null;
		try {
			record = dao.select("question", "id == "+ getRequest().getParameter("id"));
		}  catch (IOException e) {
			e.printStackTrace();
			getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		} catch (XmlException e) {
			e.printStackTrace();
			getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		}
		
		Question question = (Question) _getContentFromColumnArray(record.getColumnArray(), new Question());
		if (record != null) {
			Map<String, String> answer_values = new HashMap<String, String>();
			Map<String, String> question_values = new HashMap<String, String>();
			String answer_id = UUID.randomUUID().toString();
			question_values.put("answerId", answer_id);
			question_values.put("updateDate",
					Question.getDateFormat().format(new Date()));
			answer_values.put("id", answer_id);
			answer_values.put("description",
					getRequest().getParameter("description"));
			answer_values.put("postDate",
					Answer.getDateFormat().format(new Date()));
			answer_values.put("modifiedDate",
					Answer.getDateFormat().format(new Date()));
			answer_values.put("modified", "0");
			if (getRequest().getParameter("id") != null) {
				try {
					dao.insert("answer", answer_values);
					dao.update("question", question_values, "id == "
							+ getRequest().getParameter("id"));
				} catch (IOException e) {
					e.printStackTrace();
					getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
				} catch (XmlException e) {
					e.printStackTrace();
					getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
				}
			}
		} else {
			getResponse().setStatus(HttpServletResponse.SC_CONFLICT);
		}
	}
	
	@Override
	public boolean isAuthorized() {
		return true;
	}
	
}

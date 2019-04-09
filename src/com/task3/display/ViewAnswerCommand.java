package com.task3.display;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.XmlException;

import com.task3.content.Answer;
import com.task3.content.Question;
import com.task3.content.XmlDAO;
import com.task3.data.RecordType;

public class ViewAnswerCommand extends PageCommand{

	@Override
	public void execute() {
		XmlDAO dao = new XmlDAO(getRequest());
		RecordType q_records = null;
		RecordType a_records = null;
		if(getRequest().getParameter("id") != null){
			try {
				q_records = dao.select("question", "id == "+ getRequest().getParameter("id"));
			} catch (IOException e) {
				e.printStackTrace();
				getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			} catch (XmlException e) {
				e.printStackTrace();
				getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			}
			Question question = (Question) _getContentFromColumnArray(q_records.getColumnArray(), new Question());
			try {
				a_records = dao.select("answer", "id == "+ question.getAnswerId());
			} catch (IOException e) {
				e.printStackTrace();
				getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			} catch (XmlException e) {
				e.printStackTrace();
				getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			}
			Answer answer;
			if(a_records != null){
				answer = (Answer) _getContentFromColumnArray(a_records.getColumnArray(), new Answer());
				answer.setDescription(answer.getDescription().replaceAll("\n", "<br/>"));
			} else {
				answer = null;
			}
			question.setDescription(question.getDescription().replaceAll("\n", "<br/>"));
			getRequest().setAttribute("question", question);
			getRequest().setAttribute("answer", answer);
		} else {
			getResponse().setStatus(HttpServletResponse.SC_BAD_GATEWAY);
		}
	}
	
	@Override
	public boolean isAuthorized() {
		return false;
	}

}

package com.task3.display;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.XmlException;

import com.task3.content.Question;
import com.task3.content.XmlDAO;
import com.task3.data.RecordType;

public class ViewQuestionCommand extends PageCommand{

	@Override
	public void execute() {
		XmlDAO dao = new XmlDAO(getRequest());
		RecordType[] records = null;
		try {
			records = dao.select("question");
		} catch (IOException e) {
			e.printStackTrace();
			getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		} catch (XmlException e) {
			e.printStackTrace();
			getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		}
		List<Question> questions = new ArrayList<Question>();
		for(RecordType record : records){
			Question question = (Question) _getContentFromColumnArray( record.getColumnArray(), new Question() );
			questions.add(question);
			
		}
		Collections.sort(questions);
		getRequest().setAttribute("totalPage", _getTotalPage(questions));
		questions = _getQuestionByPageNo(questions);
		getRequest().setAttribute("questions", questions);
	}
	
	@Override
	public boolean isAuthorized() {
		return false;
	}
	
	private List<Question> _getQuestionByPageNo(List<Question> questions){
		int totalPage = (int) Math.ceil(questions.size()/5.0);
		int remainder = questions.size() % 5 ;
		String page_ = getRequest().getParameter("page");
		int page = 0;
		if(page_ == null){
			page = 1;
		} else {
			try{
				page = Integer.parseInt(page_);
			} catch(NumberFormatException e){
				e.printStackTrace();
				getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
		if(page > totalPage){
			getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		} else {
			int fromIndex = (page-1) * 5;
			int toIndex = page == totalPage && remainder != 0 ? remainder + fromIndex: 5 + fromIndex;
			return questions.subList(fromIndex, toIndex);
		}
		
	}
	
	private int _getTotalPage(List<Question> questions){
		return (int) Math.ceil(questions.size()/5.0);
	}


}

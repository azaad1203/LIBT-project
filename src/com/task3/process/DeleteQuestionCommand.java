package com.task3.process;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.XmlException;

import com.task3.content.XmlDAO;

public class DeleteQuestionCommand extends TaskCommand{

	@Override
	public void execute() {
		setRedirectPage("admin-panel.jsp");
		XmlDAO dao = new XmlDAO(getRequest());
		String[] values = (String[]) getRequest().getParameterValues("id");
		if(values != null){
			for(String raw : values){
				String[] id = raw.split("##");
				try {
					if(id.length == 2){
						dao.delete("question", "id == "+id[0]);
						dao.delete("answer", "id == "+id[1]);
					} else if(id.length == 1){
						dao.delete("question", "id == "+id[0]);
					}
				} catch (IOException e) {
					e.printStackTrace();
					getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
				} catch (XmlException e) {
					e.printStackTrace();
					getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
				}
			}
		}
	}
	
	@Override
	public boolean isAuthorized() {
		return true;
	}

}

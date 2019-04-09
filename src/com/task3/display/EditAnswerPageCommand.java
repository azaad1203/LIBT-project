package com.task3.display;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.XmlException;

import com.task3.command.Command;
import com.task3.content.Answer;
import com.task3.content.Question;
import com.task3.content.XmlDAO;
import com.task3.data.RecordType;

public class EditAnswerPageCommand extends PageCommand{

	@Override
	public void execute() {
		Command command = new ViewAnswerCommand();
		command.init(getRequest(), getResponse());
		command.execute();
	}
	
	@Override
	public boolean isAuthorized() {
		return true;
	}


}

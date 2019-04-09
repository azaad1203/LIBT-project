package com.task3.display;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.XmlException;

import com.task3.content.Answer;
import com.task3.content.Question;
import com.task3.content.XmlDAO;
import com.task3.data.RecordType;

public class StatisticReportCommand extends PageCommand{
	 private static final int BUFSIZE = 4096;
	@Override
	public void execute() {
	    File file = _createReport();
	    _doDownload(file);
		
	}
	private File _createReport(){
			File tempDir = (File) getRequest().getServletContext().
		        getAttribute("javax.servlet.context.tempdir");
			ArrayList<Question> questions = _getQuestions();
		    File file = null;
			try {
				file = File.createTempFile( "stat", ".tmp", tempDir );
				FileWriter fstream = new FileWriter(file);
				BufferedWriter out = new BufferedWriter(fstream);
				
				long[] result = _getResult(questions);
				out.write("=========================================================================");
				out.newLine();				
				out.write("=--------------------------Statistical Report---------------------------=");
				out.newLine();
				out.write("=========================================================================");
				out.newLine();
				out.write("The maximum number of hours that has taken to reply to a request : " + result[0]);
				out.newLine();
				out.write("The mean number of hours taken to reply to answered requests : "+ result[1]);
				out.newLine();
				out.write("The median number of hours taken to reply to answered requests : "+ result[2]);
				out.newLine();
				out.write("The number of requests that have been answered in total be they current or archived : " + result[3]);
				out.newLine();
				out.write("The number of currently archived messages : " + result[4] );
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		return file;
	}
	
	private long[] _getResult(ArrayList<Question> questions){
		long sum = 0;
		long max = 0;
		long mean =  0;
		long median = 0;
		long answeredRequests = 0;
		long archivedRequests = 0;
		ArrayList<Long> responseTimes = new ArrayList<Long>();

		for(Question question : questions){
			Date qPostDate = question.getPostDate();
			long responseTime = 0;
			if (! "".equals(question.getAnswerId()) && question.getAnswerId() != null){
				Answer answer = _getCorespondingAnswer(question.getAnswerId());
				Date aPostDate = answer.getPostDate();
				responseTime = ((aPostDate.getTime() - qPostDate.getTime()) / (1000*3600));
				responseTimes.add(responseTime);
				sum	+= responseTime;
				max = max < responseTime? responseTime : max;

			} 
		}

		if(responseTimes.size() > 0){
		mean = sum/responseTimes.size();
		Collections.sort(responseTimes);
		double medianPosition = (responseTimes.size()+1)/2.0;
		if (medianPosition == Math.round(medianPosition)){
			int position = (int) medianPosition;
			position -= 1;
			median = responseTimes.get(position);	
		} else {
			int positionA = (int) Math.floor(medianPosition);
			positionA -= 1;
			int positionB = (int) Math.ceil(medianPosition);
			positionB -=1;
			median = (responseTimes.get(positionA)+ responseTimes.get(positionB))/2;	
		}
		}
		
		answeredRequests = responseTimes.size();
		archivedRequests = questions.size();

		return new long[]{max, mean, median, answeredRequests, archivedRequests};
	}
	private void _doDownload(File file){
	    ServletOutputStream outStream = null;
		try {
	        outStream = getResponse().getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
        int length   = 0;
        String mimetype = "text/plain";
        
        getResponse().setContentType(mimetype);
        getResponse().setContentLength((int) file.length());
        String fileName = "stat.txt";
        
        getResponse().setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        
        byte[] byteBuffer = new byte[BUFSIZE];
        DataInputStream in = null;
		try {
			in = new DataInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
        

        try {
            while ((in != null) && ((length = in.read(byteBuffer)) != -1))
            {
                outStream.write(byteBuffer,0,length);
            }
            
            in.close();
            outStream.close();
            
		} catch (IOException e) {
			e.printStackTrace();
			getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	private ArrayList<Question> _getQuestions(){
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
		ArrayList<Question> questions = new ArrayList<Question>();
		for(RecordType record : records){
			Question question = (Question) _getContentFromColumnArray( record.getColumnArray(), new Question() );
			questions.add(question);	
		}
		Collections.sort(questions);
		return questions;
	}
	
	private Answer _getCorespondingAnswer(String questionId){
		XmlDAO dao = new XmlDAO(getRequest());
		RecordType record = null;
		try {
			record = dao.select("answer", "id == "+ questionId);
		} catch (IOException e) {
			e.printStackTrace();
			getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		} catch (XmlException e) {
			e.printStackTrace();
			getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		}
		if(record != null){
			Answer answer = (Answer) _getContentFromColumnArray(record.getColumnArray(), new Answer() );
			return answer;
		} else {
			return null;
		}
		
	}
	@Override
	public boolean isAuthorized() {
		return false;
	}

}

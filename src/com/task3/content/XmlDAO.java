package com.task3.content;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;

import com.task3.data.ColumnType;
import com.task3.data.RecordType;
import com.task3.data.TableDocument;


public class XmlDAO{
	private static String directory = "/WEB-INF/xml/";
	private HttpServletRequest request = null;
	private TableDocument doc = null;
	private File xmlFile = null;
	private void _openFile(String directory) throws IOException, XmlException {
		directory = request.getServletContext().getRealPath(directory);
		xmlFile = new File(directory);
		doc = TableDocument.Factory.parse(xmlFile);
	}
	
	public XmlDAO(HttpServletRequest request){
		this.request = request;
	}
	
	private void _save(){
		try {doc.save(xmlFile);} catch (Exception e) {e.printStackTrace();}
	}
	
	public RecordType[] select(String from) throws IOException, XmlException{
		_openFile(directory+from+".xml");
		RecordType[] records = doc.getTable().getRecordArray();
		return records;
	}
	
	public RecordType select(String from, String where) throws IOException, XmlException{
		RecordType[] records = select(from);
		String[] condition = where.split("\\s*==\\s*");
		
		if(condition == null) {
			throw new IllegalArgumentException("Please enter a valid condition. The format of condition is \"'name' == 'value'\"");
		}
		else if(condition.length == 2) for(RecordType record: records){
			ColumnType[] columns = record.getColumnArray();
			for(ColumnType column: columns){
					if(column.getName().equals(condition[0]) 
							&& column.getStringValue().equals(condition[1])){
						return record;
					}

						
				}
			}
		return null;
	}

	public void insert(String from, Map<String, String> values) throws IOException, XmlException {
		_openFile(directory+from+".xml");
		RecordType record = doc.getTable().addNewRecord();
		for(String name: values.keySet()){
			ColumnType column = record.addNewColumn();
			column.setName(name);
			column.setStringValue(values.get(name));
		}
		_save();
	}

	public void update(String from, Map<String, String> values, String where) throws IOException, XmlException{
		_openFile(directory+from+".xml");
		RecordType[] records = doc.getTable().getRecordArray();
		String[] condition = where.split("\\s*==\\s*");
		
		if(condition == null) {
			throw new IllegalArgumentException("Please enter a valid condition. The format of condition is \"'name' == 'value'\"");
		}
		else for(RecordType record: records){
			ColumnType[] columns = record.getColumnArray();
			innerLoop : for(ColumnType column: columns){
					if(column.getName().equals(condition[0]) 
							&& column.getStringValue().equals(condition[1])){
						for(ColumnType column_: columns){
							innerLoop2: for(String name: values.keySet()){
								if(column_.getName().equals(name)){
									column_.setStringValue(values.get(name));
									values.remove(name);
									break innerLoop2;
								} else{
									continue innerLoop2;
								}
							}
						}
						for(String name: values.keySet()){
								ColumnType column__ = record.addNewColumn();
								column__.setName(name);
								column__.setStringValue(values.get(name));
						}
					} else {
						continue innerLoop;
						}
				}
			}
		_save();
	}

	public void delete(String from, String where) throws IOException, XmlException{
		_openFile(directory+from+".xml");
		String[] condition = where.split("\\s*==\\s*");
		if(condition == null) {
			throw new IllegalArgumentException("Please enter a valid condition. The format of condition is \"'name' == 'value'\"");
		} else {
			XmlCursor cursor = doc.newCursor();
			cursor.selectPath("./table/record[column[@name='"+condition[0]+"' and text() = '"+condition[1]+"']]");
			if(cursor.hasNextSelection()){
				cursor.toNextSelection();
				cursor.removeXml();
			}
			cursor.dispose();
			_save();
		}
		
	}



}

package com.task3.content;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public abstract class Content implements Serializable{
	private static final long serialVersionUID = -4426371004750988868L;
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:mm:ss a", Locale.UK );
	private static DateFormat displayDateFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a", Locale.UK );
	public static DateFormat getDateFormat() {
		return dateFormat;
	}
	public static DateFormat getDisplayDateFormat() {
		return displayDateFormat;
	}
	
}

package com.task3.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/*
Thanks to:
http://greatwebguy.com/programming/java/simple-cross-site-scripting-xss-servlet-filter
*/

public class HtmlEscapeRequestWrapper extends HttpServletRequestWrapper {

	public HtmlEscapeRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values==null)  {
                   return null;
            }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
              encodedValues[i] = cleanXSS(values[i]);
         }
        return encodedValues;
      }
      public String getParameter(String parameter) {
            String value = super.getParameter(parameter);
            if (value == null) {
                   return null;
                    }
            return cleanXSS(value);
      }
      public String getHeader(String name) {
          String value = super.getHeader(name);
          if (value == null)
              return null;
          return cleanXSS(value);
      }
      private String cleanXSS(String value) {
          value = value.replaceAll("&", "&amp;");
          value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
          value = value.replaceAll("'", "&#x27;").replaceAll("\"", "&quot;");
          value = value.replaceAll("/", "&#x2F;").replaceAll("\\\\", "&#x5C;");
          value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
       
          return value;
      }

}

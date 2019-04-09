<%@ page language="java" contentType="text/html;  charset=UTF-8"
    pageEncoding="UTF-8" import="com.task3.content.Question, java.util.List"%>
<%
List<Question> questions = null;
Integer totalPage = null;
questions = (List<Question>) request.getAttribute("questions");
totalPage = (Integer) request.getAttribute("totalPage");
String status = null;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>View Question - Brunel Computing</title>
<link rel="stylesheet" type="text/css" href="css/main.css"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<script type="text/javascript" src="lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="script/cookie.js"></script>
<script type="text/javascript" src="script/validate.js"></script>
<script type="text/javascript" src="script/show_message.js"></script>
<script type="text/javascript" src="script/show_form_message.js"></script>
<script type="text/javascript" src="script/show_login.js"></script>
</head>
<body>
<div id="container">
<div id="header">
<a id="banner" href="index.html"></a>
<div id="main_nav">
<a href="index.html">HOME</a>
<a href="revision.html">REVISION</a>
<a href="view-question.jsp">VIEW QUESTION</a>
<a href="about.html">ABOUT</a>
<a href="contact.html">CONTACT</a>
<a class="link-button" href="ask-question.html">ASK QUESTION</a>
</div>
</div>

<div id="main-content">
<h1>Question</h1>
<%try { for(Question question : questions){
	status = question.getAnswerId() != null && !"".equals(question.getAnswerId())? "img/answered.png": "img/pending.png";
%>
<div class="view-content">
<div class="content-status">
<img src="<%=status %>"/>
</div>
<div class="content-main-with-status">
<h1><a href="view-answer.jsp?id=<%=question.getId() %>"><%=question.getTitle() %></a></h1>
<div class="content-info">
<p><%=question.getName() %> asked at <%=Question.getDisplayDateFormat().format(question.getPostDate()) %></p>
<p>Email: <%=question.getEmail() %></p>
<%if(question.isModified()){ %><%="<p class=\"modified\">[modified by admin at " + Question.getDisplayDateFormat().format(question.getModifiedDate())+"]&nbsp;</p>"%><%} %>
</div>
</div>
</div>
<%} for(int index = 1; index < totalPage; index++){ %>
<a href="view-question.jsp?page=<%=index %>"><%=index %></a><b>&nbsp;|</b>
<%}%>
<a href="view-question.jsp?page=<%=totalPage %>"><%=totalPage %></a>
<%} catch(NullPointerException e){
	response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
} %>
</div>

<div id="footer"><p>Copyright © 2012 LIBT. All Rights Reserved. | <a href="reference.html">Reference</a> | <a href="admin-panel.jsp" id="open-login-box">admin panel</a></p></div>
</div>


</body>
</html>
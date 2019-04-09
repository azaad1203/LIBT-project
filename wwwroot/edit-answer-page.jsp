<%@ page language="java" contentType="text/html;  charset=UTF-8"
    pageEncoding="UTF-8" import="com.task3.content.Answer,com.task3.content.Question"%>
<%
Question question = null;
Answer answer = null;

question = (Question) request.getAttribute("question");
answer = (Answer) request.getAttribute("answer");
String[] temp = null;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Edit Answer - Brunel Computing</title>
<link rel="stylesheet" type="text/css" href="css/main.css"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<script type="text/javascript" src="lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="script/cookie.js"></script>
<script type="text/javascript" src="script/validate.js"></script>
<script type="text/javascript" src="script/show_message.js"></script>
<script type="text/javascript" src="script/show_form_message.js"></script>
<script type="text/javascript" src="script/show_login.js"></script>
<script type="text/javascript">
	validate("validate-answer.jsp", "#answer-form", "#submit-button", true);
</script>
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
<%try { 
	temp = question.getDescription().split("<br/>");
%>
<%="<h1 style=\"font-size:20px;\">" +question.getTitle() + "</h1>" %>
<div class="view-content" style="border:none;">
<div class="content-main">
<% for(String paragraph: temp){ %>
<p><%=paragraph %></p>
<%} %>
</div>
<div class="content-info">
<p><%=question.getName() %> asked at <%=Question.getDisplayDateFormat().format(question.getPostDate()) %></p>
<p>Email: <%=question.getEmail() %></p>
<%if(question.isModified()){ %><p class="modified"><%="[modified by admin at " + Question.getDisplayDateFormat().format(question.getModifiedDate())+"]"%>&nbsp;</p><%} %>
</div>
</div>
<%} catch(NullPointerException e) {
	e.printStackTrace();
	response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
} if(answer !=null){ %>
<div class="view-content" style="border:none;">
<div class="content-main">
<h3>Answer</h3>
<p><%=answer.getDescription()  %></p>
</div>
<div class="content-info">
<div>
<p>Admin answered at <%=Answer.getDisplayDateFormat().format(answer.getPostDate()) %></p>
</div>
<%if(answer.isModified()){ %><p class="modified"><%="[modified by admin at " + Question.getDisplayDateFormat().format(question.getModifiedDate())+"]"%>&nbsp;</p><%} %>
</div>
</div>
<h2 style="margin-top:50px;">Edit :</h2>
<form id="answer-form"  action="edit-answer.jsp" method="post">
<p><textarea name="description"></textarea></p>
<input type="hidden" name="id" value="<%=answer.getId() %>"/>
<button id="submit-button">Submit</button>
</form>
<%} else {%>
<h2 style="margin-top:50px;">Answer :</h2>
<form id="answer-form" action="answer-question.jsp" method="post">
<p><textarea name="description"></textarea></p>
<input type="hidden" name="id" value="<%=question.getId() %>"/>
<button id="submit-button">Submit</button>
</form>
<%} %>
<hr/>
<a href="admin-panel.jsp" class="button" style="color:#FFF;">Go Back</a>
</div>

<div id="footer"><p>Copyright © 2012 LIBT. All Rights Reserved. | <a href="reference.html">Reference</a> | <a href="admin-panel.jsp" id="open-login-box">admin panel</a></p></div>
</div>


</body>
</html>
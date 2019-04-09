<%@ page language="java" contentType="text/html;  charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Object id = request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Edit Question - Brunel Computing</title>
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
<a class="link-button" href="edit-question.html">ASK QUESTION</a>
</div>
</div>

<div id="main-content">
<h1>Edit : </h1>
<form id="edit-question" action="edit-question.jsp" method="post">
<p><label for="title">Title :</label><input type="text" name="title" id="input-title" class="input-text"/></p>
<p><textarea name="description"></textarea></p>
<input type="hidden" name="id" value="<%=id %>"/>
<input type="hidden" name="e_validate" value="0"/>
<input type="submit" value="Submit" class="button"/>
</form>

<hr/>
<a href="admin-panel.jsp" class="button" style="color:#FFF;">Go Back</a>
</div>

<div id="footer"><p>Copyright © 2012 LIBT. All Rights Reserved. | <a href="reference.html">Reference</a> | <a href="admin-panel.jsp" id="open-login-box">admin panel</a></p></div>
</div>


</body>
</html>
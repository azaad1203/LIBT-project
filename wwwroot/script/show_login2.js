$(document).ready(function() {
	if(typeof(getCookie("login")) == 'undefined' || getCookie("login") == null){
	$("#open-login-box").attr("href", "#");
	$("#open-login-box").bind("click", function(){
		var header = $('<h1>Login</h1>');
		var form = $('<form></form>');
		form.attr("id", "login-form");
		form.attr("action", "../login.jsp");
		form.attr("method", "post");
		var usname_label = $('<label>User Name :</label>');
		var pw_label = $('<label>Password :</label>');
		var usname_input = $('<input></input>');
		var pw_input = $('<input></input>');
		var br = $('<div></div>');
		var button = $('<button>Log In</button>');
		
		usname_label.attr("for", "username");
		pw_label.attr("for", "passwords");
		usname_input.attr("type", "text");
		pw_input.attr("type", "password");
		usname_input.attr("name", "username");
		pw_input.attr("name", "password");
		br.attr("style", "clear: both;");
		button.attr("id", "login-button");
		
		form.append(usname_label);
		form.append(usname_input);
		form.append(pw_label);
		form.append(pw_input);
		form.append(br);
		form.append(button);
		
		showMessage(header, form , "login");
		validate("../validate-login.jsp", "#login-form", "#login-button", false, ["login","ok"]);
		
		});
	}
});
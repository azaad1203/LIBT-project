var validate = function(action ,formSelector, buttonSelector, ready, cookie){
	var execute = function(action ,formSelector, buttonSelector){
	    $("body").find(buttonSelector).on("click", function(event){
	    	event.preventDefault();
	    	var button =  $("body").find(buttonSelector);
	    	var form = $("body").find(formSelector);
	        button.attr("disabled", "disabled");
	        $.post(action, form.serialize(), function(msg) {
	           	if(msg == "ok"){
	           		if(Object.prototype.toString.call( cookie ) === '[object Array]'){
	           			setCookie(cookie[0], cookie[1], 1);
	           		}
	           		form.submit();
	        	} else{
	            	button.removeAttr("disabled");
	            	showFormMessage(msg, formSelector);
	        	}
	       });
	    });

	};
	
	if(ready == true){
		$(document).ready(function() {
			execute(action ,formSelector, buttonSelector);
		});
	} else {
		execute(action ,formSelector, buttonSelector);
	}

};
var showFormMessage = function(content, formSelector){	
	
	var warningImg = $('<img></img>');
	warningImg.attr("src", "img/alert.png");
	
	contentBox = $('<div></div>');
	contentBox.append(content);
	
	message_box = $('<div></div>');
	message_box.attr("id", "form-message");
	message_box.append(warningImg);
	message_box.append(contentBox);
	

	$("body").find("#form-message").remove();
	$("body").find(formSelector).before(message_box);

};
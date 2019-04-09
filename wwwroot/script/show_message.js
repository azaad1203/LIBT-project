var showMessage = function(header, content ,inner_box_id, remove){
	if(typeof(remove)==='undefined') {remove = true;}
	info_box = $('<div></div>');
	info_box.attr("id", inner_box_id);
	info_box.append(header);
	info_box.append(content);
	
	if(remove == true){
		info_box.bind("click", function(event){
			event.stopPropagation();
		});
	}
			        	      
	message_box = $('<div></div>');
	message_box.attr("id", "message-box");
	message_box.append(info_box);
			        	   
	if(remove == true){
		message_box.bind("click", function(){
			$(this).remove();
		});
	}
	
	
	$("body").append(message_box);
};
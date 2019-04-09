function showParagraph() {
$(".content-header").removeClass("content-header_activated");
$(".content-paragraph").hide()
	
$(this).addClass("content-header_activated");
var paragraph = $(this).next();
paragraph.show();
}; 

function hideParagraph() {
$(this).removeClass("content-header_activated");

var paragraph = $(this).next();
paragraph.hide();
}; 

$(document).ready(function() {
$(".content-header").toggle(showParagraph, hideParagraph);
});
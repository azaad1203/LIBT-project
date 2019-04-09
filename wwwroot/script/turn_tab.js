function slideTabTo01() {
$(this).addClass("activated_tab");
$("#tab_02").removeClass("activated_tab");
$("#tab-content_02").hide();
$("#tab-content_01").show();
}; 

function slideTabTo02() {
$(this).addClass("activated_tab");
$("#tab_01").removeClass("activated_tab");
$("#tab-content_01").hide();
$("#tab-content_02").show();
}; 

$(document).ready(function() {
$("#tab_01").bind("click", slideTabTo01);
$("#tab_02").bind("click", slideTabTo02);
});
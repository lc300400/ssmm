$(function(){
	//对具有data-full和data-fun的dom元素绑定事件
	$('body').delegate("a[data-full]","click",function(){
		var full = $(this).data("full");
		var fun =$(this).data("fun");
		console.debug(full);
		window[fun](full);
	});
});
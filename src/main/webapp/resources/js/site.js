/* =======================================================================
 * 弹出层-打开关闭
 * ======================================================================== */
/*  打开弹出层
	参数解释：
	title	标题
	url		请求的url
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
function layer_show(title, url, w, h){
	if (title == null || title == "") {
		title = false;
	}
	if (url == null || url == "") {
		url = "404.html";
	}
	if (w == null || w == "") {
		w = 800;
	}
	if (h == null || h == "") {
		h = ($(window).height() - 50);
	}
	layer.open({
		type: 2,
		area: [w+'px', h +'px'],
		fix: false, //不固定
		maxmin: true,
		shade: 0.4,
		title: title,
		content: url
	});
}

/* 关闭弹出框口 */
function layer_close(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}

/* =======================================================================
 * 左侧菜单-隐藏显示
 * ======================================================================== */
function displaynavbar(obj){
	if($(obj).hasClass("open")){
		$(obj).removeClass("open");
		$("body").removeClass("big-page");
	} else {
		$(obj).addClass("open");
		$("body").addClass("big-page");
	}
}

/* =======================================================================
 * 左侧菜单-展开折叠
 * ========================================================================*/
function Huifold(obj, obj_c, speed, obj_type, Event) {
	if (obj_type == 2) {
		$(obj + ":first").find("b").html("-");
		$(obj_c + ":first").show();
	}
	$(obj).on(Event, function() {
		if ($(this).next().is(":visible")) {
			if (obj_type == 2) {
				return false;
			} else {
				$(this).next().slideUp(speed).end().removeClass("selected");
				if ($(this).find("b")) {
					$(this).find("b").html("+");
				}
			}
		} else {
			if (obj_type == 3) {
				$(this).next().slideDown(speed).end().addClass("selected");
				if ($(this).find("b")) {
					$(this).find("b").html("-");
				}
			} else {
				$(obj_c).slideUp(speed);
				$(obj).removeClass("selected");
				if ($(this).find("b")) {
					$(obj).find("b").html("+");
				}
				$(this).next().slideDown(speed).end().addClass("selected");
				if ($(this).find("b")) {
					$(this).find("b").html("-");
				}
			}
		}
		
		//暂时解决三级菜单为展开的问题.
		$(this).next().find("dl>dd:has(li[class='current'])").prev().click();
	});
}

function Huifold2(obj, obj_c, speed) {
	$(obj).on("click", function() {
		if ($(this).next().is(":visible")) {
			$(this).next().slideUp(speed).end().removeClass("selected");
		} else {
			$(obj_c).slideUp(speed);
			$(obj).removeClass("selected");
			$(this).next().slideDown(speed).end().addClass("selected");
		}
	});
}

$(function(){
	/*下拉菜单*/
	$(document)
		.on("mouseenter", ".dropDown", function(){ $(this).addClass("hover"); })
		.on("mouseleave", ".dropDown", function(){ $(this).removeClass("hover"); })
		.on("mouseenter", ".dropDown_hover", function(){ $(this).addClass("open"); })
		.on("mouseleave", ".dropDown_hover", function(){ $(this).removeClass("open"); })
		.on("click", ".dropDown-menu li a", function(){ $(".dropDown").removeClass('open'); })
		.on("mouseenter", ".menu > li", function(){ $(this).addClass("open"); })
		.on("mouseleave", ".menu > li", function(){ $(this).removeClass("open"); });
	
	/*左侧菜单*/
	//Huifold(".menu_dropdown dl dt",".menu_dropdown dl dd","fast",1,"click");
	Huifold(".menu_dropdown > dl > dt",".menu_dropdown > dl > dd","fast",1,"click");
	Huifold2(".menu_dropdown dd dl > dt",".menu_dropdown dd dl > dd","fast");
});


/* 获取当前日期 ,格式yyyy-MM-dd */
function getNowDate(){
	var date = new Date();
	var seperator = "-";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentDate = date.getFullYear() + seperator + month + seperator + strDate;
	return currentDate;
}

/* 获取当前日期时间 ,格式yyyy-MM-dd HH:mm:ss */
function getNowDateTime(){
	var date = new Date();
	var seperator1 = "-";
	var seperator2 = ":";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentDatetime = date.getFullYear() + seperator1 + month + seperator1 + strDate + " "
						+ date.getHours() + seperator2 + date.getMinutes() + seperator2 + date.getSeconds();
	return currentDatetime;
}
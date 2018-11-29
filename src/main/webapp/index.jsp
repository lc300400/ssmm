<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>
<link rel="icon" href="favicon.png" type="image/png" />
<link rel="bookmark" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="lib/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="lib/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="lib/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="lib/h-ui.admin/css/style.css" />
<title>嘉捷信诚管理平台</title>
</head>
<body>
<header class="navbar-wrapper">
<div class="navtemp">
  <div class="topleft"><span><img src="${ctx}/resources/images/logo.png"></span><span>嘉捷信诚管理平台</span></div>
  <div class="topright">
    <div class="toprighttwo" id="Hui-userbar">
      <ul>
        <li><span><img src="${ctx}/resources/icon/user.png"></span><a href="javascript:;" onclick="personalInfo()">个人信息</a></li>
        <li><span><img src="${ctx}/resources/icon/news.png"></span><a href="javascript:;">消息</a></li>
        <li><span><img src="${ctx}/resources/icon/password.png"></span><a href="javascript:;" onClick="updatePwd()">密码修改</a></li>
        <li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i><span>换肤</span></a>
		<ul class="dropDown-menu menu radius box-shadow">
			<li><a href="javascript:;" data-val="default" title="默认（黑色）">默认</a></li>
			<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
			<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
			<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
			<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
			<li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
		</ul>
		</li>
        <li><span><img src="${ctx}/resources/icon/quit.png"></span><a href="#" onclick="logoutFun()">退出</a></li>
      </ul>
    </div>
    <div class="toprightone"> <span>欢迎您:[<shiro:principal/>]</span> <span id="nowTime_span"></span> </div>
  </div>
  </div>
</header>
<aside class="Hui-aside">
 	<jsp:include page="/common/menu.jsp"/>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
  <div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
    <div class="Hui-tabNav-wp">
      <ul id="min_title_list" class="acrossTab cl">
        <li class="active"> <span title="我的桌面" data-href="javascript:void(0);">我的桌面</span> <em></em></li>
      </ul>
    </div>
    <div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
  </div>
  <div id="iframe_box" class="Hui-article">
    <div class="show_iframe">
      <div style="display:none" class="loading"></div>
      <iframe id="iframeSon" scrolling="yes" frameborder="0" src="javascript:void(0);"></iframe>
    </div>
  </div>
</section>
<div class="contextMenu" id="Huiadminmenu">
  <ul>
    <li id="closethis">关闭当前 </li>
    <li id="closeall">关闭全部 </li>
  </ul>
</div>
<!--_footer 作为公共模版分离出去--> 
<jsp:include page="/common/footer.jsp"/>

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script> 
<script type="text/javascript">
$(function(){
	showNowTime("nowTime_span");
	$("#min_title_list li").contextMenu('Huiadminmenu', {
		bindings: {
			'closethis': function(t) {
				console.log(t);
				if(t.find("i")){
					t.find("i").trigger("click");
				}		
			},
			'closeall': function(t) {
				alert('Trigger was '+t.id+'\nAction was Email');
			},
		}
	});
});

/*查看个人信息*/
function personalInfo(){
	layer.open({
		type: 2,
		area: ["400px", "400px"],
		maxmin: true,
		title: "个人信息",
		content: "${ctx}/security/user/getPersonalInfo"
	});
}

/*密码修改*/
function updatePwd(){
	layer.open({
		type: 2,
		area: ["800px", "400px"],
		maxmin: true,
		title: "密码修改",
		content: "${ctx}/pages/personal/updatePwd.jsp"
	});
}
</script> 
<script type="text/javascript">
	//退出系统
	function logoutFun(){
		layer.confirm("确认要离开吗？", function(index){
			$.ajax({ 
		        type: "post", 
		        url: "${ctx}/login/logout.json", 
		        dataType: "json", 
		        success: function(data) { 
		        	//推出成功
	                window.location.href = "${ctx}/index.jsp";
		        },
		        error: function(data) { 
		            alert("调用失败...."); 
		        }
		    });
		})
	}
</script>
<script type="text/javascript">
/* 
 * 指定格式显示时间 
 * 2018年10月26日 星期五 14::25:32
 * */
function showNowTime(str){
	getCurrentDate(str);
	window.setInterval(function(){
		getCurrentDate(str);
	},1000);
}
function getCurrentDate(str){
    var today,hour,second,minute,year,month,date;
    var strDate ;
    today=new Date();
    var n_day = today.getDay();
    switch (n_day)
    {
        case 0:{
          strDate = "星期日"
        }break;
        case 1:{
          strDate = "星期一"
        }break;
        case 2:{
          strDate ="星期二"
        }break;
        case 3:{
          strDate = "星期三"
        }break;
        case 4:{
          strDate = "星期四"
        }break;
        case 5:{
          strDate = "星期五"
        }break;
        case 6:{
          strDate = "星期六"
        }break;
        case 7:{
          strDate = "星期日"
        }break;
    }
    year = today.getYear()+1900;
    month = today.getMonth()+1;
    if(month<10){
        month="0"+month;
    }
    date = today.getDate();
    if(date<10){
        date="0"+date;
    }
    hour = today.getHours();
    if(hour<10){
        hour="0"+hour;
    }
    minute =today.getMinutes();
    if(minute<10){
        minute="0"+minute;
    }
    second = today.getSeconds();
    if(second<10){
        second="0"+second;
    }
    //2018年9月27号 11:12:56
    var timeStr = "现在是："+year + "年" + month + "月" + date + "日 " + strDate +" " + hour + ":" + minute + ":" + second; //显示时间
    $("#"+str).html(timeStr); 
    //window.setTimeout('showCurrentDate()',1000);
}
</script>
</body>
</html>
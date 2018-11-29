<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>
	<link rel="icon" href="favicon.png" type="image/png" />
	<link rel="bookmark" href="favicon.ico" />
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<title>系统登陆</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jiajiestyle.css" />
</head>
<body>
	<div class="bigdiv01"></div>
	<div class="bigdiv02"></div>
	<div>
		<div class="div01">
			<div class="div02"> 管理系统V1.0</div>
			<div class="div03">
				<div class="div03left">
					<table>
						<tr>
							<td class="div04">用户名：</td><td><input class="input01" type="text" id="username"/></td>
						</tr>
						<tr>
							<td class="div04">密码 ：</td><td><input class="input01" type="password" id="password"/></td>
						</tr>
					</table>
				</div>
				<div class="div03right"><a class="div05" href="#" id="loginbtn">登&nbsp;录</a></div>
			</div>
		</div>
	</div>	
</body>
<jsp:include page="/common/footer.jsp"/>
<script type="text/javascript">
$(function(){
	if(window !=top){  
	    top.location.href=location.href;  
	} 
	document.onkeydown = function (e) {
        if (!e) e = window.event;
        if ((e.keyCode || e.which) == 13) {
            $("#loginbtn").click();
        }
    }
});
</script>
<script type="text/javascript">
$("#loginbtn").click(function() {
    var param = {
        username : $("#username").val(),
        password : $("#password").val()
    };
    $.ajax({ 
        type: "post", 
        url: "${ctx}/login/checkLogin.json", 
        data: param, 
        dataType: "json", 
        success: function(data) {
            if(data.success == true){
            	//登录成功
                window.location.href = "${ctx}/index.jsp";
            }else{
                layer.msg("账户或密码错误！");
            }
        },
        error: function(data) { 
            alert("调用失败...."); 
        }
    });
});
</script>
</html>

<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<jsp:include page="/common/meta.jsp"/>
</head>
<body>
<article class="page-container">
	<form id="inputForm" class="form form-horizontal" action="${ctx}/security/user/updatePwd" method="post">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="oldPassword"><span class="c-red">*</span>旧密码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="password" class="input-text required" id="oldPassword" name="oldPassword" placeholder="请输入现在的密码" maxlength="20">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="newPassword"><span class="c-red">*</span>新密码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="password" class="input-text required" id="newPassword" name="newPassword" placeholder="请输入至少六位的新密码" minlength="6" maxlength="20">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="confirmPassword"><span class="c-red">*</span>确认密码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="password" class="input-text required" id="confirmPassword" name="confirmPassword" placeholder="请再次输入新密码" maxlength="20">
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<button class="btn btn-secondary" type="submit"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
				<button onClick="cancelFun();" class="btn btn-default" type="button"><i class="Hui-iconfont">&#xe6a6;</i>取消</button>
			</div>
		</div>
	</form>
</article>
<jsp:include page="/common/footer.jsp"/>
<!-- 请在下方写此页面业务相关的脚本 -->
<script type="text/javascript">
$(function(){
	$("#inputForm").validate({
		rules: {
			confirmPassword: { equalTo: "#newPassword" }
		},
		messages: {
			confirmPassword: { equalTo: "两次输入的密码不一致" }
		},
		focusCleanup: true,
		submitHandler: function(form){
			$(form).ajaxSubmit({
				success: function(data){
					if(data.success){
						layer.msg(data.msg, {icon:1});
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);
					} else {
						layer.msg(data.msg, {icon:5});
					}
				}
			});
		}
	});
});

//取消操作
function cancelFun(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}

</script>
<!-- /请在上方写此页面业务相关的脚本 -->
</body>
</html>

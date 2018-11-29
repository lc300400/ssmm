<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<jsp:include page="/common/meta.jsp"/>
</head>
<body>
<article class="page-container">
	<form id="inputForm" class="form form-horizontal" action="" method="post">
		<div class="row cl">
			<label class="col-xs-4 col-sm-3 text-r">用户账号：</label>
			<div class="col-xs-8 col-sm-9">${user.username}</div>
		</div>
		<div class="row cl">
			<label class="col-xs-4 col-sm-3 text-r">用户姓名：</label>
			<div class="col-xs-8 col-sm-9">${user.name}</div>
		</div>
		<div class="row cl">
			<label class="col-xs-4 col-sm-3 text-r">手机号码：</label>
			<div class="col-xs-8 col-sm-9">${user.mobile}</div>
		</div>
		<div class="row cl">
			<label class="col-xs-4 col-sm-3 text-r">电子邮箱：</label>
			<div class="col-xs-8 col-sm-9">${user.email}</div>
		</div>
		<div class="row cl">
			<label class="col-xs-4 col-sm-3 text-r">所属部门：</label>
			<div class="col-xs-8 col-sm-9">${user.dept.name}</div>
		</div>
		<%--
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3 text-r">用户角色：</label>
			<div class="formControls col-xs-8 col-sm-9">${user.roleNames}</div>
		</div>
		--%>
	</form>
</article>
<!-- 请在下方写此页面业务相关的脚本 -->
<!-- /请在上方写此页面业务相关的脚本 -->
</body>
</html>

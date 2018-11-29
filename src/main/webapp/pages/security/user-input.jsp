<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<jsp:include page="/common/meta.jsp"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/lib/jquery-easyui-1.5.2/themes/default/easyui.css">
	<style type="text/css">
		#roleCheckboxs > label {display:block; margin-bottom:.5em; width:auto; text-align:left; cursor:pointer;}
		#roleCheckboxs > input {display:block; float:left; margin-bottom:.5em; margin-right:5px; cursor:pointer;}
	</style>
</head>
<body>
<article class="page-container">
	<div class="cl pd-15 bg-1 bk-gray" style="background-color:#FCF8E3;">
 		提示：新用户的默认密码为<span class="label label-danger">${defaultPassword}
	</div>
	<form id="inputForm" class="form form-horizontal" action="${ctx}/security/user/save" method="post">
		<div class="row cl">
			<input type="hidden" name="id" value="${user.id}">
			<label class="form-label col-xs-4 col-sm-2" for="username"><span class="c-red">*</span>用户账号：</label>
			<div class="form-label col-xs-8 col-sm-9">
				<c:if test="${user.id !=null}">
					<input type="text" class="input-text required" value="${user.username}" placeholder="用户账号" maxlength="50" disabled="disabled"/>
					<input type="hidden" name="username" value="${user.username}">
				</c:if>
				<c:if test="${user.id ==null}">
					<input type="text" class="input-text required" id="username" name="username" value="${user.username}" placeholder="用户账号" maxlength="50">
				</c:if>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="name"><span class="c-red">*</span>用户姓名：</label>
			<div class="form-label col-xs-8 col-sm-9">
				<input type="text" class="input-text required" id="name" name="name" value="${user.name}" placeholder="用户姓名" maxlength="50">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="mobile">手机号码：</label>
			<div class="form-label col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="mobile" name="mobile" value="${user.mobile}" placeholder="手机号码" maxlength="50">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="email">电子邮箱：</label>
			<div class="form-label col-xs-8 col-sm-9">
				<input type="email" class="input-text email" id="email" name="email" value="${user.email}" placeholder="电子邮箱" maxlength="50">
			</div>
		</div>
		<div class="row cl" style="margin-top:1em;margin-bottom:1em;">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>所属部门：</label>
			<div class="form-label col-xs-8 col-sm-9">
				<input class="easyui-combotree" style="width:100%;height:34px;padding-left:12px;" name="deptId" value="${user.dept.id}" data-options="url:'${ctx}/security/dept/getEasyUITree.json'">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">用户角色：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<div class="skin-minimal" id="roleCheckboxs">
					<input type="hidden" id="roleChecked" name="roleChecked"/>
					<c:forEach items="${roleList}" var="role" > 
						  <div class="check-box">
						    <input type="checkbox" name="roleCheck" value="${role.id}"
						    class="icheckbox-blue" id="checkbox-${role.id}">
						    <label class="checkbox sc-cSHVUG hlIZAx"
						    for="checkbox-${role.id}">${role.name}</label>
						  </div><br>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<button class="btn defaultColor" type="submit"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
				<button onClick="cancelFun();" class="btn btn-default" type="button"><i class="Hui-iconfont">&#xe6a6;</i>取消</button>
			</div>
		</div>
	</form>
</article>

<jsp:include page="/common/footer.jsp"/>
<!-- 请在下方写此页面业务相关的脚本 -->
<script src="${ctx}/lib/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script src="${ctx}/lib/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
$(function(){
	$("#inputForm").validate({
		rules: {
			username: { remote: "${ctx}/security/user/checkUsernameUnique?oldUsername=" + encodeURIComponent("${username}") }
		},
		messages: {
			username: { remote: "用户帐号已经存在" }
		},
		focusCleanup: true,
		submitHandler: function(form){
			var deptId = $("input[name='deptId']").val();
			if(deptId == "") {
				layer.msg("请选择用户所属部门！", {icon:0});
				return false;
			}
			getSelectedRole();
			$(form).ajaxSubmit({
				success: function(data){
					if(data.success){
						parent.layer.msg(data.msg, { icon:1, time:1000 });
						parent.$("#refreshBtn").click();
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);
					} else {
						layer.msg(data.msg, {icon:5});
					}
				}
			});
		}
	});
	$("#username").focus();
	selectedCheckedRole();
});

//获取选中的角色
function getSelectedRole(){
	//组装已选角色信息
	var roles = [];  
    $("input:checkbox[name='roleCheck']:checked").each(function() {  
    	roles.push($(this).val());  
    });
    var roleCheckedStr="";
    $.each(roles,function(i){
    	roleCheckedStr += this+",";
    });
	$("#roleChecked").val(roleCheckedStr);
}

//默认勾选用户已有角色信息
function selectedCheckedRole(){
	var rl = JSON.parse('${checkedRoleIds}');
	if(rl!=null && rl != "" && rl.length>0 ){
		$.each(rl,function(){
			$("#checkbox-"+this).attr("checked","checked");
		});
	}
	
}

// 取消操作
function cancelFun(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}
</script>
<!-- /请在上方写此页面业务相关的脚本 -->
</body>
</html>

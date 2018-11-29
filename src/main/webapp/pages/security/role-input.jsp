<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<jsp:include page="/common/meta.jsp"/>
	<style type="text/css">
	#authCheckboxs > label {display:block; margin-bottom:.5em; width:auto; text-align:left; cursor:pointer;}
	#authCheckboxs > input {display:block; float:left; margin-bottom:.5em; margin-right:5px; cursor:pointer;}
	</style>
</head>
<body>
<article class="page-container">
	<form id="inputForm" class="form form-horizontal" action="${ctx}/security/role/save" method="post">
		<div class="row cl">
			<input type="hidden" id="role_id_hidden" name="id" value="${role.id}"/>
			<label class="form-label col-xs-4 col-sm-2" for="name"><span class="c-red">*</span>角色名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="hidden" class="" id="oldName" name="oldName" value="${role.name}" />
				<input type="text" class="input-text required" id="name" name="name" value="${role.name}" placeholder="角色名称" maxlength="50">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="description"><span class="c-red">*</span>角色描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text required" id="description" name="description" value="${role.description}" placeholder="角色描述" maxlength="100">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">角色权限：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<div class="skin-minimal" id="authCheckboxs">
					<input type="hidden" id="authChecked" name="authChecked"/>
					<c:forEach items="${authorityList}" var="auth" > 
						  <div class="check-box">
						    <input type="checkbox" name="authCheck" value="${ auth.id }"
						    class="icheckbox-blue" id="checkbox-${ auth.id }">
						    <label class="checkbox sc-cSHVUG hlIZAx"
						    for="checkbox-${ auth.id }">${ auth.description }</label>
						  </div><br>
					
					</c:forEach>
				<!-- <s:checkboxlist name="checkedAuthIds" list="allAuthorityList" 
				listKey="id" listValue="description"/> -->
				</div>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<button type="submit" class="btn defaultColor"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
				<button type="button" class="btn btn-default" onclick="cancelFun()"><i class="Hui-iconfont">&#xe6a6;</i>取消</button>
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
			name: { remote: "${ctx}/security/role/checkNameUnique.action?oldName=" + $("#oldName").val() }
		},
		messages: {
			name: { remote: "角色名称已经存在" }
		},
		focusCleanup: true,
		submitHandler: function(form){
			getSelectedAuthority();
			$(form).ajaxSubmit({
				success: function(data){
					if(data.success){
						parent.layer.msg(data.msg, { icon:1, time:1000 });
						parent.dataTable.api().ajax.reload(null,false);
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);
					} else {
						layer.msg(data.msg, {icon:5});
					}
				}
			});
		}
	});
	$("#name").focus();
	selectedCheckedAuthority();
});

//获取选中的权限
function getSelectedAuthority(){
	//已选权限封装
	var auths = [];  
    $("input:checkbox[name='authCheck']:checked").each(function() {  
    	auths.push($(this).val());  
    });
    var authCheckedStr="";
    $.each(auths,function(i){
    	authCheckedStr += this+",";
    });
	$("#authChecked").val(authCheckedStr);
}

function selectedCheckedAuthority(){

	var rl = JSON.parse('${ checkedAuthorityIds }');
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

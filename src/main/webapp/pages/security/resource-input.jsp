<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<jsp:include page="/common/meta.jsp"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/lib/jquery-easyui-1.5.2/themes/default/easyui.css">
</head>
<body>
<article class="pd-20">
	<div class="cl pd-15 bg-1 bk-gray" style="background-color:#FCF8E3;">
		提示：系统最多支持三级菜单； 一级资源必须是“菜单”；类型为“功能”的资源必须指定上级资源。
	</div>
	<form id="inputForm" class="form form-horizontal" action="${ctx}/security/resource/save" method="post">
		<input type="hidden" name="id" id="id" value="${ resource.id }"/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">上级资源：</label>
			<input type="hidden" id="pid_hidden_value" value="${ resource.pid }"/>
			<div class="formControls col-xs-8 col-sm-9">
				<input class="easyui-combotree" id="pid_tree_input"
					style="width:100%;height:34px;padding-left:12px;" name="pid" 
					value="${resource.pid}" 
					data-options="url:'${ctx}/security/resource/getEasyUITree'">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="type"><span class="c-red">*</span> 资源类型：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="hidden" id="type_hidden_value" value="${ resource.type }"/>
				<select id="resource_type_sel" name="type" class="easyui-combobox" 
				 style="width:100%;height:34px;padding-left:12px;" editable="false">
					<option value="1">菜单</option>
					<option value="2">功能</option>
				</select>
				<%-- <s:hidden name="id" value="${ resource.id }" />
				<s:select cssClass="form-control required" id="type" name="type" 
				 list="#{1:'菜单',2:'功能'}"/> --%>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="name"><span class="c-red">*</span> 资源名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text required" id="name" name="name" value="${resource.name}" placeholder="资源名称" maxlength="50">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="url">资源路径：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="url" name="url" value="${resource.url}" placeholder="资源路径" maxlength="100">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="menuId">资源标识：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="menuId" name="menuId" value="${resource.menuId}" placeholder="菜单唯一标识" maxlength="50">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="icon">资源图标：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="icon" name="icon" value="${resource.icon}" placeholder="菜单图标" maxlength="50">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="description">资源描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="description" name="description" value="${resource.description}" placeholder="资源功能描述" maxlength="100">
			</div>
		</div>
		<c:if test="${resource.id != null}">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="seq"><span class="c-red">*</span>显示顺序：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text digits" id="seq" name="seq" value="${resource.seq}" placeholder="显示顺序" maxlength="5">
			</div>
		</div>
		</c:if>
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
	selectsByData();
	
	$("#inputForm").validate({
		focusCleanup: true,
		submitHandler: function(form){
			var type = $("#type").val();
			var pid = $("input[name='pid']").val();
			if(type == "2" && pid == "") {
				layer.msg("类型为“功能”的资源必须指定上级资源！", {icon:0});
				return false;
			}
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
	$("#name").focus();
});

// 取消操作
function cancelFun(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}

function selectsByData(){
	var type_value = $("#type_hidden_value").val();
	var pid_value = $("#pid_hidden_value").val();
	
	if(type_value != "" && type_value !=null ){
		$("#resource_type_sel").combobox('select',type_value);
	}
	if(pid_value != "" && pid_value !=null ){
		$("#pid_tree_input").combotree('setValue',pid_value);
	}
}
</script>
<!-- /请在上方写此页面业务相关的脚本 -->
</body>
</html>

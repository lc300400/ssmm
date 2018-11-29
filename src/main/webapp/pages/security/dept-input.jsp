<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<jsp:include page="/common/meta.jsp"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/lib/jquery-easyui-1.5.2/themes/default/easyui.css">
</head>
<body>
<article class="page-container">
	<form id="inputForm" class="form form-horizontal" action="${ctx}/security/dept/save.do" method="post">
		<div class="row cl">
			<input type="hidden" name="id" value="${entity.id}"/>
			<label class="form-label col-xs-4 col-sm-2" for="name"><span class="c-red">*</span>部门名称：</label>
			<div class="form-label col-xs-8 col-sm-9">
				<input type="text" class="input-text required" id="name" name="name" value="${entity.name}" placeholder="部门名称" maxlength="50">
			</div>
			
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="description">部门描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="description" name="description" value="${entity.description}" placeholder="部门描述" maxlength="100">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="linkman">联系人：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="linkman" name="linkman" value="${entity.linkman}" placeholder="联系人" maxlength="50">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="tel">联系电话：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" id="tel" name="tel" value="${entity.tel}" placeholder="联系电话" maxlength="50">
			</div>
		</div>
		<c:choose>
			<c:when test="${entity.id == null}">
				<input type="hidden" name="type" value="4"/>
				<div class="row cl" style="margin-top:1em;margin-bottom:1em;">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>上级部门：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input class="easyui-combotree" style="width:100%;height:34px;padding-left:12px;" name="pid" value="${entity.parent.id}" data-options="url:'${ctx}/security/dept/getEasyUITree.json'">
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2" for="seq">显示顺序：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text digits" id="seq" name="seq" value="${entity.seq}" placeholder="显示顺序" maxlength="5">
					</div>
				</div>
				<input type="hidden" name="type" value="4"/>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2" for="parent">上级部门：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" id="parent" value="${entity.parent.name}" disabled="disabled">
					</div>
				</div>
			</c:otherwise>
		</c:choose>
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
		focusCleanup: true,
		submitHandler: function(form){
			var type = $("#type").val();
			var pid = $("input[name='pid']").val();
			if(type != "1" && pid == "") {
				layer.msg("请选择上级部门！", {icon:0});
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
</script>
<!-- /请在上方写此页面业务相关的脚本 -->
</body>
</html>

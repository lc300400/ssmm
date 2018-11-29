<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<jsp:include page="/common/meta.jsp"/>
</head>
<body>
<article class="page-container">
	<form id="inputForm" class="form form-horizontal" action="${ctx}/security/parameter/save.form" method="post">
		<div class="row cl">
			<input type="hidden" name="id" value="${parameter.id}"/>
			<label class="form-label col-xs-4 col-sm-2" for="name"><span class="c-red">*</span>参数名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text required" id="name" value="${parameter.name}" disabled="disabled">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="value"><span class="c-red">*</span>参数名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text required" id="value" name="value" value="${parameter.value}">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">参数说明：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea name="remark" cols="" rows="" class="textarea"  placeholder="说点什么...">${parameter.remark}</textarea>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<button class="btn defaultColor" type="submit"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
				<button onClick="cancelFun();" class="btn btn-default" type="button">
					<i class="Hui-iconfont">&#xe6a6;</i>取消</button>
			</div>
		</div>
	</form>
</article>

<jsp:include page="/common/footer.jsp"/>
<!-- 请在下方写此页面业务相关的脚本 -->
<script type="text/javascript">
$(function(){
	$("#inputForm").validate({
		focusCleanup: true,
		submitHandler: function(form){
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
	$("#value").focus();
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

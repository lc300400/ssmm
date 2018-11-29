<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<jsp:include page="/common/meta.jsp"/>
	<%-- 
	<link rel="stylesheet" type="text/css" href="${ctx}/lib/jquery-easyui-1.5.2/themes/default/easyui.css">
	 --%>
	<link rel="stylesheet" type="text/css" href="${ctx}/lib/zTree-3.5.28/css/zTreeStyle/zTreeStyle.css">
	<style type="text/css">
	.ztree .line {border-top:none}
	</style>
</head>
<body>
<article class="page-container">
	<form id="inputForm" class="form form-horizontal" action="${ctx}/security/authority/save" method="post">
		<div class="row cl">
			<input type="hidden" id="authority_id_hidden" name="id" value="${authority.id}"/>
			<label class="form-label col-xs-4 col-sm-2" for="name"><span class="c-red">*</span>权限名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="hidden" class="" id="oldName" name="oldName" value="${authority.name}" />
				<input type="text" class="input-text required" id="name" name="name" value="${authority.name}" placeholder="权限名称" maxlength="50">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2" for="description"><span class="c-red">*</span>权限描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text required" id="description" name="description" value="${authority.description}" placeholder="权限描述" maxlength="100">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">授权资源：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<!-- <ul class="easyui-tree" id="tree" style="padding-top:0" ></ul> 
				<div id="checkedResourceIds"  style="display:none"></div> -->
				<input type='hidden' id="resourceList_input" name='resourceList_input'>
				<ul class="ztree" id="tree" style="padding-top:0"></ul>
				<div id="checkedResourceIds"  style="display:none"></div>
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
<script src="${ctx}/lib/zTree-3.5.28/js/jquery.ztree.core.min.js"></script>
<script src="${ctx}/lib/zTree-3.5.28/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript">
var treeObj;
var setting = {
	async: {
		enable: true,
		url: "${ctx}/security/resource/getTreeGridData"
	},
	callback: {
		onAsyncSuccess: zTreeOnAsyncSuccess,
		onClick: zTreeOnClick
	},
	check: {
		enable: true
	},
	data: {
		key: {
			url: "eurl"
		}
	},
	view: {
		showIcon: false
	}
};

$(function(){
	$("#inputForm").validate({
		rules: {
			name: { remote: "${ctx}/security/authority/checkNameUnique.action?oldName=" + $("#oldName").val() }
		},
		messages: {
			name: { remote: "权限名称已经存在" }
		},
		focusCleanup: true,
		submitHandler: function(form){
			getSelectedResource();
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
	treeObj = $.fn.zTree.init($("#tree"), setting, null);
});

// 数据加载成功后展开全部节点
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	treeObj.expandAll(true);
	$.each(${checkedResourceIds}, function(){
		var node = treeObj.getNodeByParam("id", this);
		treeObj.checkNode(node, true, false);
	});
}

// 节点单击事件,实现点击复选框或节点名称都能切换选中状态.
function zTreeOnClick(event, treeId, treeNode) {
	treeObj.checkNode(treeNode, !treeNode.checked, true);
}
// 获取已选资源ID组成的数组
function getSelectedResource() {
	/* var nodes = treeObj.getCheckedNodes(true);
	var resourceObj = $("#checkedResourceIds");
	resourceObj.empty();
	$.each(nodes, function(){
		var $input = $("<input type='hidden' name='checkedResourceIds'>");
		$input.val(this.id);
		resourceObj.append($input);
	}); */
	//var nodes = $('#tree').tree('getChecked');
	var nodes = treeObj.getCheckedNodes(true);
	console.log(nodes);
	var resourceList = "";
	
	$.each(nodes, function(){
		resourceList += this.id+",";
	});
	$("#resourceList_input").val(resourceList)
}

// 取消操作
function cancelFun(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}
</script>
</body>
</html>

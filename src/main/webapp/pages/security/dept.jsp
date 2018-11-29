<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<jsp:include page="/common/meta.jsp"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/lib/jquery-easyui-1.5.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/lib/jquery-easyui-1.5.2/themes/icon.css">
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i>
	 首页 <span class="c-gray en">&gt;</span> 
	 系统管理 <span class="c-gray en">&gt;</span>
	 部门管理 <a class="btn radius r defaultColor" 
	 style="line-height:1.6em;margin-top:3px;" 
	 href="javascript:location.replace(location.href);" title="刷新" >
	<i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="page-container">
	<!-- 条件搜索区域 -->
	<!-- /条件搜索区域 -->
	<!-- 用户操作区域 -->
	<div class="cl pd-5 bg-1 bk-gray">
	 	<span class="l"><a href="javascript:;" onclick="addFun()" class="btn defaultColor">
	 	<i class="Hui-iconfont">&#xe600;</i> 新增部门</a> 
	</div>
	<!-- /用户操作区域 -->
	<!-- 数据展示区域 -->
	<div class="mt-20">
		<div id="toolbar" style="display:none;">
			<table>
				<tr>
					<td><a href="javascript:void(0);" style="text-decoration:none;"  data-options="plain:true" onclick="expand();"><i class="Hui-iconfont">&#xe674;</i> 展开&nbsp;&nbsp;</a></td>
					<td><a href="javascript:void(0);" style="text-decoration:none;" data-options="plain:true" onclick="collapse();"><i class="Hui-iconfont">&#xe679;</i> 折叠&nbsp;&nbsp;</a></td>
					<td><div class="datagrid-btn-separator"></div></td>
					<td>&nbsp;<a href="javascript:void(0);" style="text-decoration:none;" data-options="plain:true" onclick="treeGrid.treegrid('reload');" id="refreshBtn"><i class="Hui-iconfont">&#xe67a;</i> 刷新</a></td>
				</tr>
			</table>
		</div>
		<table id="treeGrid" class="easyui-treegrid" style="width:100%;min-height:420px;"
				data-options="
					url: '${ctx}/security/dept/getTreeGridData.json',
					idField: 'id',
					treeField: 'name',
					rownumbers: true,
					toolbar: '#toolbar'
				">
			<thead>
				<tr>
					<th data-options="field:'name',width:'25%'">部门名称</th>
					<th data-options="field:'number',width:'15%'">部门编码</th>
					<th data-options="field:'description',width:'25%'">部门描述</th>
					<th data-options="field:'linkman',width:'12%'">联系人</th>
					<th data-options="field:'tel',width:'15%'">联系电话</th>
					<th data-options="field:'id',width:'8%',formatter:formatterAction">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	<!-- /数据展示区域 -->
</div>

<jsp:include page="/common/footer.jsp"/>
<!-- 请在下方写此页面业务相关的脚本 -->
<script src="${ctx}/lib/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script src="${ctx}/lib/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
var treeGrid;
$(function(){
	/* 表格初始化 */
	treeGrid = $("#treeGrid");
});

// 展开表格
function expand() {
	var node = treeGrid.treegrid("getSelected");
	if(node) {
		treeGrid.treegrid("expandAll", node.id);
	} else {
		treeGrid.treegrid("expandAll");
	}
}

// 折叠表格
function collapse() {
	var node = treeGrid.treegrid("getSelected");
	if(node) {
		treeGrid.treegrid("collapseAll", node.id);
	} else {
		treeGrid.treegrid("collapseAll");
	}
}

// 创建操作列
function formatterAction(value, row, index) {
	var edit = '<a style="text-decoration:none" title="编辑" href="javascript:;" class="ml-5" onclick="editFun(' + row.id + ')"><i class="Hui-iconfont">&#xe6df;</i></a>';
	var del = '<a style="text-decoration:none" title="删除" href="javascript:;" class="ml-5" onclick="delFun(' + row.id + ')"><i class="Hui-iconfont">&#xe6e2;</i></a>';
	var content = edit;
	if(row.children.length == 0) {
		content += del;
	}
	return content;
}

// 新增数据
function addFun(){
	var add = layer.open({
		type: 2,
		area: ["800px", "600px"],
		maxmin: true,
		title: "新增部门",
		content: "${ctx}/security/dept/input.json"
	});
	layer.full(add);
}

// 编辑数据
function editFun(id){
	var edit = layer.open({
		type: 2,
		area: ["800px", "600px"],
		maxmin: true,
		title: "编辑部门",
		content: "${ctx}/security/dept/input.json?id="+id
	});
	layer.full(edit);
}

// 删除数据
function delFun(id){
	layer.confirm("确认要删除吗？", function(index){
		$.ajax({
			url: "${ctx}/security/dept/delete.json?id="+id,
			type: "post",
			success: function(data){
	      		if(data.success){
					layer.msg(data.msg, { icon:1, time:1000 });
					$("#refreshBtn").click();
				} else {
					layer.msg(data.msg, {icon:5});
				}
			}
		});
	});
}
</script>
<!-- /请在上方写此页面业务相关的脚本 -->
</body>
</html>

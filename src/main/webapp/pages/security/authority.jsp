<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<jsp:include page="/common/meta.jsp"/>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i>
		 首页 <span class="c-gray en">&gt;</span> 
		 系统管理 <span class="c-gray en">&gt;</span>
		 权限管理 <a class="btn radius r defaultColor" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
		<i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="page-container">
		<!-- 条件搜索区域 -->
		<div class="text-c">
			<form class="form-inline" id="searchForm">
				<input type="text" class="input-text" name="name" style="width:200px" placeholder=" 权限名称">
				<button name="" id="" class="btn defaultColor" type="button" onclick="search()"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
			</form>
		</div>	
		<br>
		<!-- /条件搜索区域 -->
		<!-- 用户操作区域 -->
		<div class="cl pd-5 bg-1 bk-gray">
		 	<span class="l"><a href="javascript:;" onclick="addFun()" class="btn defaultColor">
		 	<i class="Hui-iconfont">&#xe600;</i> 新增权限</a> 
		</div>
		<!-- /用户操作区域 -->
		<!-- 数据展示区域 -->
		<div class="mt-20">
			<table class="table table-border table-bordered table-bg table-hover text-c table-sort table-responsive" 
			       id="dataTable" style="width:100%;">
				<thead class="text-c">
					<tr>
						<th>序号</th>
						<th>权限名称</th>
						<th>权限描述</th>
						<th width="80px">操作</th>
					</tr>
				</thead>
				<tbody class="text-c"></tbody>
			</table>
		</div>
		<!-- /数据展示区域 -->
		
	</div>
	
<jsp:include page="/common/footer.jsp"/>
<!-- 请在下方写此页面业务相关的脚本 -->
<script type="text/javascript">
var dataTable;
$(function(){
	/* 表格初始化 */
	dataTable = $("#dataTable").dataTable({
		ajax: {
			url: "${ctx}/security/authority/list",
			type: "POST",
			data: function(data){
				delete data.order;
				delete data.search;
				delete data.columns;
				return $.extend({}, data, $("#searchForm").serializeJSON());
			}
		},
		columns: [
			{
				data: null,
				render: function(data, type, full, meta){
					var startIndex = meta.settings._iDisplayStart;
					return startIndex + meta.row + 1;
				}
			},
			{ data: "name" },
			{ data: "description" },
			{
				data: "operate",
				//visible: visibleOperate,
				render: function(data, type, full, meta){
					var edit = '<a title="编辑" style="text-decoration:none" href="javascript:;" class="ml-10" onclick="editFun(' + full.id + ')"><i class="Hui-iconfont Hui-iconfont-edit"></i></a>';
					var del = '<a title="删除" style="text-decoration:none" href="javascript:;" class="ml-10" onclick="delFun(' + full.id + ')"><i class="Hui-iconfont Hui-iconfont-del2"></i></a>';
					var content = edit + del;
					return content;
				}
			}
		]
	});
});

// 搜索数据
function search(){
	dataTable.api().ajax.reload();
}

// 新增数据
function addFun(){
	layer.open({
		type: 2,
		area: ["800px", "500px"],
		maxmin: true,
		title: "新增权限",
		content: "${ctx}/security/authority/savePage"
	});
}

// 编辑数据
function editFun(id){
	layer.open({
		type: 2,
		area: ["800px", "500px"],
		maxmin: true,
		title: "编辑权限",
		content: "${ctx}/security/authority/savePage?id="+id
	});
}

// 删除数据
function delFun(id){
	layer.confirm("确认要删除吗？", function(index){
		$.ajax({
			url: "${ctx}/security/authority/delete?id="+id,
			type: "post",
			success: function(data){
	      		if(data.success){
					layer.msg(data.msg, { icon:1, time:1000 });
					dataTable.api().ajax.reload(null,false);
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

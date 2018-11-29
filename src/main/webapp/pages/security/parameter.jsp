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
	 参数管理 <a class="btn radius r defaultColor" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
	<i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
	  
<div class="page-container">
	<!-- 条件搜索区域 -->
	<div class="text-c">
		<form class="form-inline" id="searchForm">
			<input type="text" class="input-text" name="name" style="width:200px" placeholder=" 参数名称">
			<button name="" id="" class="btn defaultColor" type="button" onclick="search()"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
		</form>
	</div>	
	<!-- /条件搜索区域 -->
	<!-- 用户操作区域 -->
	<!-- /用户操作区域 -->
	<!-- 数据展示区域 -->
	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover text-c table-sort table-responsive" 
		       id="dataTable" style="width:100%;">
			<thead class="text-c">
				<tr>
					<th>序号</th>
					<th>参数名称</th>
					<th>参数值</th>
					<th>参数说明</th>
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
			url: "${ctx}/security/parameter/list.json",
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
			{ data: "value" },
			{ data: "remark" },
			{
				data: "operate",
				render: function(data, type, full, meta){
					var edit = ' <a style="text-decoration:none" class="ml-5" onclick="editFun(' + full.id + ')" href="javascript:;" title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a> ';
					var content = '';
					if(full.type == 1){
						content = edit;
					}
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

// 编辑数据
function editFun(id){
	layer.open({
		type: 2,
		area: ["800px", "500px"],
		maxmin: true,
		title: "编辑参数",
		content: "${ctx}/security/parameter/input.json?id="+id
	});
}
</script>
<!-- /请在上方写此页面业务相关的脚本 -->
</body>
</html>

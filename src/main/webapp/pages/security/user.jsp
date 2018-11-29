<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<jsp:include page="/common/meta.jsp"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/lib/jquery-easyui-1.5.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/lib/jquery-easyui-1.5.2/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
	<style type="text/css">
		.panel-body label{font-size:12px;}
		.datagrid-cell a{font-size:14px;}
	</style>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i>
	 首页 <span class="c-gray en">&gt;</span> 
	 系统管理 <span class="c-gray en">&gt;</span>
	 用户管理 <a class="btn radius r defaultColor" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
	<i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="page-container">
	<div class="Hui-article">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false,height:65">
				<div class="cl pd-15 bg-1 bk-gray" style="background-color:#FCF8E3;">
			 		<span class="l">提示：选择部门，查询该部门及下级部门的用户信息。</span>
				</div>
			</div>
			<div data-options="region:'west',title:'用户部门',split:true,width:240,minWidth:200,maxWidth:300">
				<ul id="tree"></ul>
			</div>
			<div data-options="region:'center'">
				<div id="toolbar" style="display:none;height:35px;">
					<table>
						<tr>
							<td>
								<a class="btn defaultColor" style="line-height:1.6em;margin-top:3px" onclick="addFun()">
										<i class="Hui-iconfont">&#xe600;</i> 新增</a>
							</td>
							<td>
								<form class="form-inline" id="searchForm">
									<div class="form-group form-group-sm">
										<input type="text" class="input-text" id="username" name="username" maxlength="20" placeholder="用户账号或用户姓名" title="用户账号或用户姓名">
									</div>
									<div class="form-group form-group-sm">
										<select class="input-text" id="enable" name="enable" title="状态">
											<option value="">全部</option>
											<option value="1" selected="selected">启用</option>
											<option value="0">停用</option>
										</select>
									</div>
									<div class="form-group form-group-sm">
										<input type="hidden" id="dept" name="branchNO">
										<div class="check-box">
											<label><input type="checkbox" class="icheckbox-blue" name="children" value="all" checked="checked">下级用户</label>
										</div>
									</div>
									<a class="btn defaultColor" style="line-height:1.6em;margin-top:3px" 
										id="refreshBtn" onclick="searchForm();">搜索</a>
								</form>
							</td>
						</tr>
					</table>
				</div>
				<table id="datagrid" class="easyui-datagrid"
					data-options="
						url:'${ctx}/security/user/listByDept',
						fit:true,
						border:false,
						rownumbers:true,
						pagination:true,
						singleSelect:true,
						pageSize:15,
						pageList:[15,20,30,40,50],
						toolbar:'#toolbar'">
					<thead>
						<tr>
							<th data-options="field:'dept',align:'center',width:'20%',formatter:function(value,row,index){return row.dept.name;}">部门</th>
							<th data-options="field:'username',align:'center',width:'15%'">账号</th>
							<th data-options="field:'name',align:'center',width:'15%'">姓名</th>
							<th data-options="field:'roleNames',align:'center',width:'30%'">角色</th>
							<th data-options="field:'enable',align:'center',width:'10%',formatter:formatterEnable">状态</th>
							<th data-options="field:'operator',align:'center',width:'10%',formatter:formatterOperator">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>

<jsp:include page="/common/footer.jsp"/>
<!-- 请在下方写此页面业务相关的脚本 -->
<script src="${ctx}/lib/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script src="${ctx}/lib/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
var datagrid;
$(function(){
	var tree = $("#tree").tree({
		url: "${ctx}/security/dept/getEasyUITree.json",
		onClick: function(node){
			$("#searchForm")[0].reset();
			$("#addBtn").linkbutton("enable");
			$("#dept").val(node.attributes.number);
			searchForm();
		},
		onLoadSuccess: function(node, data){ //数据加载后,选中根节点.
			if(data.length > 0){
				var root = tree.tree("find", data[0].id);
				tree.tree("select", root.target);
				$("#addBtn").linkbutton("enable");
				$("#dept").val(root.attributes.number);
			}
		}
	});
	datagrid = $("#datagrid");
	datagrid.datagrid({
		queryParams: $("#searchForm").serializeJSON()
	});
});

// 格式化状态列
function formatterEnable(value, row, index){
	if(row.enable=='1'){
		return '<span class="c-success">已启用</span>';
	}else{
		return '<span class="c-warning">已停用</span>';
	}
}

// 格式化操作列
function formatterOperator(value, row, index){
	var edit = '<a title="编辑" href="javascript:;" onclick="editFun(' + row.id + ')"><i class="Hui-iconfont">&#xe6df;</i></a>';
	var resetPwd = '<a title="重置密码" href="javascript:;" class="ml-10" onclick="resetPwdFun(' + row.id + ')"><i class="Hui-iconfont">&#xe605;</i></a>';
	var disable = '<a title="停用" href="javascript:;" class="ml-10" onclick="disableFun(' + row.id + ')"><i class="Hui-iconfont">&#xe726;</i></a>';
	var enable = '<a title="启用" href="javascript:;" onclick="enableFun(' + row.id + ')"><i class="Hui-iconfont">&#xe726;</i></a>';
	var content = '';
	if(row.username == "admin"){
		content = '';
	}else if(row.enable=='1'){
		content = edit + resetPwd + disable;
	}else{
		content = enable;
	}
	return content;
}

// 搜索数据
function searchForm(){
	datagrid.datagrid("load", $("#searchForm").serializeJSON());
}

// 新增数据
function addFun(){
	var add = layer.open({
		type: 2,
		area: ["800px", "600px"],
		maxmin: true,
		title: "新增用户",
		content: "${ctx}/security/user/input"
	});
	layer.full(add);
}

// 编辑数据
function editFun(id){
	var edit = layer.open({
		type: 2,
		area: ["800px", "600px"],
		maxmin: true,
		title: "编辑用户",
		content: "${ctx}/security/user/input?id="+id
	});
	layer.full(edit);
}

// 重置密码
function resetPwdFun(id){
	layer.confirm("确认要重置密码吗？", function(index){
		$.ajax({
			url: "${ctx}/security/user/resetPassword?id="+id,
			type: "post",
			success: function(data){
	      		if(data.success){
					layer.msg(data.msg, { icon:1, time:1000 });
				} else {
					layer.msg(data.msg, {icon:5});
				}
			}
		});
	});
}

// 启用用户
function enableFun(id){
	layer.confirm("确认要启用用户吗？", function(index){
		$.ajax({
			url: "${ctx}/security/user/enableUser?id="+id,
			type: "post",
			success: function(data){
	      		if(data.success){
					layer.msg(data.msg, { icon:1, time:1000 });
					datagrid.datagrid("reload");
				} else {
					layer.msg(data.msg, {icon:5});
				}
			}
		});
	});
}

// 停用用户
function disableFun(id){
	layer.confirm("确认要停用用户吗？", function(index){
		$.ajax({
			url: "${ctx}/security/user/disable?id="+id,
			type: "post",
			success: function(data){
	      		if(data.success){
					layer.msg(data.msg, { icon:1, time:1000 });
					datagrid.datagrid("reload");
				} else {
					layer.msg(data.msg, {icon:5});
				}
			}
		});
	});
}

// 删除数据
function delFun(id){
	layer.confirm("确认要删除吗？", function(index){
		$.ajax({
			url: "${ctx}/security/user!delete.action?id="+id,
			type: "post",
			success: function(data){
	      		if(data.success){
					layer.msg(data.msg, { icon:1, time:1000 });
					datagrid.datagrid("reload");
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

<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<jsp:include page="/common/meta.jsp"/>
</head>
<body>
	<jsp:include page="/common/menu.jsp"/>

	<section class="Hui-article-box">
		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i> 首页</li>
			<li>模板界面</li>
			<li>表格插件 DataTables</li>
			<a class="btn btn-success pull-right" href="javascript:location.replace(location.href);" title="刷新"><i class="fa fa-repeat"></i></a>
		</ol>
		<div class="Hui-article">
			<!-- 条件搜索区域 -->
			
			<!-- /条件搜索区域 -->
			<!-- 用户操作区域 -->
			<div class="btn-area">
				<button type="button" class="btn btn-danger" onclick="batchDelFun()"><i class="fa fa-trash-o"></i> 批量删除</button>
			</div>
			<!-- /用户操作区域 -->
			<!-- 数据展示区域 -->
			<table class="table table-bordered table-hover text-c" id="dataTable">
				<thead>
					<tr>
						<th width="25px">
							<label><input type="checkbox" id="selectAll"></label>
						</th>
						<th width="25px">序号</th>
						<th>账号</th>
						<th>姓名</th>
						<th>部门</th>
						<th>电子邮箱</th>
						<th>手机号码</th>
						<th>是否启用</th>
						<th width="80">操作</th>
					</tr>
				</thead>
			</table>
			<!-- /数据展示区域 -->
		</div>
	</section>

<jsp:include page="/common/footer.jsp"/>
<!-- 请在下方写此页面业务相关的脚本 -->
<script type="text/javascript">
var dataTable;
$(function(){
	/* 选中菜单 */
	$("#menu-demo > dt").click();
	$("#menu-demo-datatables").addClass("current");
	
	/* 表格初始化 */
	dataTable = $("#dataTable").dataTable({
		serverSide: false,
		searching: true,
		ordering: true,
		data: [
			{ "id":1,"username":"admin","name":"系统管理员","dept":"睿信天和","email":"admin@rexyn.cn","mobile":"13888888888","enable":true },
			{ "id":2,"username":"rexyn","name":"系统管理员","dept":"睿信天和","email":"rexyn@rexyn.cn","mobile":"13888888888","enable":true },
			{ "id":3,"username":"PG1","name":"程序员1","dept":"技术部","email":"pg1@rexyn.cn","mobile":"13888885555","enable":true },
			{ "id":4,"username":"PG2","name":"程序员2","dept":"技术部","email":"pg2@rexyn.cn","mobile":"13888885555","enable":true },
			{ "id":5,"username":"PG3","name":"程序员3","dept":"技术部","email":"pg3@rexyn.cn","mobile":"13888885555","enable":false },
			{ "id":6,"username":"SE1","name":"软件工程师1","dept":"技术部","email":"se1@rexyn.cn","mobile":"13888885555","enable":true },
			{ "id":7,"username":"SE2","name":"软件工程师2","dept":"技术部","email":"se2@rexyn.cn","mobile":"13888885555","enable":false },
			{ "id":8,"username":"SE3","name":"软件工程师3","dept":"技术部","email":"se3@rexyn.cn","mobile":"13888885555","enable":true },
			{ "id":9,"username":"SE4","name":"软件工程师4","dept":"技术部","email":"se4@rexyn.cn","mobile":"13888885555","enable":true },
			{ "id":10,"username":"SE5","name":"软件工程师5","dept":"技术部","email":"se5@rexyn.cn","mobile":"13888885555","enable":true },
			{ "id":11,"username":"SE6","name":"软件工程师6","dept":"技术部","email":"se6@rexyn.cn","mobile":"13888885555","enable":true },
			{ "id":12,"username":"SE7","name":"软件工程师7","dept":"技术部","email":"se7@rexyn.cn","mobile":"13888885555","enable":true },
			{ "id":13,"username":"SE8","name":"软件工程师8","dept":"技术部","email":"se8@rexyn.cn","mobile":"13888885555","enable":true },
			{ "id":14,"username":"SE9","name":"软件工程师9","dept":"技术部","email":"se9@rexyn.cn","mobile":"13888885555","enable":false },
			{ "id":15,"username":"SSE1","name":"高级软件工程师1","dept":"技术部","email":"sse1@rexyn.cn","mobile":"13888885555","enable":true },
			{ "id":16,"username":"SSE2","name":"高级软件工程师2","dept":"技术部","email":"sse2@rexyn.cn","mobile":"13888885555","enable":true },
			{ "id":17,"username":"PL1","name":"项目组长1","dept":"技术部","email":"pl1@rexyn.cn","mobile":"13888885555","enable":true },
			{ "id":18,"username":"PL2","name":"项目组长2","dept":"技术部","email":"pl2@rexyn.cn","mobile":"13888885555","enable":true },
			{ "id":19,"username":"PL3","name":"项目组长3","dept":"技术部","email":"pl3@rexyn.cn","mobile":"13888885555","enable":true },
			{ "id":20,"username":"PM1","name":"项目经理1","dept":"技术部","email":"pm1@rexyn.cn","mobile":"13888885555","enable":true },
			{ "id":21,"username":"PM2","name":"项目经理2","dept":"技术部","email":"pm2@rexyn.cn","mobile":"13888885555","enable":true },
			{ "id":22,"username":"PM3","name":"项目经理3","dept":"技术部","email":"pm3@rexyn.cn","mobile":"13888885555","enable":true },
			{ "id":23,"username":"VP1","name":"市场部用户1","dept":"市场部","email":"vp1@rexyn.cn","mobile":"13888886666","enable":true },
			{ "id":24,"username":"VP2","name":"市场部用户2","dept":"市场部","email":"vp2@rexyn.cn","mobile":"13888886666","enable":true },
			{ "id":25,"username":"VP3","name":"市场部用户3","dept":"市场部","email":"vp3@rexyn.cn","mobile":"13888886666","enable":false },
			{ "id":26,"username":"VP4","name":"市场部用户4","dept":"市场部","email":"vp4@rexyn.cn","mobile":"13888886666","enable":true },
			{ "id":27,"username":"VP5","name":"市场部用户5","dept":"市场部","email":"vp4@rexyn.cn","mobile":"13888886666","enable":true },
			{ "id":28,"username":"hr1","name":"人力资源部用户1","dept":"人力资源部","email":"hr1@rexyn.cn","mobile":"13888887777","enable":true },
			{ "id":29,"username":"hr2","name":"人力资源部用户2","dept":"人力资源部","email":"hr2@rexyn.cn","mobile":"13888887777","enable":true },
			{ "id":30,"username":"hr3","name":"人力资源部用户3","dept":"人力资源部","email":"hr3@rexyn.cn","mobile":"13888887777","enable":false },
			{ "id":31,"username":"hr4","name":"人力资源部用户4","dept":"人力资源部","email":"hr4@rexyn.cn","mobile":"13888887777","enable":true },
			{ "id":32,"username":"hr5","name":"人力资源部用户5","dept":"人力资源部","email":"hr5@rexyn.cn","mobile":"13888887777","enable":true },
			{ "id":33,"username":"hr6","name":"人力资源部用户6","dept":"人力资源部","email":"hr6@rexyn.cn","mobile":"13888887777","enable":true },
			{ "id":34,"username":"hr7","name":"人力资源部用户7","dept":"人力资源部","email":"hr7@rexyn.cn","mobile":"13888887777","enable":true }
		],
		columns: [
			{
				data: "id",
				sortable: false,
				render: function(data, type, full, meta){
					return '<input type="checkbox" value="' + data + '">';
				}
			},{
				data: null,
				render: function(data, type, full, meta){
					var startIndex = meta.settings._iDisplayStart;
					return startIndex + meta.row + 1;
				}
			},
			{ data: "username" },
			{ data: "name" },
			{ data: "dept" },
			{ data: "email" },
			{ data: "mobile" },
			{
				data: "enable",
				render: function(data, type, full, meta){
					var content;
					if(full.enable){
						content = '<span class="label label-success">已启用</span>';
					}else{
						content = '<span class="label label-default">已停用</span>';
					}
					return content;
				}
			},{
				data: "operate",
				sortable: false,
				render: function(data, type, full, meta){
					var enable = '<a title="启用" href="javascript:;" onclick=""><i class="fa fa-check-circle-o"></i></a>';
					var disable = '<a title="停用" href="javascript:;" onclick=""><i class="fa fa-minus-circle"></i></a>';
					var edit = '<a title="编辑" href="javascript:;" class="ml-10" onclick=""><i class="fa fa-pencil"></i></a>';
					var del = '<a title="删除" href="javascript:;" class="ml-10" onclick="delFun(' + full.id + ', this)"><i class="fa fa-trash-o"></i></a>';
					
					var content = edit + del;
					
					if(full.enable){
						content = disable + content;
					}else{
						content = enable + content;
					}
					
					return content;
				}
			}
		],
		createdRow: function(row, data, dataIndex){
			// 已停用
			if(!data.enable){
				$(row).addClass("bg-danger");
			}
		},
		drawCallback: function(settings){
			// 如果 scrollX: true  使用 #dataTable_wrapper
			// 如果 scrollX: false 使用 #dataTable
			// 因为 scrollX: true 增加了滚动条,表头和表体成了两个表格.
			//var checkAll = $("#dataTable thead :checkbox");
			//var checkboxs = $("#dataTable tbody :checkbox");
			var checkAll = $(this.api().table().header()).find(":checkbox");
			var checkboxs = $(this.api().table().body()).find(":checkbox");
			// 设置全选复选框事件
			checkAll.on("change", function() {
				checkboxs.prop("checked", $(this).prop("checked"));
			});
			// 设置一般复选框事件
			checkboxs.on("change", function() {
				checkAll.prop("checked", checkboxs.length == checkboxs.filter(":checked").length);
			});
			// 取消全选
            checkAll.prop("checked", false);
		}
	});
});

// 删除数据
function delFun(id, obj){
	layer.confirm("确认要删除吗？", function(index){
		$(obj).parents("tr").remove();
		layer.msg("删除成功!", { icon:1, time:1000 });
	});
}

// 批量删除数据
function batchDelFun(){
	var selected = $(dataTable.api().table().body()).find(":checkbox:checked");
	var length = selected.length;
	if(length > 0){
		var ids = "";
		$.each(selected, function(i){
			ids += ((i > 0 ? ",":"") + $(this).val());
		});
		layer.confirm("确认要删除吗？", function(index){
			//layer.msg("删除记录：" + ids);
			layer.msg("删除成功!", { icon:1, time:1000 });
		});
	}else{
		layer.msg("请选择要删除的记录!", { icon:0, time:1000 });
	}
}
</script>
<!-- /请在上方写此页面业务相关的脚本 -->
</body>
</html>

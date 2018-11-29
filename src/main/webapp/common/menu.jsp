<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<body>
	<!-- 资源菜单div -->
	<div class="menu_dropdown bk_2" id="menu_div"></div>
</body>

<script type="text/javascript" src="${ctx}/lib/jquery-1.11.3/jquery.min.js"></script>
<script type="text/javascript">
//初始化用户菜单
function initMenu(){
	$.ajax({
		url: "${ctx}/security/resource/getUserMenu",
		type: "post",
		async: false,
		success: function(data){
			initLevelMenu(data);
		}
	});
}

//初始化用户菜单
function initLevelMenu(menus){
	var leftMenu = $("#menu_div");
	var str = "";
	$.each(menus, function(i, menu){
		var children = menu.children;
		str+="<dl>";
		str+="<dt><i class='Hui-iconfont icondiv "+menu.icon+"'></i><span>"+menu.name+"</span>";
		str+="<i class='Hui-iconfont menu_dropdown-arrow'>&#xe6d5;</i></dt>";
		if(children !=""){
			str+="<dd style='display: none;'><ul>";
			$.each(children, function(i, menus){
				str+="<li><a data-href='"+menus.url+"' data-title='"+menus.name+"' href='javascript:void(0);'>"+menus.name+"</a></li>";
			});
			str+="</ul></dd>";
		}	
		str+="</dl>";
	});
	leftMenu.append(str);
}
initMenu();
</script>

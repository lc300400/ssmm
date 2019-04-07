<%@ page pageEncoding="UTF-8"%>
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
			<li>空白页</li>
			<a class="btn btn-success pull-right" href="javascript:location.replace(location.href);" title="刷新"><i class="fa fa-repeat"></i></a>
		</ol>
		<div class="Hui-article">
			这是个空白页，请在此处加入您的代码！
		</div>
	</section>

<jsp:include page="/common/footer.jsp"/>
<!-- 请在下方写此页面业务相关的脚本 -->
<script type="text/javascript">
$(function(){
	/* 选中菜单 */
	$("#menu-demo > dt").click();
	$("#menu-demo-blank").addClass("current");
});
</script>
<!-- /请在上方写此页面业务相关的脚本 -->
</body>
</html>

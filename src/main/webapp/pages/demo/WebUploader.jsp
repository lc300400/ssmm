<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<jsp:include page="/common/meta.jsp"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/lib/webuploader-0.1.5/webuploader.css">
</head>
<body>
	<jsp:include page="/common/header.jsp"/>
	<jsp:include page="/common/menu.jsp"/>

	<section class="Hui-article-box">
		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i> 首页</li>
			<li>模板界面</li>
			<li>上传插件 WebUploader</li>
			<a class="btn btn-success pull-right" href="javascript:location.replace(location.href);" title="刷新"><i class="fa fa-repeat"></i></a>
		</ol>
		<div class="Hui-article">
			<div id="uploader" class="wu-example">
				<!--用来存放文件信息-->
				<div id="thelist" class="uploader-list"></div>
				<div class="btns">
					<div id="picker">选择文件</div>
					<button id="ctlBtn" class="btn btn-default">开始上传</button>
				</div>
			</div>
		</div>
	</section>

<jsp:include page="/common/footer.jsp"/>
<script type="text/javascript" src="${ctx}/lib/webuploader-0.1.5/webuploader.min.js"></script>
<!-- 请在下方写此页面业务相关的脚本 -->
<script type="text/javascript">
$(function(){
	/* 选中菜单 */
	$("#menu-demo > dt").click();
	$("#menu-demo-webuploader").addClass("current");
});

var uploader = WebUploader.create({

	// swf文件路径
	swf: "${ctx}/lib/webuploader-0.1.5/Uploader.swf",

	// 文件接收服务端
	server: 'http://webuploader.duapp.com/server/fileupload.php',

	// 选择文件的按钮。可选。
	// 内部根据当前运行是创建，可能是input元素，也可能是flash.
	pick: '#picker',

	// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
	resize: false
});
</script>
<!-- /请在上方写此页面业务相关的脚本 -->
</body>
</html>

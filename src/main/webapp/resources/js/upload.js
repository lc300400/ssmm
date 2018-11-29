/**
 * 导入该上传文件脚本之前,需要导入WebUploader组件.
 */
$(function(){
	var contextPath = window.location.origin + "/" + window.location.pathname.split("/")[1];
	var uploader = WebUploader.create({
		// 选完文件后,是否自动上传.
		auto: true,
		// swf文件路径.
		swf: contextPath + "/lib/webuploader-0.1.5/Uploader.swf",
		// 文件接收服务端
		server: contextPath + "/file!upload.action",
		// 选择文件的按钮.可选.
		// 内部根据当前运行是创建,可能是input元素,也可能是flash.
		pick: "#picker",
		// 不压缩image,默认如果是jpeg,文件上传前会压缩一把再上传！
		resize: false
	});

	// 当有文件被添加进队列的时候.
	uploader.on("fileQueued", function(file){
		var $li = $("<div class='uploader-item'></div>").attr({id: file.id});
		var $file = $("<div></div>").append(file.name).append($("<span class='uploader-state'>等待上传...</span>"));
		$li.append($file).appendTo($("#attachments"));
		$("button[type=submit]").attr({"disabled":"disabled"});
	});

	// 文件上传过程中创建进度条实时显示.
	uploader.on("uploadProgress", function(file, percentage){
	    var $li = $("#"+file.id);
	    var $percent = $li.find(".progress .progress-bar");

		// 避免重复创建
		if(!$percent.length){
			var $progress = $("<div class='progress'></div>");
			$percent = $("<div class='progress-bar progress-bar-success progress-bar-striped active' role='progressbar' style='width:0%'></div>").appendTo($progress);
			$li.append($progress);
		}
		
		$li.find("span.uploader-state").text("上传中...");
		$percent.css("width", percentage * 100 + "%").text(Math.floor(percentage * 100) + "%");
	});

	// 文件上传成功,给item添加成功class,标记上传成功.
	uploader.on("uploadSuccess", function(file, response){
		if(response.success){
			var id = response.obj.id;
			$("#"+file.id).find("span.uploader-state").addClass("success").text("已上传").attr({"data-id":id});
			var attachmentIds = $("#attachmentIds").val();
			if(attachmentIds != undefined){
				if(attachmentIds.length > 0) {
					attachmentIds += ("," + id);
				} else{
					attachmentIds = id;
				}
				$("#attachmentIds").val(attachmentIds);
			}
		} else {
			$("#"+file.id).find("span.uploader-state").addClass("error").text("上传出错");
		}
	});

	// 文件上传失败,显示上传出错.
	uploader.on("uploadError", function(file){
		$("#"+file.id).find("span.uploader-state").addClass("error").text("上传出错");
	});

	// 完成上传,不管成功或者失败,先删除进度条再添加删除链接.
	uploader.on("uploadComplete", function(file){
		$("#"+file.id).find(".progress").fadeOut();
		$("button[type=submit]").removeAttr("disabled");
		$a = $("<a title='删除' href='javascript:void(0);' class='uploader-delete'><i class='fa fa-trash-o'></i></a>")
			.click(function(){
				var id = $("#"+file.id).find("span.uploader-state").attr("data-id");
				var attachmentIds = $("#attachmentIds").val();
				// 删除界面上的文件.
				$("#"+file.id).remove();
				// 删除附件字段中对应ID.
				if(id != undefined && attachmentIds != undefined){
					$("#attachmentIds").val($.grep(attachmentIds.split(','),function(n,i){return n != id}));
				}
			});
		$("#"+file.id).find("span.uploader-state").after($a);
	});
});

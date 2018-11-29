$(function(){
	/**
	 *   表单多个附件字段  上传  
	 *   <rexyn:getAttachment attachmentIds="${accessory}" elementId="accessory" delete="true"/>
	 *	<input type="hidden" id="accessory" name="accessory" value="${accessory}">
	 *	<div class="uploader-list"></div>
	 *	<div class="accessory">选择文件</div>
	 *   ${pageContext.request.contextPath}
	 */
	var contextPath = window.location.origin + "/" + window.location.pathname.split("/")[1];
	$(".accessory").each(function(){
		//父类元素
		var $parent  = $(this).parent();
		//当前附件ID
		var $accesId = $parent.children("input[type='hidden']");
		var certificateUploader = WebUploader.create({
				auto: true,
				swf: contextPath+"/lib/webuploader-0.1.5/Uploader.swf",
				server: contextPath+"/file!upload.action",
				pick:  $(this),
				resize: false
		});
		// 当有文件被添加进队列的时候.
		certificateUploader.on("fileQueued", function(file){
			var $li = $("<div class='uploader-item'></div>").attr({id: file.id});
			var $file = $("<div></div>").append(file.name).append($("<span class='uploader-state'>等待上传...</span>"));
			$li.append($file).appendTo($parent.children("div[class='uploader-list']"));
			$("button[type=submit]").attr({"disabled":"disabled"});
		});
		// 文件上传过程中创建进度条实时显示.
		certificateUploader.on("uploadProgress", function(file, percentage){
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
		certificateUploader.on("uploadSuccess", function(file, response){
			if(response.success){
				var id = response.obj.id;
				$("#"+file.id).find("span.uploader-state").addClass("success").text("已上传").attr({"data-id":id});
				var attachmentIds = $accesId.val();
				if(attachmentIds != undefined){
					if(attachmentIds.length > 0) {
						attachmentIds += ("," + id);
					} else{
						attachmentIds = id;
					}
					$accesId.val(attachmentIds);
				}
			} else {
				$("#"+file.id).find("span.uploader-state").addClass("error").text("上传出错");
			}
		});
		
		// 文件上传失败,显示上传出错.
		certificateUploader.on("uploadError", function(file){
			$("#"+file.id).find("span.uploader-state").addClass("error").text("上传出错");
		});
		// 完成上传,不管成功或者失败,先删除进度条再添加删除链接.
		certificateUploader.on("uploadComplete", function(file){
			$("#"+file.id).find(".progress").fadeOut();
			$("button[type=submit]").removeAttr("disabled");
			$a = $("<a title='删除' href='javascript:void(0);' class='uploader-delete'><i class='fa fa-trash-o'></i></a>")
				.click(function(){
					var id = $("#"+file.id).find("span.uploader-state").attr("data-id");
					var attachmentIds = $accesId.val();
					// 删除界面上的文件.
					$("#"+file.id).remove();
					// 删除附件字段中对应ID.
					if(id != undefined && attachmentIds != undefined){
						$accesId.val($.grep(attachmentIds.split(','),function(n,i){return n != id}));
					}
				});
			$("#"+file.id).find("span.uploader-state").after($a);
		});
		 
 	});
	
});
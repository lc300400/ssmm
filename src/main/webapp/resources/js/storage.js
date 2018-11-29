/** 表单数据暂存到本地浏览器 */
$(function(){
	if(window.localStorage){
		var $storageBtn = $("button[data-name='storageBtn']");
		var $key = $storageBtn.data("key");
		var $form = $("#" + $storageBtn.data("formid"));
		
		//鼠标移动到缓存按钮时,显示提示信息.
		$storageBtn.mouseover(function(){
			var that = this;
			layer.tips("提示：暂存功能主要用于暂存表单数据到本地缓存，仅对当前电脑的同一浏览器单次有效。", that, {
				time: 8000, //显示8秒
				tips: [4,'#FFB800']
			});
		});
		
		//从客户浏览器本地存储中取回暂存数据.
		var tempData = localStorage.getItem($key);
		if(tempData){ 
			layer.msg("系统自动提取您上次的暂存数据!", {icon:6});
			//如果有暂存数据,填充表单.
			$.each(JSON.parse(tempData), function(name, value){
				if(value != ""){
					if($form.find(":radio[name='" + name + "']").length > 0){
						$form.find(":radio[name='" + name + "'][value='" + value + "']").attr("checked",true);
					} else if($form.find(":checkbox[name='" + name + "']").length > 0){
						//有BUG,只能取到选中的最后一个复选框值.
						$form.find(":checkbox[name='" + name + "'][value='" + value + "']").attr("checked",true);
					} else if($form.find(":input[name='" + name + "']").length > 0){
						$form.find(":input[name='" + name + "']").val(value);
					}
				}
			});
			//清除暂存数据.
			localStorage.removeItem($key);
		}

		//为暂存按钮添加暂存功能.
		$storageBtn.click(function(){
			//var data = $form.serialize();
			var data = $form.serializeJSON();
			localStorage.setItem($key, JSON.stringify(data));
			layer.msg("暂存成功!", {icon:1});
		});
	}else{
		layer.msg("对不起，您的浏览器不支持暂存功能！", {icon:5});
	}
});
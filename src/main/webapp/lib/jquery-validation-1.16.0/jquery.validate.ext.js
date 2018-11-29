
//验证输入值必须小于指定值
jQuery.validator.addMethod("lt", function(value, element, param) {
	return this.optional(element) || Number(value) < Number(param);
}, "请输入小于 {0} 的数值");

//验证输入值必须大于指定值
jQuery.validator.addMethod("gt", function(value, element, param) {
	return this.optional(element) || Number(value) > Number(param);
}, "请输入大于 {0} 的数值");

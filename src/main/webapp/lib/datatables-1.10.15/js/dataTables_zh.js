$.extend($.fn.dataTable.defaults, {
	"bServerSide": true, //是否启动服务器端数据导入
	"bStateSave": false, //是否打开客户端状态记录功能,此功能在ajax刷新纪录的时候不会将个性化设定回复为初始化状态
	"bFilter": false, //禁用原生搜索
	"bSort": false,  //禁用排序
	"aaSorting": [], //取消默认排序查询
	"bProcessing": true, //显示加载提示
	"sPaginationType": "full_numbers",
	"oLanguage": {
		"sEmptyTable": "没有数据",
		"sInfo": "显示第  _START_ 到 _END_ 条记录，共 _TOTAL_ 条记录",
		"sInfoEmpty": "没有数据",
		"sInfoFiltered": "(从  _MAX_ 条记录中过滤)",
		"sInfoPostFix": "",
		"sDecimal": "",
		"sThousands": ",",
		"sLengthMenu": "每页显示  _MENU_ 条记录",
		"sLoadingRecords": "载入中...",
		"sProcessing": "处理中...",
		"sSearch": "从当前数据中过滤：",
		"sSearchPlaceholder": "",
		"sUrl": "",
		"sZeroRecords": "没有找到匹配的记录",
		"oPaginate": {
			"sFirst": "首页",
			"sPrevious": "上页",
			"sNext": "下页",
			"sLast": "末页"
		},
		"oAria": {
			"sSortAscending": ": 以升序排列此列",
			"sSortDescending": ": 以降序排列此列"
		}
	},
	//"aLengthMenu": [[10, 50, 100, 1000],[10, 50, 100, "全部≤1000"]],
	"aLengthMenu": [[10, 50, 100, 1000],[10, 50, 100,1000]],
	"dom": "Blfrtip",
	language : {
        buttons: {
            copyTitle: '当前内容已复制',
            copyKeys: 'test'
        }
    },
	buttons: [
        {  
            'extend': 'copy',
            'text': '复制',
            'exportOptions': {  
                'modifier': {  
                    'page': 'current'  
                }  
            }
         },
	     {  
        'extend': 'excel',
        'className':'excelButton1',
        'text': '导出Excel',
        'exportOptions': {  
            'modifier': {  
                'page': 'current'  
            }  
        }
    }],
});
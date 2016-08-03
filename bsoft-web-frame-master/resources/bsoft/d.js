$import([ "dependency.bsoft.remoteService", "dependency.bsoft.tool" ]);
$define("bsoft.d", {
	extend : "bsoft.c",// 类 bsoft.d 继承 bsoft.c
	init : function(args) { // 构造函数
		console.log("d 构造函数执行" + args);
	},
	method1 : function() {
 
		var getMyInfo = $remoteService('hai.demoService', 'getMyInfo');
		var saveData = $remoteService('hai.demoService', 'saveData');
		var arg = {
			num : '001'
		}
		var result = getMyInfo(arg);
		result.success(function(data) {
			alert($o2s(data));
		}).error(function(msg, code) {
			alert(msg + ":" + code);
		});
		//this.$callSuper(arguments)(111);
	}
});
$import([ "dependency.bsoft.remoteService", "dependency.bsoft.tool" ]);
$define("bsoft.a", {
	init : function(args) { // 构造函数
		console.log("a 构造函数执行" + args);
	},
	ima : function() {
		alert("我是a");
	}
});
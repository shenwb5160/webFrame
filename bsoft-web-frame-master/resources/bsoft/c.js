$import("dependency.bsoft.remoteService");
$define("bsoft.c", {
	init : function() {
		console.log("c 构造函数执行");
		this.c = "11111";
	},
	method1 : function(d) {
		alert("c.method1 被执行了:d=" + d);
	},
	method2 : function() {
		alert("bsoft.c method2执行了 c=" + this.c);
	}
});

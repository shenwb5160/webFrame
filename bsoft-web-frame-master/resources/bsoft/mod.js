$import([ "dependency.bsoft.remoteService", "dependency.bsoft.tool" ]);
$define("bsoft.mod", {
	extend : "dependency.bsoft.mod.modBase;bsoft.d",
	init : function(dom, args) {
		this.jdom = $(dom);
		console.log("mod 的构造函数执行  ");
	},
	component : $ref("bsoft.a"),
	fun:function(){
		
	}
});

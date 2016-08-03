$define("dependency.bsoft.mod.modBase", {
	init : function(dom, args, callback) {
		console.log("modBase 构造函数执行");
		this.cdom = dom;// 容器dom
		this.args = args;// 参数

		// 加载css
		$addStyle(this.$className, this._css);

		// 加载HTML
		this.dom = $addHtml(dom, this._html);

		if ($tool.isFunction(callback))
			callback();
	},
	remove : function() {
		if (this.cdom && this.dom) {
			if ($tool.isArray(this.dom))
				for (var i = 0; i < length; i++) {
					this.cdom.removeChild(this.dom[i]);
				}
			else
				this.cdom.removeChild(this.dom);
		}
	}
});

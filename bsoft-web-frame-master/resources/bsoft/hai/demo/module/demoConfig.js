$import('bsoft.ssdev.jsui.util.rmi.RemoteService');

var getAllData = $remoteService('hai.demoService', 'getAllData');

var ths = this;
var thsDom;

function init(dom, args){
	thsDom = dom;
	var num = args;
	ths.initData(num);
}

ths.initData = function(num){
	
	var result = getAllData();
	result.success(function(data){

		thsDom.find('#msg').empty();
		if(data!=null&&data.length>0){
			var div = '<div id="{num}">工号：{num}，姓名：{name}，年龄：{age}，部门：{dept}，电话：{telphone}</div>';
			$.each(data,function(i,n){
				thsDom.find('#msg').append(div.format(n));
				if(n.num == num){
					thsDom.find('#'+num+'').addClass('selected');
				}
			})
			
		}
		
	}).error(function(){
		
	});
}
//改变选中信息
ths.changeSelect = function(num){
	thsDom.find('.selected').removeClass('selected');
	thsDom.find('#'+num+'').addClass('selected');
}
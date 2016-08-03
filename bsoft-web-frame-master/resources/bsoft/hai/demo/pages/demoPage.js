$styleSheet("bsoft.hai.common.css.common");
$import('bsoft.ssdev.jsui.util.rmi.RemoteService');

var getMyInfo = $remoteService('hai.demoService', 'getMyInfo');
var saveData = $remoteService('hai.demoService', 'saveData');

function init(dom, args) {
	var arg = {
		num : '001'
	}
	// 加载数据
	loadData(arg);
	loadMsg(arg.num);

	// 选择工号
	$('#num').change(function(e) {
		var arg = {
			num : $('#num').val()
		}
		loadData(arg);
		pageClass["bsoft.hai.demo.module.demoConfig"][0].changeSelect(arg.num);
	});
	// 保存
	$('#save').click(function(e) {
		savePerson();
	});
}

function loadData(arg) {
	var result = getMyInfo(arg);
	result.success(function(data) {
		setData(data);
	}).error(function(msg,code) {
		alert(msg+":"+code);
	});
}
function loadMsg(arg) {

	if (pageClass["bsoft.hai.demo.module.demoConfig"] == null) {
		$loadpage($('#msgDiv'), 'bsoft.hai.demo.module.demoConfig', arg);
	} else {
		pageClass["bsoft.hai.demo.module.demoConfig"][0].initData(arg);
	}
}

function setData(data) {
	$('#num').val(data.num);
	$('#name').val(data.name);
	$('#age').val(data.age);
	$('#dept').val(data.dept);
	$('#telphone').val(data.telphone);

}

function savePerson() {
	var num = $('#num').val();
	var name = $('#name').val();
	var age = $('#age').val();
	var dept = $('#dept').val();
	var telphone = $('#telphone').val();

	var person = new Person(num, name, age, dept, telphone);
	person.save();

}

function Person(num, name, age, dept, telphone) {
	this.num = num;
	this.name = name;
	this.age = age;
	this.dept = dept;
	this.telphone = telphone;

	this.save = function() {
		var result = saveData(this);
		result.success(function(data) {
			pageClass["bsoft.hai.demo.module.demoConfig"][0].initData(num);
		}).error(function(data) {
		});
	};
}
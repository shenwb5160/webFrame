//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ tool
//easyUI
function showErr(text) {
	$.messager.alert('错误',text,'error');
}
function showOk(text) {
	$.messager.show({
		title:'提示',
		msg: text,
		timeout:2000,
		showType:'slide',
		style:{
			right:'',
			top:document.body.scrollTop+document.documentElement.scrollTop,
			bottom:''
		}
	});
}
//JqWidgets显示通知信息 message--文本 template--类型（info、warning、success、error、mail、time）
function showInfo(message, template) {
	var ss = $("#messageNotification");
	if (ss.length == 0) {
		var messageDiv = $("<DIV id='messageNotification'><div id='messageText'></div></DIV>");
		$('body').append(messageDiv);
		$(messageDiv).jqxNotification({
			width : 400,
			position : "top",
			opacity : 0.9,
			showCloseButton : false,
			autoOpen : false,
			animationOpenDelay : 800,
			autoClose : true,
			closeOnClick : false,
			autoCloseDelay : 2000,
			browserBoundsOffset: 20,
			template : "info"
		});
	}
	var ww = ($(window).width() - 400) / 2;
	$('.jqx-notification-container').css({
		"left" : ww + "px",
		"z-index" : 9999
	});
	if ($("#messageNotification")) {
		$("#messageNotification").jqxNotification("closeAll");
	}
	if (template == undefined || template == '') {
		$("#messageNotification").jqxNotification({
			template : 'info',
			autoClose : true,
			showCloseButton : false
		});
	}else if (template == 'warning' || template == 'error') {
		$("#messageNotification").jqxNotification({
			template : template,
			autoClose : false,
			showCloseButton : true
		});
	}else {
		$("#messageNotification").jqxNotification({
			template : template,
			autoClose : true,
			showCloseButton : false
		});
	}
	$("#messageText").text(message);
	$("#messageNotification").jqxNotification("open");
}
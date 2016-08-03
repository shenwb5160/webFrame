$import([ "dependency.angular.1229.angular,dependency.bsoft.remoteService", "dependency.bsoft.tool" ]);
$define("bsoft.angmod", {
	init : function($scope) {
		var getMyInfo = $remoteService('hai.demoService', 'getMyInfo');
		var saveData = $remoteService('hai.demoService', 'saveData');
		var arg = {
			num : '001'
		}
		var result = getMyInfo(arg);
		result.success(function(data) {
			$scope.name = $o2s(data);
			$scope.click = function() {
				alert("我被点了");
			}
			$scope.$apply();
		}).error(function(msg, code) {
			alert(msg + ":" + code);
		});
	}
});

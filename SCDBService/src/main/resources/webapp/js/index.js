var protojson = angular.module('protojson', []);

protojson.config(['$interpolateProvider', function($interpolateProvider) {
	  $interpolateProvider.startSymbol('{[');
	  $interpolateProvider.endSymbol(']}');
	}]);
var ProtoBuf = dcodeIO.ProtoBuf;
var ContractHeaders;

ProtoBuf.loadProtoFile("scdb_api.proto", function(err, builder) {
		ContractHeaders = builder.build("org.simplity.apiscdb.ContractHeaders");
	});

protojson.controller('ProtoCtrl', function($scope, $http) {
	  
	  $scope.getContacts = function() {
		    $scope.contracts = [];
		    var req = {
		      method: 'GET',
		      url: 'http://localhost:8070/scdb/storagecontracts/contract', 
		      responseType: 'arraybuffer'
		    };

		    $http(req).success(function(data) {
		      var msg = ContractHeaders.decode(data);
		      $scope.contracts = msg.contractHeaders;
		    });
		  };
});
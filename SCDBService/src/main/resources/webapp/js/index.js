var protojson = angular.module('protojson', []);

protojson.config(['$interpolateProvider', function($interpolateProvider) {
	  $interpolateProvider.startSymbol('{[');
	  $interpolateProvider.endSymbol(']}');
	}]);
var ProtoBuf = dcodeIO.ProtoBuf;
var ContractHeaders;
var ContractHeader;

ProtoBuf.loadProtoFile("scdb_api.proto", function(err, builder) {
		ContractHeaders = builder.build("org.simplity.apiscdb.ContractHeaders");
		ContractHeader = builder.build("org.simplity.apiscdb.ContractHeader");
	});

protojson.controller('ProtoCtrl', function($scope, $http) {
	  
	  $scope.getContracts = function() {
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
		  
	$scope.getOneContract = function(){
	    $scope.contracts = [];
	    var req = {
			      method: 'GET',
			      url: 'http://localhost:8070/scdb/storagecontracts/contract/'+$scope.contractId, 
			      responseType: 'arraybuffer'
			    };
	    $http(req).success(function(data) {
		      var msg = ContractHeader.decode(data);
		      $scope.contracts.push(msg);
		    });	    
	}	  
	
	$scope.createContract = function(){
	    $scope.contracts = [];
	    var newContract = new ContractHeader({
	    	    "country": 1010002001001400,
	    	    "notes": "Notes",
	    	    "city": "Cushing",	    	    
	    	    "county": "Payne",
	    	    "throughputsPerYear": "100",
	    	    "econsUOM": 1,
	    	    "leaseType": 1, 
	    	    "segment": "Midcon",	    	
	    	    "state": "OKLAHOMA",
	    	    "contractingEntity": "BPPNA",
	    	    "dealCounterParty": "Magellan Midstream, LLC",
	    	    "durationInMonths": 181,
	    	    "bench": 1010003073000004,
	    	    "otherRefNum": "na",
	    	    "contractStartDate": 1501439400000,
	    	    "contractEndDate": 1501439400000,
	    	    "excessThroughputRateUOM": 1,
	    	    "terminal": "MAGELLAN,TUL",
	    	    "econs": 77000000,
	    	    "dealName": "Cushing Magellan",
	    	    "assetOwner": "Magellan",
	    	    "contractSignDate": 1501439400000,
	    	    "assetName": "wewqe",
	    	    "contractNum": "TestCopy1",
	    	    "region": 1010003098000000,
	    	    "excessThroughputRate": 31, 
	    	    "desc": "Cushing Storage",
	    	    "status": 1
	    	});
	    
	    var req = {
			      method: 'POST',
			      url: 'http://localhost:8070/scdb/storagecontracts/contract',
			      transformRequest: function(r) { return r;},
			      data: newContract.toArrayBuffer(),
			      responseType: 'arraybuffer',
			      headers: {
			          'Content-Type': 'binary/octet-stream'
			        }
			    }; 
	    $http(req).success(function(data) {
		      var msg = ContractHeader.decode(data);
		      $scope.contracts.push(msg);
		    });	    
	}		
	
	$scope.updateContract = function(){
	    $scope.contracts = [];
	    var updateContract = new ContractHeader({
	    		"id": 1,
	    	    "country": 1010002001001400,
	    	    "notes": "Notes",
	    	    "city": "Changed",	    	    
	    	    "county": "Changed",
	    	    "throughputsPerYear": "100",
	    	    "econsUOM": 1,
	    	    "leaseType": 1, 
	    	    "segment": "Midcon",	    	
	    	    "state": "OKLAHOMA",
	    	    "contractingEntity": "Changed",
	    	    "dealCounterParty": "Changed",
	    	    "durationInMonths": 181,
	    	    "bench": 1010003073000004,
	    	    "otherRefNum": "na",
	    	    "contractStartDate": 1501439400000,
	    	    "contractEndDate": 1501439400000,
	    	    "excessThroughputRateUOM": 1,
	    	    "terminal": "Changed",
	    	    "econs": 77000000,
	    	    "dealName": "Changed",
	    	    "assetOwner": "Changed",
	    	    "contractSignDate": 1501439400000,
	    	    "assetName": "Changed",
	    	    "contractNum": "Changed",
	    	    "region": 1010003098000000,
	    	    "excessThroughputRate": 31, 
	    	    "desc": "Changed Storage",
	    	    "status": 1
	    	});
	    
	    var req = {
			      method: 'POST',
			      url: 'http://localhost:8070/scdb/storagecontracts/contract',
			      transformRequest: function(r) { return r;},
			      data: updateContract.toArrayBuffer(),
			      responseType: 'arraybuffer',
			      headers: {
			          'Content-Type': 'binary/octet-stream'
			        }
			    }; 
	    $http(req).success(function(data) {
		      var msg = ContractHeader.decode(data);
		      $scope.contracts.push(msg);
		    });	    
	}		
});
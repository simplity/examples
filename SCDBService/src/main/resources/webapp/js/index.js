var protojson = angular.module('protojson', []);

protojson.config(['$interpolateProvider', function($interpolateProvider) {
	  $interpolateProvider.startSymbol('{[');
	  $interpolateProvider.endSymbol(']}');
	}]);
var ProtoBuf = dcodeIO.ProtoBuf;
var ContractHeaders;
var ContractHeader;

protobuf.load("webapp/scdb_api.proto", function(err, root) {
		ContractHeaders = root.lookupType("org.simplity.apiscdb.ContractHeaders");
		ContractHeader = root.lookupType("org.simplity.apiscdb.ContractHeader");
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
		      var msg = ContractHeaders.decode(new Uint8Array(data));
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
		      var msg = ContractHeader.decode(new Uint8Array(data));
		      var object = ContractHeader.toObject(msg, {
		    	  enums: String,  // enums as string names
		    	  longs: String,  // longs as strings (requires long.js)
		    	  bytes: String,  // bytes as base64 encoded strings
		    	  defaults: true, // includes default values
		    	  arrays: true,   // populates empty arrays (repeated fields) even if defaults=false
		    	  objects: true,  // populates empty objects (map fields) even if defaults=false
		    	  oneofs: true    // includes virtual oneof fields set to the present field's name
		    	});
		      $scope.contracts.push(object);
		    });	    
	}	  
	
	$scope.createContract = function(){
	    $scope.contracts = [];
	    var newContract = {
	    	    "country": 1010002001001400,
	    	    "notes": "Notes",
	    	    "city": "Cushing",	    	    
	    	    "county": "Payne",
	    	    "throughputsPerYear": "100",
	    	    "econsUOM": "GM",
	    	    "leaseType": "CAPITAL", 
	    	    "segment": "Midcon",	    	
	    	    "state": "OKLAHOMA",
	    	    "contractingEntity": "BPPNA",
	    	    "dealCounterParty": "Magellan Midstream, LLC",
	    	    "durationInMonths": 181,
	    	    "bench": 1010003073000004,
	    	    "otherRefNum": "na",
	    	    "contractStartDate": 1501439400000,
	    	    "contractEndDate": 1501439400000,
	    	    "excessThroughputRateUOM": "BBL",
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
	    	    "status": "CLOSED"
	    	};
	    
	 // Create a new message
	    var message = ContractHeader.fromObject(newContract);
	    
	    var req = {
			      method: 'POST',
			      url: 'http://localhost:8070/scdb/storagecontracts/contract',
			      transformRequest: function(r) { return r;},
			      data: ContractHeader.encode(message).finish(),
			      responseType: 'arraybuffer',
			      headers: {
			          'Content-Type': 'binary/octet-stream'
			        }
			    }; 
	    $http(req).success(function(data) {
		      var msg = ContractHeader.decode(new Uint8Array(data));
		      $scope.contracts.push(msg);
		    });	    
	}		
	
	$scope.updateContract = function(){
	    $scope.contracts = [];
	    var updateContract = {
	    		"id": 1,
	    	    "country": 1010002001001400,
	    	    "notes": "Notes",
	    	    "city": "Changed",	    	    
	    	    "county": "Changed",
	    	    "throughputsPerYear": "100",
	    	    "econsUOM": "GM",
	    	    "leaseType": "CAPITAL", 
	    	    "segment": "Midcon",	    	
	    	    "state": "OKLAHOMA",
	    	    "contractingEntity": "Changed",
	    	    "dealCounterParty": "Changed",
	    	    "durationInMonths": 181,
	    	    "bench": 1010003073000004,
	    	    "otherRefNum": "na",
	    	    "contractStartDate": 1501439400000,
	    	    "contractEndDate": 1501439400000,
	    	    "excessThroughputRateUOM": "BBL",
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
	    	    "status": "CLOSED"
	    	};
	    
	 // Create a new message
	    var message = ContractHeader.fromObject(updateContract);
	    
	    var req = {
			      method: 'POST',
			      url: 'http://localhost:8070/scdb/storagecontracts/contract',
			      transformRequest: function(r) { return r;},
			      data: ContractHeader.encode(message).finish(),
			      responseType: 'arraybuffer',
			      headers: {
			          'Content-Type': 'binary/octet-stream'
			        }
			    }; 
	    $http(req).success(function(data) {
		      var msg = ContractHeader.decode(new Uint8Array(data));
		      $scope.contracts.push(msg);
		    });	    
	}		
});
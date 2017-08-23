var protojson = angular.module('protojson', []);

protojson.config(['$interpolateProvider', function($interpolateProvider) {
	  $interpolateProvider.startSymbol('{[');
	  $interpolateProvider.endSymbol(']}');
	}]);
var ProtoBuf = dcodeIO.ProtoBuf;
var ContractHeaders;
var ContractHeader;

protobuf.load("scdb_api.proto", function(err, root) {
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
	       var newContract = {"benches":[],"locations":[],"roleDetails":[{"contractCschdFk":"2100000003431923","createdBy":"SYSTEM","createdDate":"2017-08-21T12:18:05.000Z","cscrdPk":2237409277,"isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"2017-08-21T12:18:05.000Z","lockNum":1,"primaryIndividual":"Pederson","roleCscrtFk":0,"secondaryIndividual":"Recktenwall"}],"storages":[{"contractCschdFk":"2100000003431923","createdBy":"SYSTEM","createdDate":"2017-08-21T12:18:24.000Z","cscsgPk":2237409284,"currency":-1480327336,"description":"Segregated","gradeGroup":"ETHANOL","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"2017-08-21T12:18:24.000Z","lockNum":1,"quantity":390000,"quantityUom":"BBL","storageEndDate":"2013-10-01","storageRate":1,"storageStartDate":"2013-10-01","tankId":"390-1"},{"contractCschdFk":"2100000003431923","createdBy":"SYSTEM","createdDate":"2017-08-21T12:18:24.000Z","cscsgPk":1237409285,"currency":-1480327336,"description":"Segregated","gradeGroup":"MTBE","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"2017-08-21T12:18:24.000Z","lockNum":1,"quantity":390000,"quantityUom":"BBL","storageEndDate":"2013-10-01","storageRate":1,"storageStartDate":"2013-10-01","tankId":"390-2"},{"contractCschdFk":"2100000003431923","createdBy":"SYSTEM","createdDate":"2017-08-21T12:18:24.000Z","cscsgPk":1237409286,"currency":-1480327336,"description":"Segregated","gradeGroup":"BIODIESEL","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"2017-08-21T12:18:24.000Z","lockNum":1,"quantity":390000,"quantityUom":"BBL","storageEndDate":"2013-10-01","storageRate":1,"storageStartDate":"2013-10-01","tankId":"390-3"},{"contractCschdFk":"2100000003431923","createdBy":"SYSTEM","createdDate":"2017-08-21T12:18:24.000Z","cscsgPk":1237409287,"currency":-1480327336,"description":"Segregated","gradeGroup":"FAME","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"2017-08-21T12:18:24.000Z","lockNum":1,"quantity":390000,"quantityUom":"BBL","storageEndDate":"2013-10-01","storageRate":1,"storageStartDate":"2013-10-01","tankId":"390-4"}],"assetName":"","assetOwner":"","bpContractNum":"","bpContractingEntity":"","contractEndDate":"0","contractLink":"","contractRenewalDate":"0","contractSignDate":"0","contractStartDate":"0","createdBy":"","createdDate":"0","cschdPk":"2100000003431923","dealCounterParty":"","dealName":"","description":"","durationMonths":0,"econs":0,"econsUom":"NPV","excessThroughputRate":0,"excessThroughputRateUom":"M3","externalContractNum":"","isDeleted":"","lastUpdatedBy":"","lastUpdatedDate":"0","leasePercentage":0,"leaseType":"OPERATING","lockNum":0,"notes":"","otherReferenceNum":"","region":0,"status":"LIVE","terminal":"","througputPerYear":0};
	    
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
	    var updateContract = {"benches":[],"locations":[],"roleDetails":[{"contractCschdFk":"2100000003431923","createdBy":"SYSTEM","createdDate":"2017-08-21T12:18:05.000Z","cscrdPk":2237409277,"isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"2017-08-21T12:18:05.000Z","lockNum":1,"primaryIndividual":"Pederson","roleCscrtFk":0,"secondaryIndividual":"Recktenwall"}],"storages":[{"contractCschdFk":"2100000003431923","createdBy":"SYSTEM","createdDate":"2017-08-21T12:18:24.000Z","cscsgPk":2237409284,"currency":-1480327336,"description":"Segregated","gradeGroup":"ETHANOL","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"2017-08-21T12:18:24.000Z","lockNum":1,"quantity":390000,"quantityUom":"BBL","storageEndDate":"2013-10-01","storageRate":1,"storageStartDate":"2013-10-01","tankId":"390-1"},{"contractCschdFk":"2100000003431923","createdBy":"SYSTEM","createdDate":"2017-08-21T12:18:24.000Z","cscsgPk":1237409285,"currency":-1480327336,"description":"Segregated","gradeGroup":"MTBE","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"2017-08-21T12:18:24.000Z","lockNum":1,"quantity":390000,"quantityUom":"BBL","storageEndDate":"2013-10-01","storageRate":1,"storageStartDate":"2013-10-01","tankId":"390-2"},{"contractCschdFk":"2100000003431923","createdBy":"SYSTEM","createdDate":"2017-08-21T12:18:24.000Z","cscsgPk":1237409286,"currency":-1480327336,"description":"Segregated","gradeGroup":"BIODIESEL","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"2017-08-21T12:18:24.000Z","lockNum":1,"quantity":390000,"quantityUom":"BBL","storageEndDate":"2013-10-01","storageRate":1,"storageStartDate":"2013-10-01","tankId":"390-3"},{"contractCschdFk":"2100000003431923","createdBy":"SYSTEM","createdDate":"2017-08-21T12:18:24.000Z","cscsgPk":1237409287,"currency":-1480327336,"description":"Segregated","gradeGroup":"FAME","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"2017-08-21T12:18:24.000Z","lockNum":1,"quantity":390000,"quantityUom":"BBL","storageEndDate":"2013-10-01","storageRate":1,"storageStartDate":"2013-10-01","tankId":"390-4"}],"assetName":"","assetOwner":"","bpContractNum":"","bpContractingEntity":"","contractEndDate":"0","contractLink":"","contractRenewalDate":"0","contractSignDate":"0","contractStartDate":"0","createdBy":"","createdDate":"0","cschdPk":"2100000003431923","dealCounterParty":"","dealName":"","description":"","durationMonths":0,"econs":0,"econsUom":"NPV","excessThroughputRate":0,"excessThroughputRateUom":"M3","externalContractNum":"","isDeleted":"","lastUpdatedBy":"","lastUpdatedDate":"0","leasePercentage":0,"leaseType":"OPERATING","lockNum":0,"notes":"","otherReferenceNum":"","region":0,"status":"LIVE","terminal":"","througputPerYear":0};
	    
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
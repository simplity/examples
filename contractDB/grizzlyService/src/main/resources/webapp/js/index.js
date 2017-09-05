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
		      headers: {
		    	  'Accept':'application/octet-stream'
		      },
		      url: '/scdb/storagecontracts/contract', 
		      responseType: 'arraybuffer'
		    };

		    $http(req).success(function(data) {
		      var msg = ContractHeaders.decode(new Uint8Array(data));
		      $scope.contracts = msg.contractHeaders;
		    });
		  };

		  $scope.getFilteredContracts = function() {
			    $scope.contracts = [];
			    var req = {
			      method: 'GET',
			      headers: {
			    	  'Accept':'application/octet-stream'
			      },			      
			      url: '/scdb/storagecontracts/filter?'+$scope.contractId, 
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
			      headers: {
			    	  'Accept':'application/octet-stream'
			      },			      
			      url: '/scdb/storagecontracts/contract/'+$scope.contractId, 
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
	       var newContract = {"assetName":"Enterprise Houston Terminal-Changes","assetOwner":"Enterprise","bpContractNum":"Ent Houston OTI-createnew-123","bpContractingEntity":"BPPNA","contractEndDate":"1756578600000","contractSignDate":"1451586600000","contractStartDate":"1535740200000","createdBy":"SYSTEM","createdDate":"1503317821000","cschdPk":"2200000003431923","dealCounterParty":"Enterprise Partners, LLC","dealName":"Enterprise Houston Crude","description":"Enterprise Houston Storage","durationMonths":85.2,"econs":40000000,"excessThroughputRate":0.5,"externalContractNum":"1-hou-gcrude-12","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"1503317821000","leasePercentage":20,"leaseType":"CAPITAL","lockNum":"1","notes":"Note","otherReferenceNum":"na","region":"1010003098000000","status":"CLOSED","roleDetails":[{"contractCschdFk":"2200000003431923","createdBy":"SYSTEM","createdDate":"1503317885000","cscrdPk":"2200000003431933","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"1503317885000","lockNum":"1","primaryIndividual":"Pederson","roleCscrtFk":"2000000003431929","roleType":[{"cscrtPk":"2000000003431929","isDeleted":"N","roleName":"Bench Owner"}],"secondaryIndividual":"Recktenwall"}],"storages":[{"contractCschdFk":"2200000003431923","createdBy":"SYSTEM","createdDate":"1503317904000","cscsgPk":"2200000003431940","currency":"1010003029000024","description":"Segregated","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"1503317904000","lockNum":"1","quantity":390000,"quantityUom":"BBL","storageEndDate":"1380565800000","storageRate":0.55,"storageStartDate":"1380565800000","tankId":"390-1"},{"contractCschdFk":"2200000003431923","createdBy":"SYSTEM","createdDate":"1503317904000","cscsgPk":"2200000003431941","currency":"1010003029000024","description":"Segregated","gradeGroup":"MTBE","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"1503317904000","lockNum":"1","quantity":390000,"quantityUom":"BBL","storageEndDate":"1380565800000","storageRate":0.55,"storageStartDate":"1380565800000","tankId":"390-2"},{"contractCschdFk":"2200000003431923","createdBy":"SYSTEM","createdDate":"1503317904000","cscsgPk":"2200000003431942","currency":"1010003029000024","description":"Segregated","gradeGroup":"BIODIESEL","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"1503317904000","lockNum":"1","quantity":390000,"quantityUom":"BBL","storageEndDate":"1380565800000","storageRate":0.55,"storageStartDate":"1380565800000","tankId":"390-3"},{"contractCschdFk":"2200000003431923","createdBy":"SYSTEM","createdDate":"1503317904000","cscsgPk":"2200000003431943","currency":"1010003029000024","description":"Segregated","gradeGroup":"FAME","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"1503317904000","lockNum":"1","quantity":390000,"quantityUom":"BBL","storageEndDate":"1380565800000","storageRate":0.65,"storageStartDate":"1380565800000","tankId":"390-4"}],"terminal":"Enterprise ","througputPerYear":12};
	    
	 // Create a new message
	    var message = ContractHeader.fromObject(newContract);
	     
	    var req = {
			      method: 'POST',	      
			      url: '/scdb/storagecontracts/contract',
			      transformRequest: function(r) { return r;},
			      data: ContractHeader.encode(message).finish(),
			      responseType: 'arraybuffer',
			      headers: {
			          'Content-Type': 'binary/octet-stream',
			          'Accept':'application/octet-stream'
			        }
			    }; 
	    $http(req).success(function(data) {
		      var msg = ContractHeader.decode(new Uint8Array(data));
		      $scope.contracts.push(msg);
		    });	    
	}		
	
	$scope.updateContract = function(){
	    $scope.contracts = [];
	    var updateContract = {"assetName":"Enterprise Houston Terminal-Changes","assetOwner":"Enterprise","bpContractNum":"Ent Houston OTI-update-123","bpContractingEntity":"BPPNA","contractEndDate":"1756578600000","contractSignDate":"1451586600000","contractStartDate":"1535740200000","createdBy":"SYSTEM","createdDate":"1503317821000","cschdPk":"2000000003431923","dealCounterParty":"Enterprise Partners, LLC","dealName":"Enterprise Houston Crude","description":"Enterprise Houston Storage","durationMonths":85.2,"econs":40000000,"excessThroughputRate":0.5,"externalContractNum":"1-hou-gcrude-12","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"1503317821000","leasePercentage":20,"lockNum":"1","notes":"Note","otherReferenceNum":"na","region":"1010003098000000","roleDetails":[{"contractCschdFk":"2000000003431923","createdBy":"SYSTEM","createdDate":"1503317885000","cscrdPk":"2000000003431933","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"1503317885000","lockNum":"1","primaryIndividual":"Pederson","roleCscrtFk":"2000000003431929","roleType":[{"cscrtPk":"2000000003431929","isDeleted":"N","roleName":"Bench Owner"}],"secondaryIndividual":"Recktenwall"}],"storages":[{"contractCschdFk":"2000000003431923","createdBy":"SYSTEM","createdDate":"1503317904000","cscsgPk":"2000000003431940","currency":"1010003029000024","description":"Segregated","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"1503317904000","lockNum":"1","quantity":390000,"quantityUom":"BBL","storageEndDate":"1380565800000","storageRate":0.55,"storageStartDate":"1380565800000","tankId":"390-1"},{"contractCschdFk":"2000000003431923","createdBy":"SYSTEM","createdDate":"1503317904000","cscsgPk":"2000000003431941","currency":"1010003029000024","description":"Segregated","gradeGroup":"MTBE","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"1503317904000","lockNum":"1","quantity":390000,"quantityUom":"BBL","storageEndDate":"1380565800000","storageRate":0.55,"storageStartDate":"1380565800000","tankId":"390-2"},{"contractCschdFk":"2000000003431923","createdBy":"SYSTEM","createdDate":"1503317904000","cscsgPk":"2000000003431942","currency":"1010003029000024","description":"Segregated","gradeGroup":"BIODIESEL","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"1503317904000","lockNum":"1","quantity":390000,"quantityUom":"BBL","storageEndDate":"1380565800000","storageRate":0.55,"storageStartDate":"1380565800000","tankId":"390-3"},{"contractCschdFk":"2000000003431923","createdBy":"SYSTEM","createdDate":"1503317904000","cscsgPk":"2000000003431943","currency":"1010003029000024","description":"Segregated","gradeGroup":"FAME","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"1503317904000","lockNum":"1","quantity":390000,"quantityUom":"BBL","storageEndDate":"1380565800000","storageRate":0.65,"storageStartDate":"1380565800000","tankId":"390-4"}],"terminal":"Enterprise ","througputPerYear":12} 
	    
	 // Create a new message
	    var message = ContractHeader.fromObject(updateContract);
	    
	    var req = {
			      method: 'PUT',
			      url: '/scdb/storagecontracts/contract/'+updateContract.cschdPk,
			      transformRequest: function(r) { return r;},
			      data: ContractHeader.encode(message).finish(),
			      responseType: 'arraybuffer',
			      headers: {
			          'Content-Type': 'binary/octet-stream',
			          'Accept':'application/octet-stream'
			        }
			    }; 
	    $http(req).success(function(data) {
		      var msg = ContractHeader.decode(new Uint8Array(data));
		      $scope.contracts.push(msg);
		    });	    
	}		
});
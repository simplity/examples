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
		      url: 'http://localhost:8080/SCDBWeb/scdb/storagecontracts/contract', 
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
			      url: 'http://localhost:8080/SCDBWeb/scdb/storagecontracts/contract/'+$scope.contractId, 
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
	       var newContract = {"assetName":"Enterprise BMW","assetOwner":"Enterprise","benches":[{"bench":-1436327356,"contractCschdFk":"2100000003431927","createdBy":"SYSTEM","createdDate":"2017-08-21T12:18:24.000Z","cscbsPk":2237409313,"isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"2017-08-21T12:18:24.000Z","lockNum":1}],"bpContractNum":"Ent BMW","bpContractingEntity":"BPPNA","contractEndDate":"1680201000000","contractSignDate":"1407263400000","contractStartDate":"1459449000000","createdBy":"SYSTEM","createdDate":"1503317821000","cschdPk":"2200000003431927","dealCounterParty":"Enterprise Partners, LLC","dealName":"Enterprise Beaumont Marine West","description":"Beaumont","durationMonths":85.17,"econs":63500000,"excessThroughputRate":0.55,"excessThroughputRateUom":"BBL","externalContractNum":"BMW - 5763 - 2210","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"1503317821000","leaseType":"CAPITAL","leasePercentage":60,"locations":[{"city":"Cushing","contractCschdFk":"2100000003431927","country":1786641339,"county":"Payne","createdBy":"SYSTEM","createdDate":"2017-08-21T12:18:24.000Z","csclsPk":2237409307,"isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"2017-08-21T12:18:24.000Z","lockNum":1,"state":"Oklahoma"}],"lockNum":1,"notes":"Note","otherReferenceNum":"na","region":-1411327360,"terminal":"ENTERPRISE ","status":"CLOSED","througputPerYear":15};
	    
	 // Create a new message
	    var message = ContractHeader.fromObject(newContract);
	     
	    var req = {
			      method: 'POST',
			      url: 'http://localhost:8080/SCDBWeb/scdb/storagecontracts/contract',
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
	    var updateContract = {"assetName":"Enterprise BMW","assetOwner":"Enterprise","benches":[{"bench":-1436327356,"contractCschdFk":"2100000003431927","createdBy":"SYSTEM","createdDate":"2017-08-21T12:18:24.000Z","cscbsPk":2237409313,"isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"2017-08-21T12:18:24.000Z","lockNum":1}],"bpContractNum":"Ent BMW","bpContractingEntity":"BPPNA","contractEndDate":"1680201000000","contractSignDate":"1407263400000","contractStartDate":"1459449000000","createdBy":"SYSTEM","createdDate":"1503317821000","cschdPk":"2200000003431927","dealCounterParty":"Enterprise Partners, LLC","dealName":"Enterprise Beaumont Marine West","description":"Beaumont","durationMonths":85.17,"econs":63500000,"excessThroughputRate":0.55,"excessThroughputRateUom":"BBL","externalContractNum":"BMW - 5763 - 2210","isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"1503317821000","leaseType":"CAPITAL","leasePercentage":60,"locations":[{"city":"Cushing","contractCschdFk":"2100000003431927","country":1786641339,"county":"Payne","createdBy":"SYSTEM","createdDate":"2017-08-21T12:18:24.000Z","csclsPk":2237409307,"isDeleted":"N","lastUpdatedBy":"SYSTEM","lastUpdatedDate":"2017-08-21T12:18:24.000Z","lockNum":1,"state":"Oklahoma"}],"lockNum":1,"notes":"Note","otherReferenceNum":"na","region":-1411327360,"terminal":"ENTERPRISE ","status":"CLOSED","througputPerYear":16};
	    
	 // Create a new message
	    var message = ContractHeader.fromObject(updateContract);
	    
	    var req = {
			      method: 'POST',
			      url: 'http://localhost:8080/SCDBWeb/scdb/storagecontracts/contract',
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
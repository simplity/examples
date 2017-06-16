'use strict';

/* Controllers */

angular.module('myApp.controllers')

  .controller('TroubleTicketCtrl', ['$scope','$http',function($scope, $http) {
	  $scope.mydata = [];
	  
	  $scope.loadDB = function(){
		var data =  [ {
			  "id" : "1",
			  "severity" : "High",
			  "description" : "Complaint",
			  "type" : "Product",
			  "status" : "Resolved",
			  "note" : [ ]
			}, {
			  "id" : "1000",
			  "description" : "videoQuailty",
			  "severity" : "High",
			  "type" : "DSPDailymotion",
			  "creationDate" : "2013-10-20 18:44:27",
			  "status" : "InProgress_Pending",
			  "statusChangeReason" : "Access Seeker action/information required",
			  "statusChangeDate" : "2013-10-20 18:44:36",
			  "notes" : [ ],
			  "relatedpartyref" : [ {
			    "role" : "user",
			    "href" : "vbo"
			  }]
			},{
				  "id" : "1004",
				  "description" : "Some Description",
				  "severity" : "Medium",
				  "type" : "Bills, charges or payment",
				  "creationDate" : "2013-09-16 16:39:28",
				  "targetResolutionDate" : "2013-09-16 16:39:28",
				  "status" : "Acknowledged",
				  "resolutionDate" : "2013-09-16 16:39:28",
				  "relatedobject" : [ {
				    "reference" : "referenceobject",
				    "involvement" : "involvment"
				  } ],
				  "note" : [ {
				    "date" : "2013-09-16 16:39:28",
				    "author" : "author",
				    "text" : "text"
				  }, {
				    "date" : "2013-09-16 16:39:28",
				    "author" : "author",
				    "text" : "text"
				  } ],
				  "relatedpartyref" : [ {
				    "role" : "role",
				    "href" : "reference party"
				  }, {
				    "role" : "role",
				    "href" : "reference party"
				  } ]
				}
			];
			for(var i=0;i<data.length;i++){
				$http.post('http://localhost:8081/api/troubleTicket',JSON.stringify(data[i]))
				.then(function (json) {
					$scope.mydata = json;
				}, function () {
					alert("error");
				});
			}  
	  }
			
			
	  $http.get('http://localhost:8081/api/troubleTicket')
		.then(function (json) {
			$scope.mydata = json;
		}, function () {
			alert("error");
		});
    
	  
	  /*var troubleTicket = {
			  "description":"videoQuailty",
			  "severity":"High",
			  "type":"DSPDailymotion",
	  };
	  
	  $http.post('http://localhost:8081/api/troubleTicket',JSON.stringify(troubleTicket))
		.then(function (json) {
			$scope.mydata = json;
		}, function () {
			alert("error");
		});*/
  }]);

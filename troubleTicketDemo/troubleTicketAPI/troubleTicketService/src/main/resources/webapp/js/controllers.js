'use strict';

/* Controllers */

angular.module('myApp.controllers')

  .controller('TroubleTicketCtrl', ['$scope','$http',function($scope, $http) {
	  $scope.tickets = [];
	  
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
			  "creationDate" : new Date("2013-10-20 18:44:27"),
			  "status" : "InProgress_Pending",
			  "statusChangeReason" : "Access Seeker action/information required",
			  "statusChangeDate" : new Date("2013-10-20 18:44:36"),
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
				  "creationDate" : new Date("2013-09-16 16:39:28"),
				  "targetResolutionDate" : new Date("2013-09-16 16:39:28"),
				  "status" : "Acknowledged",
				  "resolutionDate" : new Date("2013-09-16 16:39:28"),
				  "relatedobject" : [ {
				    "reference" : "referenceobject",
				    "involvement" : "involvment"
				  } ],
				  "note" : [ {
				    "date" : new Date("2013-09-16 16:39:28"),
				    "author" : "author",
				    "text" : "text"
				  }, {
				    "date" : new Date("2013-09-16 16:39:28"),
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
					console.log("Data loaded to DB");
				}, function () {
					alert("error");
				});
			}  
	  }
			
			
	  $http.get('http://localhost:8081/api/troubleTicket')
		.then(function (json) {
			$scope.tickets = json.tickets;
		}, function () {
			alert("error");
		});
    
 }]);

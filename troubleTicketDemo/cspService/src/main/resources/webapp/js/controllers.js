'use strict';

/* Controllers */

angular.module('tmfforum.controllers', [])
.controller('TroubleTicketCtrl', ['$scope', '$http', function($scope, $http) {

  // Instantiate an object to store scope data
  $scope.myData = {};
  $scope.ticketAction = "get";
  $scope.myData.action = "list";
  $http.get("api/troubleTicket")
  .then(function(response) {
      $scope.myData.ticketList = response.data;
  });
  
  $scope.setCurrentTicketView = function(id) {
  	 $http.get("api/troubleTicket/"+id)
  	    .then(function(response) {
  	    	$scope.myData.action = "view";
  	        $scope.myData.currentTicket = response.data;
  	    });    	  
    };
  
    $scope.setCurrentTicketEdit = function(id) {    	  
   	 $http.get("api/troubleTicket/"+id)
   	    .then(function(response) {
   	    	$scope.myData.action = "edit";
   	    	$scope.ticketAction = "update";
   	        $scope.myData.currentTicket = response.data;
   	    });    	  
     };      
  
     $scope.createTicket = function(){
   	 $scope.myData.currentTicket = null;    	   
  	 $scope.myData.action = "edit"; 
  	 $scope.ticketAction = "add";
     };
     
     $scope.updateTicket = function(ticket){     
  	   for (var p in ticket) {
  		    if( ticket.hasOwnProperty(p) ) {
  		      if (!ticket[p]){
  		    	  delete ticket[p];
  		      }
  		    } 
  		  } 	   
  	   if($scope.ticketAction == "update"){  
  		 ticket.source = "self";  
       	 $http.put("api/troubleTicket/"+ticket.id,ticket) 
  	    .then(function(response) {
  	    	console.log(response);
  	    }); 
  	   }
  	   else if($scope.ticketAction == "add"){
  		ticket.source = "self";    		   
  		$http.post("api/troubleTicket",ticket) 
  	    .then(function(response) {
  	    	console.log(response);
  	    });   
  	   }
       };       
     
    $scope.showAll = function(){
  	  $scope.myData.currentTicket = null;
  	  $scope.myData.action = "list";  
    };
}])
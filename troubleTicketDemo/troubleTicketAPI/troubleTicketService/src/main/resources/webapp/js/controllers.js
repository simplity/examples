'use strict';

/* Controllers */

angular.module('myApp.controllers')

  .controller('TroubleTicketCtrl', ['$scope', '$http', function($scope, $http) {

    // Instantiate an object to store scope data
    $scope.myData = {};
    $scope.myData.action = "list";
    $http.get("api/troubleTicket")
    .then(function(response) {
        $scope.myData.ticketList = response.data.tickets;
    });
    
    $scope.setCurrentTicketView = function(id) {
    	 $http.get("api/troubleTicket/"+id)
    	    .then(function(response) {
    	    	$scope.myData.action = "view";
    	        $scope.myData.currentTicket = response.data.ticket[0];
    	    });    	  
      };
    
      $scope.setCurrentTicketEdit = function(id) {
     	 $http.get("api/troubleTicket/"+id)
     	    .then(function(response) {
     	    	$scope.myData.action = "edit";
     	        $scope.myData.currentTicket = response.data.ticket[0];
     	    });    	  
       };      
    
       $scope.createTicket = function(){
     	 $scope.myData.currentTicket = null;    	   
    	 $scope.myData.action = "edit"; 
       };
       
       $scope.updateTicket = function(ticket){     	   
         	 $http.put("api/troubleTicket/"+ticket.id,ticket) 
    	    .then(function(response) {
    	    	console.log(response);
    	    }); 
         };       
       
      $scope.showAll = function(){
    	  $scope.myData.currentTicket = null;
    	  $scope.myData.action = "list";  
      };
  }])
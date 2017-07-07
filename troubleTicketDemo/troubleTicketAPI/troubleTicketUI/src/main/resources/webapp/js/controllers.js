'use strict';

/* Controllers */

angular.module('tmfforum.controllers', [])
.controller('TroubleTicketCtrl',function($scope,$http,$routeParams,$window){

    // Instantiate an object to store scope data 
    $scope.myData = {};
    $scope.ticketAction = "get";
    $scope.myData.action = "list";
    var code = $routeParams.code;
    if(code){
        $http.get("api/troubleTicket?code="+code)
        .then(function(response) {
            $scope.myData.ticketList = response.data.tickets;  
        });    	
    }else{
        $http.get("api/troubleTicket")
        .then(function(response) {
        	if(response.data.url){
        		$window.location.href=decodeURIComponent(response.data.url);
        	}
        });
    }

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
     	    	$scope.ticketAction = "update";
     	        $scope.myData.currentTicket = response.data.ticket[0];
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
         	 $http.put("api/troubleTicket/"+ticket.id,ticket) 
    	    .then(function(response) {
    	    	console.log(response);
    	    }); 
    	   }
    	   else if($scope.ticketAction == "add"){ 
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
      $scope.createRelatedInformation = function(){
    	  if(!$scope.myData.currentTicket)
    		  $scope.myData.currentTicket = {};
    	  if(!$scope.myData.currentTicket.relatedObjects){
    		  $scope.myData.currentTicket.relatedObjects = [];
    	  }
    	  $scope.myData.currentTicket.relatedObjects.push({});
      };
      $scope.createNotes = function(){
    	  if(!$scope.myData.currentTicket)
    		  $scope.myData.currentTicket = {};
    	  if(!$scope.myData.currentTicket.notes){
    		  $scope.myData.currentTicket.notes = [];
    	  }    	  
    	  $scope.myData.currentTicket.notes.push({});
      };
      $scope.createRelatedParties = function(){
    	  if(!$scope.myData.currentTicket)  
    		  $scope.myData.currentTicket = {};
    	  if(!$scope.myData.currentTicket.relatedParties){
    		  $scope.myData.currentTicket.relatedParties = [];
    	  }    	  
    	  $scope.myData.currentTicket.relatedParties.push({});
      };      
      
  })
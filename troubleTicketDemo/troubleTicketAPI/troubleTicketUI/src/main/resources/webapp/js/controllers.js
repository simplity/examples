'use strict';

/* Controllers */

angular.module('tmfforum.controllers', [])
.controller('TroubleTicketCtrl',function($scope,$http,$routeParams,$window){

    // Instantiate an object to store scope data 
    $scope.myData = {};
    $scope.ticketAction = "get";
    $scope.myData.action = "list";
    var code = $routeParams.code;
    var correlationId= $routeParams.correlationId; 
    
    if(code){
    	var req = {
    			 method: 'GET',
    			 url: 'api/troubleTicket',
    			 params: {'code': code,
    				 	  'correlationId':correlationId}
    			}
    	
        $http(req)
        .then(function(response) {
            $scope.myData.ticketList = response.data.tickets;  
        });    	
    }else{
    	var req = {
   			 method: 'GET',
   			 url: 'api/troubleTicket',
   			}
   	    	
        $http(req)
        .then(function(response) {
        	if(response.data.url){
        		$window.location.href=decodeURIComponent(response.data.url);
        	}
        });
    }

    $scope.setCurrentTicketView = function(id) {
    	var req = {
   			 method: 'GET',
   			 url: 'api/troubleTicket/'+id,
   			 params: {'code': code,
   				 	  'correlationId':correlationId}
   			}
   	
       $http(req)
       .then(function(response) {
	    	$scope.myData.action = "view";
	        $scope.myData.currentTicket = response.data.ticket[0];
	    });    	 	  
      };
    
      $scope.setCurrentTicketEdit = function(id) {
      	var req = {
      			 method: 'GET',
      			 url: 'api/troubleTicket/'+id,
      			 params: {'code': code,
      				 	  'correlationId':correlationId}
      			}
      	
          $http(req)
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
    		    if(code){
    		    	var req = {
    		    			 method: 'PUT', 
    		    			 url: "api/troubleTicket/"+ticket.id,
    		    			 params: {'code': code},
    		    			 data: ticket
    		    			}    		    	
    		        $http(req)
    		        .then(function(response) {
    	    	    	console.log(response);  
    		        });    	
    		    }else{
    		    	var req = {
   		    			 method: 'PUT',
   		    			 url: "api/troubleTicket/"+ticket.id,
   		    			 data: ticket
   		    			}    		    	    		   	    	
    		        $http(req)
    		        .then(function(response) {
    		        	if(response.data.url){
    		        		$window.location.href=decodeURIComponent(response.data.url);
    		        	}
    		        });
    		    }    		       		       		   
    	   }
    	   if($scope.ticketAction == "add"){ 
   		    if(code){
		    	var req = {
		    			 method: 'POST',
		    			 url: "api/troubleTicket/",
		    			 params: {'code': code},
		    			 data: ticket
		    			}    		    	
		        $http(req)
		        .then(function(response) {
	    	    	console.log(response);  
		        });    	
		    }else{
		    	var req = {
		    			 method: 'POST',
		    			 url: "api/troubleTicket/",
		    			 data: ticket
		    			}    		    	    		   	    	
		        $http(req) 
		        .then(function(response) {
		        	if(response.data.url){
		        		$window.location.href=decodeURIComponent(response.data.url);
		        	}
		        });
		    }  
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
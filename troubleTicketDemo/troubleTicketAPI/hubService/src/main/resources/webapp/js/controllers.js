'use strict';

/* Controllers */

angular.module('myApp.controllers')

  .controller('HubCtrl', ['$scope', '$http', function($scope, $http) {

    // Instantiate an object to store scope data
    $scope.hubData = {};
    $scope.hubData.action = "list";
    $http.get("api/hub")
    .then(function(response) {
        $scope.hubData.hubList = response.data.hubs;
    });
    
    $scope.setCurrentHubView = function(id) {
    	 $http.get("api/hub/"+id)
    	    .then(function(response) {
    	    	$scope.hubData.action = "view";
    	        $scope.hubData.currentHub = response.data.hub[0];
    	    });    	  
      };

      $scope.setCurrentHubDelete = function(id,index) {
     	 $http.delete("api/hub/"+id)
     	    .then(function(response) {
     	    	$scope.hubData.action = "view";
     	        $scope.hubData.hubList.splice(index,1); 
     	    });    	  
       };      
       
       $scope.createHub = function(){
     	 $scope.hubData.currentHub = null;    	   
    	 $scope.hubData.action = "edit"; 
       };
       
       $scope.updateHub = function(hub){     	   
         	 $http.post("api/hub/",hub) 
    	    .then(function(response) {
    	    	console.log(response);
    	    }); 
         };       
       
      $scope.showAll = function(){
    	  $scope.hubData.currentHub = null;
    	  $scope.hubData.action = "list";  
      };
  }])
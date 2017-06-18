'use strict';

/* Controllers */

angular.module('myApp.controllers')

  .controller('TroubleTicketCtrl', ['$scope', '$http', function($scope, $http) {

    // Instantiate an object to store scope data
    $scope.myData = {};

    $http.get("api/troubleTicket")
    .then(function(response) {
        $scope.myData.ticketList = response.data.tickets;
    });
    
    $scope.setCurrentTicket = function(id) {
    	 $http.get("api/troubleTicket/"+id)
    	    .then(function(response) {
    	        $scope.myData.currentTicket = response.data.ticket[0];
    	    });    	  
      };
    
  }])
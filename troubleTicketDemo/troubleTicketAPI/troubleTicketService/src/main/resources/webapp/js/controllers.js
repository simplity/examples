'use strict';

/* Controllers */

angular.module('myApp.controllers')

  .controller('TroubleTicketCtrl', ['$scope', 'TroubleTickets', function($scope, TroubleTickets) {

    // Instantiate an object to store scope data
    $scope.myData = {};

    TroubleTickets.query(function(data) {
      console.log(data);
      $scope.myData.ticketList = data;	
    });

    $scope.setCurrentTicket = function(id) {
      TroubleTickets.getTicket({id: id}, function(data){
        console.log(data);
        $scope.myData.currentTicket = data;
      });
    };

    $scope.showAll = function() {      
      $scope.myData.currentTicket = null;
    };
  }])

  .controller('ProductOfferingCtrl', ['$scope', 'ProductOfferings',function($scope, ProductOfferings) {

    // Instantiate an object to store scope data
    $scope.myData = {};

    ProductOfferings.query(function(data) {
      console.log(data);
      $scope.myData.offeringList = data;	
    });

    $scope.setCurrentOffering = function(id) {
      ProductOfferings.getOffering({id: id}, function(data){
        console.log(data);
        $scope.myData.currentOffering = data;
      });
    };

    $scope.showAll = function() {
      $scope.myData.currentOffering = null;
    };
  }])

  .controller('ProductSpecificationCtrl', ['$scope', 'ProductSpecifications',function($scope, ProductSpecifications) {

    // Instantiate an object to store scope data
    $scope.myData = {};

    ProductSpecifications.query(function(data) {
      console.log(data);
      $scope.myData.specificationList = data;	
    });

    $scope.setCurrentSpecification = function(id) {
      ProductSpecifications.getSpecification({id: id}, function(data){
        console.log(data);
        $scope.myData.currentSpecification = data;
      });
    };

    $scope.showAll = function() {
      $scope.myData.currentSpecification = null;
    };
  }]);

'use strict';

// Declare app level module which depends on filters, and services

angular.module('tmfforum.controllers', []);

angular.module('tmfforum', ['tmfforum.controllers']).
  config(['$routeProvider', function($routeProvider) {
	$routeProvider.when('/login', {templateUrl: 'partials/partial2.html', controller: 'LoginCtrl'});	  
    $routeProvider.when('/view1', {templateUrl: 'partials/partial1.html', controller: 'TroubleTicketCtrl'});
    $routeProvider.otherwise({redirectTo: '/view1'});
  }]);

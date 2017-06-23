'use strict';

// Declare app level module which depends on filters, and services

angular.module('myApp.controllers', []);

angular.module('myApp', ['myApp.controllers']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/view1', {templateUrl: 'partials/partial1.html', controller: 'TroubleTicketCtrl'});
    $routeProvider.otherwise({redirectTo: '/view1'});
  }]);

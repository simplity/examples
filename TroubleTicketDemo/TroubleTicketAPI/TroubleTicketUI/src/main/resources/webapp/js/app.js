'use strict';

// Declare app level module which depends on filters, and services

angular.module('myApp.filters', []);
angular.module('myApp.services', []);
angular.module('myApp.directives', []);
angular.module('myApp.controllers', []);

angular.module('myApp', ['myApp.filters', 'myApp.services', 'myApp.directives', 'myApp.controllers']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/view1', {templateUrl: 'partials/partial1.html', controller: 'TroubleTicketCtrl'});
    $routeProvider.when('/view2', {templateUrl: 'partials/partial2.html', controller: 'TBD1Ctrl'});
    $routeProvider.when('/view3', {templateUrl: 'partials/partial3.html', controller: 'TBD2Ctrl'});
    $routeProvider.otherwise({redirectTo: '/view1'});
  }]);

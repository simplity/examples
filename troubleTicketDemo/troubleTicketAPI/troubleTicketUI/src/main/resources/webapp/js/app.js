'use strict';

angular.module('tmfforum', ['ngRoute','tmfforum.controllers'])
  .config(function($routeProvider,$locationProvider) {  
    $routeProvider.when('/', {templateUrl: 'partials/partial1.html', controller: 'TroubleTicketCtrl'});
    $locationProvider.html5Mode(true);
  });

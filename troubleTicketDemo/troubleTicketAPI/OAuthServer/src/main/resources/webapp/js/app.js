'use strict';

angular.module('tmfforum', ['ngRoute','tmfforum.controllers']).
  config(function($routeProvider,$locationProvider){  
	$routeProvider.when('/', 
						{templateUrl: 'partials/authorize.html',
						 controller: 'AuthCtrl'});
    $locationProvider.html5Mode(true);
  });

'use strict';

/* Controllers */

angular.module('tmfforum.controllers', [])

.controller('AuthCtrl',function($scope,$http,$routeParams,$window){
	
	var redirect_uri = $routeParams.redirect_uri;
	var state = $routeParams.state;
	var scope = $routeParams.scope;
	var response_type = $routeParams.response_type;
	var client_id= $routeParams.client_id; 
	var loginId= "";
	var pwd= "";
	$scope.submitUrl = "/auth/login?redirect_uri=" +
						redirect_uri +
						"&state=" +
						state +
						"&scope=" +
						scope +
						"&response_type=" +
						response_type +
						"&client_id=" +
						client_id;
	
	$http.get('auth/login',{ params:{'scope': scope}}) 
	.then(function(response){
			$scope.scopes  = response.data;
	})
	
	 $scope.submit = function(e){
        e.preventDefault();
        var url = $scope.submitUrl
        var data = {
            'loginId' : $scope.loginId,
            'password': $scope.pwd
        };
        $http.post(url,data).then(function(response){
        	if(response.data){
        		$window.location.href=decodeURIComponent(response.data);
        	}        
        },function(err){
        	console.log(err);
        });
    };
  })
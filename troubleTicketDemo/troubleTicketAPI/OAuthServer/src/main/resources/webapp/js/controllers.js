'use strict';

/* Controllers */

angular.module('tmfforum.controllers', [])

.controller('AuthCtrl',function($scope,$http,$routeParams,$window){
	
	var redirect_uri = $routeParams.redirect_uri;
	var state = $routeParams.state;
	var scope = $routeParams.scope;
	var response_type = $routeParams.response_type;
	var client_id= $routeParams.client_id;
	var correlation_Id= $routeParams.correlation_Id; 
	var loginId= "";
	var pwd= "";
	

	
	var req = {
		 method: 'GET',
		 url: 'auth/login',
		 params: {'scope': scope,
			 	  'correlation_Id':correlation_Id}
		}

	$http(req).then(function(response){
			$scope.scopes  = response.data;
	})


	 $scope.submit = function(e){
        e.preventDefault();
    	var req = {
    			 method: 'POST',
    			 url: '/auth/login',
    			 params: {'redirect_uri': redirect_uri,
   			 	          'state':state,
   			 	          'scope':scope,
   			 	          'response_type':response_type,
   			 	          'client_id':client_id,
   			 	          'correlation_Id':correlation_Id},    			 
    			 data: {
    		            'loginId' : $scope.loginId,
    		            'password': $scope.pwd
    		        }
    			}
        

    	$http(req).then(function(response){
        	if(response.data){
        		$window.location.href=decodeURIComponent(response.data);
        	}        
        },function(err){
        	console.log(err);
        });
    };
  })
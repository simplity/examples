var app = angular.module('mainApp',[]);

app.controller('formCtrl', function ($scope,$http) {
	$scope.todosList = [];
	
	$scope.getTodos = function(){
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		    	$scope.todosList = JSON.parse(this.responseText).todos;
		    	$scope.$apply();
		    }
		  };
		  xhttp.open("GET", "http://localhost:8083/todos/todo", true);
		  xhttp.send();
	}
	
	$scope.insertTodo = function(){
		var title = document.getElementById("myInput").value;
		if(title){
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		    	$scope.getTodos();
		    	document.getElementById("myInput").value = null;
		    }
		  };
		  xhttp.open("POST", "http://localhost:8083/todos/todo", true);
		  xhttp.send(JSON.stringify({task:document.getElementById("myInput").value}));
		}else{
			alert("please add title")
		}
	}

	$scope.updateTodo = function(todo){
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		    	$scope.getTodos();
		    }
		  };
		  todo.status = !(todo.status);
		  xhttp.open("PUT", "http://localhost:8083/todos/todo", true);
		  xhttp.send(JSON.stringify(todo));
	}

	$scope.deleteTodo = function(id){
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		    	$scope.getTodos();
		    }
		  };
		  xhttp.open("DELETE", "http://localhost:8083/todos/todo/"+id, true);
		  xhttp.send();
	}
});
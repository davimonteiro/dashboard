angular.module("ContactManager").controller("LoginController", 
	function($scope, $http, $location, $rootScope, $timeout, LoginService, AuthServerProvider) {
    	$scope.title = "Login Page";
    	
    	LoginService.limparCredenciais();
    	
    	$scope.usuario = {
    		username: 'davimonteiro',
    		password: '12345'
    	};
    	
    	$scope.login = function(usuario) {
    		AuthServerProvider.login(usuario);
    	};
    	
});
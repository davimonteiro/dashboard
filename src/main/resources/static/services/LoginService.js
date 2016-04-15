angular.module("ContactManager").factory('LoginService', function ($http, $rootScope, localStorageService) {
	var service = {};

	service.setarCredenciais = function (usuario) {
		
		$rootScope.globals = {
			usuarioLogado: {
				id: usuario.id,
				username : usuario.username,
				email: usuario.email
			}
		};
		
	};
	
	service.getHeader = function() {
		var config = {
	    		headers : {}	
	    };
	    var token = localStorageService.get('token');
	    
	    if (token && token.expires_at && token.expires_at > new Date().getTime()) {
	        config.headers.Authorization = 'Bearer ' + token.access_token;
	    }
	    
	    return config;
	}
	
	service.limparCredenciais = function () {
		$rootScope.globals = {};
		window.localStorage.clear();
    };

	return service;
});
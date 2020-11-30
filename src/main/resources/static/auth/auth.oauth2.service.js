'use strict';

angular.module('ContactManager')
    .factory('AuthServerProvider', function loginService($http, $rootScope, localStorageService, LoginService, $location) {
        return {
            login: function(usuario) {
                var data = "username=" +  encodeURIComponent(usuario.username) + "&password="
                    + encodeURIComponent(usuario.password) + 
                    "&grant_type=password&scope=read%20write&" +
                    "client_secret=123456&client_id=clientapp";
                return $http.post('oauth/token', data, {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                        "Accept": "application/json",
                        "Authorization": "Basic " + btoa("clientapp" + ':' + "123456")
                    }
                }).success(function (response) {
                    var expiredAt = new Date();
                    expiredAt.setSeconds(expiredAt.getSeconds() + response.expires_in);
                    response.expires_at = expiredAt.getTime();
                    localStorageService.set('token', response);
                    
                    $rootScope.oauth = {
                    		access_toke: response
                    };
                    
                    
                    var config = {
                    		headers : {}	
                    };
                    var token = localStorageService.get('token');
                    
                    if (token && token.expires_at && token.expires_at > new Date().getTime()) {
                        config.headers.Authorization = 'Bearer ' + token.access_token;
                    }
                    
                    $http.get('api/usuarios/current', config).success(function(response) {
                    	console.log(response);
                    	LoginService.setarCredenciais(response);
                    	$location.path("/contatos");
                    });
                    
                    return response;
                });
            },
            logout: function() {
                // logout from the server
                $http.post('api/logout').then(function() {
                    localStorageService.clearAll();
                });
            },
            getToken: function () {
                return localStorageService.get('token');
            },
            hasValidToken: function () {
                var token = this.getToken();
                return token && token.expires_at && token.expires_at > new Date().getTime();
            }
        };
    });

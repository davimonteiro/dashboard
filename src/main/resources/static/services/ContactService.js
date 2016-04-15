angular.module("ContactManager").factory('ContactService', function ($http, $rootScope, localStorageService) {
	var service = {};
	
	service.fetchAll = function () {
		return $http.get('/api/contacts')
		.success(function(data){
			return data;
		})
		.error(function(data, status) {
		  console.error('Repos error', status, data);
		});
		
	};
	service.create = function (contact) {
		return $http.post('/api/contacts/', contact)
		.success(function(data){
			return data;
		})
		.error(function(data, status) {
			console.error('Repos error', status, data);
		});
	};
	
	service.update = function(contact) {
		return $http.put("api/contacts/"+contact.id, contact)
		.success(function(data){
				return data;
		}).error(function(data, status) {
			console.error('Repos error', status, data);
		});
	};
	
	service.delete = function(id) {
		return $http.delete('api/contacts/'+id).then(
			function(response){
				return response;
			}, 
			function(errResponse){
				console.error('Error while deleting user');
			}
		);
	};

	return service;
});
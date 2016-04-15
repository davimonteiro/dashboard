angular.module("ContactManager", ["ngMaterial", "ngMdIcons", "ngMessages", "ngRoute", "LocalStorageModule"])

.config(function($mdThemingProvider, $routeProvider, $httpProvider) {
	
	$routeProvider
	.when('/login', {
		templateUrl : 'views/login.html',
		controller : 'LoginController'
	})
	.when('/contatos', {
		templateUrl : 'views/dashboard.html',
		controller : 'DashboardController'
	})
	.otherwise('/');
	
	$httpProvider.interceptors.push('authExpiredInterceptor');
	$httpProvider.interceptors.push('authInterceptor');
	
})

.run(function ($rootScope, $location) {
	$rootScope.$on('$locationChangeStart', function (event, next, current) {
		if ((!$rootScope.globals || !$rootScope.globals.usuarioLogado)) {
			$location.path('/login');
		}
	});
});
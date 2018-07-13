define([ "app" ], function(app) {

	app.run([ '$state', '$stateParams', '$rootScope', function($state, $stateParams, $rootScope) {
		$rootScope.$state = $state;
		$rootScope.$stateParams = $stateParams;
	} ]);

	app.config([ '$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
		$urlRouterProvider.otherwise('/home/404');
		// 默认视图
		// $urlRouterProvider.when("", "home");
		$urlRouterProvider.when("", "/login");

	} ]);
});

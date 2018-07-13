define([ 'angular', 'app' ], function(angular, app) {
	var URLFlag = false;// false:测试地址,true:正式地址
	var host = 'http://' + window.location.host + "/saafbase/";
	var h = window.location.host
	var p = h.indexOf(':') === -1 ? h : h.substring(h.indexOf(':'))
	h = h.indexOf(':') === -1 ? h : h.substring(0, h.indexOf(':'))
	var domain = [ 'localhost', '10.128.8.192' ];
	var domain2 = [ '127.0.0.1' ];
	//做负载均衡的时候，这里要注释掉 by ldz in 2018-7-2
	host = domain2.indexOf(h) === -1 ? host : 'http://127.0.0.1' + p + '/saafbase/';
	host = domain.indexOf(h) === -1 ? host : 'http://10.128.8.192:8080/saafbase/';
	var testUrl = host;
	// var productUrl = 'http://www.fhshmall.com';
	var _url = URLFlag ? host : testUrl;

	window.serviceURL = _url;// 全局变量，供其它框架或库使用

	var module = angular.module('URLAPI', []);
	module.constant('URLAPI', {

		// 快速生成代码·新
		newFastGeneration : _url + 'restServer/newFastGeneraServices/newFastGeneration',
		// 快速生成代码·读取列表文件
		readSystemFile : _url + 'restServer/newFastGeneraServices/readSystemFile',
		// 快速生成代码·添加element
		addElement : _url + 'restServer/newFastGeneraServices/addElement',
		// 快速生成代码·修改element
		updateElement : _url + 'restServer/newFastGeneraServices/updateElement',
		// 快速生成代码·删除element
		deleteElement : _url + 'restServer/newFastGeneraServices/deleteElement',
		// 快速生成代码·添加platform
		addPlatform : _url + 'restServer/newFastGeneraServices/addPlatform',
		// 快速生成代码·修改platform
		updatePlatform : _url + 'restServer/newFastGeneraServices/updatePlatform',
		// 快速生成代码·删除platform
		deletePlatform : _url + 'restServer/newFastGeneraServices/deletePlatform',
		// 快速生成代码·读取配置文件
		readConfigFile : _url + 'restServer/newFastGeneraServices/readConfigFile',
		// 快速生成代码·写入配置文件
		writeConfigFile : _url + 'restServer/newFastGeneraServices/writeConfigFile',

	});

	return module;
});

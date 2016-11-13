// Angular & Router ES6 Imports
import angular from 'angular';
import angularUIRouter from 'angular-ui-router';
import appComponents from './components/components.js';
import commonComponents from './common/components.js';
import appServices from './services/services.js';
import appConfiguration from './app.config';

// Single Style Entry Point
import './index.scss';

require('imports?angular=angular!angular-base64/angular-base64');
require('imports?angular=angular!ngstorage/ngStorage');
if (ENVIRONMENT === 'test') {
  console.log('ENV:', ENVIRONMENT);
  require('angular-mocks/angular-mocks');
}

const app = angular.module('app', ['ui.router', 'base64', 'ngStorage']);

// Components Entrypoint
appComponents(app);

// Common Components Entrypoint
commonComponents(app);

appConfiguration(app);

// App Services Entrypoint
appServices(app);

app.service('authInterceptor', function($q, tokenService) {
  var service = this;

  service.request = function (config) {
    if (tokenService.getToken()) {
      config.headers['x-auth-token'] = tokenService.getToken();
    }
    return config;
  }

  service.responseError = function(response) {
    if (response.status === 401){
      tokenService.removeToken();
      window.location = "/#/login";
    }
    return $q.reject(response);
  };
}).config(['$httpProvider', function($httpProvider) {
      $httpProvider.interceptors.push('authInterceptor');
}])

// Router Configuration
// Components must be declared first since
// Routes reference controllers that will be bound to route templates.

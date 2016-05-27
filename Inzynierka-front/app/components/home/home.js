import homeComponent from './home.component';

export default app => {
    app.config(($stateProvider, $urlRouterProvider) => {
        $urlRouterProvider.otherwise('/');

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'templates/home.html'
            })
            .state('login', {
                url: '/login',
                template: '<t-login></t-login>'
                //templateUrl: 'templates/login.html'
            })
            .state('register', {
                url: '/register',
                template: '<t-register></t-register>'
            })
            .state('logged', {
                url: '/logged',
                template: '<t-user-data-view></t-user-data-view>'
            })
            .state('logged.yourTopics', {
                url: '/yourTopics',
                templateUrl: 'templates/app/yourTopics.html'
            })
            .state('logged.viewInfo', {
                url: '/viewInfo',
                templateUrl: '<t-offers-view></t-offers-view>'
            })
            .state('logged.news', {
                url: '/news',
                templateUrl: 'templates/app/news.html'
            })
    }).directive('home', homeComponent);

    if (ENVIRONMENT === 'test') {
        require('./home.test.js');
    }
}

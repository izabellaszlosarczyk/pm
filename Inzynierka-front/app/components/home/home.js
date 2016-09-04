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
                templateUrl: '<t-info-view></t-info-view>'
            })
            .state('logged.news', {
                url: '/news',
                templateUrl: 'templates/app/news.html'
            })
            .state('logged.yourStuff', {
                url: '/yourStuff',
                templateUrl: 'templates/app/yourStuff.html'
            })
            .state('logged.privacy', {
                url: '/privacy',
                templateUrl: 'templates/app/privacy.html'
            })
            .state('logged.edit', {
                url: '/editUser',
                template: '<t-user-data-edit></t-user-data-edit>'
            })
            .state('logged.terms', {
                url: '/terms',
                templateUrl: 'templates/app/terms.html'
            })
            .state('logged.choices', {
                url: '/choices',
                templateUrl: 'templates/app/choices.html'
            })
            .state('logged.cookies', {
                url: '/cookies',
                templateUrl: 'templates/app/cookies.html'
            })
    }).directive('home', homeComponent);

    if (ENVIRONMENT === 'test') {
        require('./home.test.js');
    }
}

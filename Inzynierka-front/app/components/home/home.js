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
            .state('logged.subs', {
                url: '/subscriptions',
                template: '<t-subs></t-subs>'
            })
            .state('logged.viewInfo', {
                url: '/viewInfo',
                template: '<t-info-view></t-info-view>'
            })
            .state('logged.news', {
                url: '/news',
                template: '<t-news></t-news>'
            })
            .state('logged.decision', {
                url: '/decision',
                template: '<t-decision></t-decision>'
            })
            .state('logged.apriori', {
                url: '/apriori',
                template: '<t-apriori></t-apriori>'
            })
            .state('logged.yourStuff', {
                url: '/yourStuff',
                template: '<t-user-stuff></t-user-stuff>'
            })
            .state('logged.patterns', {
                url: '/patterns',
                template: '<t-patterns></t-patterns>'
            })
            .state('logged.all', {
                url: '/all',
                template: '<t-all></t-all>'
            })
            .state('logged.cvs', {
                url: '/cvs',
                template: '<t-cvs></t-cvs>'
            })
            .state('logged.images', {
                url: '/images',
                template: '<t-images></t-images>'
            })
            .state('logged.others', {
                url: '/others',
                template: '<t-others></t-others>'
            })
            .state('info', {
                url: '/info',
                template: '<t-info></t-info>'
            })
            .state('logged.charts', {
                url: '/charts',
                template: '<t-charts></t-charts>'
            })
            .state('logged.fileDetails', {
                url: '/fileDetails',
                template: '<t-file></t-file>'
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
            .state('logged.file', {
                url: '/file',
                template: '<t-file></t-file>'
            })
    }).directive('home', homeComponent);

    if (ENVIRONMENT === 'test') {
        require('./home.test.js');
    }
}

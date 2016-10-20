export default class FileController {
  constructor(usersService, $state, $http, tokenService, $rootScope) {
    this.usersService = usersService;
    this.tokenService = tokenService;
    this.$rootScope = $rootScope;
    this.email = '';
    this.password = '';
    this.$state = $state;
    this.$http = $http;
    this.token = '';

    if(this.usersService.getUserSessionData() && this.tokenService.getToken()) {
      // init data from local storage
      this.$rootScope.$broadcast('user-login');
      this.$state.go('logged');
    }
  }
}



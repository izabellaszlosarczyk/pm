export default class LoginController {
  constructor(usersService, $state, $http, tokenService, $rootScope) {
    this.usersService = usersService;
    this.tokenService = tokenService;
    this.$rootScope = $rootScope;
    this.email = '';
    this.password = '';
    this.$state = $state;
    this.$http = $http;
    this.token = '';
    this.errorLogin =  false;

    if(this.usersService.getUserSessionData() && this.tokenService.getToken()) {
      // init data from local storage
      this.$rootScope.$broadcast('user-login');
      this.$state.go('logged');
    }
  }

  login() {
    let data = {
      email: this.email,
      password: this.password
    };

    this.usersService.login(data).then(function successCallback(response, status, headers, config) {
      this.usersService.setUserSessionData(response.data);
      console.log(response.data);
      this.token = response.headers()['x-auth-token'];
      this.tokenService.setToken(this.token);
      console.log(data);
      this.usersService.getUserData(data).then(function successCallback(response, status, headers, config) {
        this.usersService.setUserDataValues(response.data);
        this.$rootScope.$broadcast('user-login');
        this.$state.go('logged');
      }.bind(this));
    }.bind(this), function errorCallback(response) {
      console.log(response.data);
      if (response.data.error = "Unauthorized"){
        this.errorLogin =  true;
      }
      /*this.token = response.data.token;
      console.log(response.data)*/
    }.bind(this));
  }
}



export default class LoginController {
  constructor(usersService, $state, $http, tokenService) {
    this.usersService = usersService;
    this.tokenService = tokenService;
    this.email = '';
    this.password = '';
    this.$state = $state;
    this.$http = $http;
    this.token = '';
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

      this.usersService.getUserData(data).then(function successCallback(response, status, headers, config) {
        this.$state.go('logged');
      });
    }.bind(this), function errorCallback(response) {
      console.log(response);
      /*this.token = response.data.token;
      console.log(response.data)*/
    }.bind(this));
  }
}



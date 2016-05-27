export default class LoginController {
  constructor(usersService, $state) {
    this.usersService = usersService;
    this.email = '';
    this.password = '';
    this.$state = $state;
    this.token = '';
  }

  login() {
    let data = {
      email: this.email,
      password: this.password
    };
    this.usersService.login(data).then(function successCallback(response) {
      this.usersService.setUserSessionData(response.data);
      this.token = response.data.token;
      this.$state.go('logged');
      console.log(this.usersService.getUserSessionData());
    }.bind(this), function errorCallback(response) {
      this.token = response.data.token;
      console.log(response.data)
    }.bind(this));
  }
}

export default class NavbarController {
  constructor(usersService, tokenService, $state, $rootScope, $scope) {
    this.$scope = $scope;
    this.tokenService = tokenService;
    this.usersService = usersService;
    this.$rootScope = $rootScope;
    this.logged = false;
    this.user = '';

    this.logout = () => {
      console.log('logout');
      this.tokenService.removeToken();
      this.usersService.removeUserSessionData();
      this.usersService.removeUserDataValues();
    };

    this.$rootScope.$on('user-login', () => {
      this.logged = true;
    });
  }
}

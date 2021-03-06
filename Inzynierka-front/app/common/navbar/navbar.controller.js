export default class NavbarController {
  constructor(usersService, tokenService, $state, $rootScope, $scope) {
    this.$scope = $scope;
    this.tokenService = tokenService;
    this.usersService = usersService;
    this.$rootScope = $rootScope;
    this.logged = false;
    this.user = '';
    this.userName = '';
    this.userLastName = '';
    this.logout = () => {
      console.log('logout');
      this.tokenService.removeToken();
      this.usersService.removeUserSessionData();
      this.usersService.removeUserDataValues();
    };
    this.$rootScope.isLogged = function() {

    };

    this.$rootScope.$on('user-login', () => {
      this.logged = true;
      this.userLastName = usersService.userData.email;
    });
  }
  logout(){
    console.log('logout');
    this.tokenService.removeToken();
    this.usersService.removeUserSessionData();
    this.usersService.removeUserDataValues();
    this.usersService.$storage.logged = false;
    this.$rootScope.$broadcast('user-login', false);
    this.usersService.logout();
    // this.usersService.changeTimeOfLog(usersService.userData.email).then(function successCallback(response, status, headers, config) {
    //   console.log("okej wylogowanie");
    //   this.tokenService.removeToken();
    //   this.usersService.removeUserSessionData();
    //   this.usersService.removeUserDataValues();
    //   this.usersService.$storage.logged = false;
    //   this.$rootScope.$broadcast('user-login', false);
    //   this.usersService.logout();
    // }.bind(this));

  }

}

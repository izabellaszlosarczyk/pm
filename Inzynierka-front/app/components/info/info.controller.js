export default class InfoController {
  constructor(infoService, usersService,$state, $http,$scope, $stateParams) {
    this.state = $state;
    this.stateParams = $stateParams;
    this.$scope = $scope;
    this.infoService = infoService;
    this.usersService = usersService;
    this.userData;
    this.filesDetails = [];
    this.imageFiles = [];
    this.loading = true;
    this.$http = $http;
  }
  $onInit() {

  }

 

}



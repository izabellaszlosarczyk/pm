export default class ChartsController {
  constructor(infoService, usersService, $state, $http,$scope, $stateParams) {
    this.infoService = infoService;
    this.usersService = usersService;
    this.loading = true;
    this.state = $state;
    this.stateParams = $stateParams;
    this.$scope = $scope;
    this.$http = $http;


    this.filesDetails = [];
  }
  
  $onInit() {
    console.log("PATTERNS");
    this.usersService.loadFiles().then(function successCallback(response, status, headers, config) {
      this.loading = false;
      this.filesDetails= response.data;
      console.log(this.filesDetails[11]);
      console.log(this.filesDetails);
    }.bind(this));
  }

  deleteFromSubs(fileDetails){
    //this.usersService.setRequestedFileDetails(fileDetails);
    console.log(this.usersService.userData);
    console.log(fileDetails);
    let data = {
      email: this.usersService.userData.email,
      title: fileDetails.title
    };
    this.usersService.deleteFileFromSubs(data);
  }


  viewFile(fileDetails){
    this.usersService.addRequestedFileDetails(fileDetails);
    console.log(this.usersService.requestedFileDetails);
    console.log(fileDetails);
    this.state.go('logged.fileDetails', fileDetails.title);
  }


}



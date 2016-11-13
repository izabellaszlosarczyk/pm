export default class SubsController {
  constructor(infoService, usersService,$state, $http,$scope, $stateParams) {
    this.state = $state;
    this.stateParams = $stateParams;
    this.$scope = $scope;
    this.infoService = infoService;
    this.usersService = usersService;
    this.filesDetails = [];
    this.imageFiles = [];
    this.loading = true;
    this.$http = $http;
    this.userData;
    this.filesDetails = [];
    this.loading = true;
  }

  $onInit() {
    this.userData = this.usersService.getUserDataValues();
    console.log("sgtggeffrghghhhhhhhhhhhhtththththrththtrh");
    console.log(this.userData);
    console.log(this.userData.savedFiles);


    //PONIZEJ ZMIENIC NA SUBSCRIBED z SAVED
    this.usersService.getFilesDetails(this.userData.savedFiles).then(function successCallback(response, status, headers, config) {
      this.loading = false;
      this.filesDetails= response.data;
      console.log("duuuuuuuuuuuuuuuupa");
      console.log(this.filesDetails);
    }.bind(this));
    // this.filesDetails.push(response.data);
    //this.usersService.getFileDetails()
    //this.userNewData = this.userData;
    //console.log(this.userNewData);
  }

  deleteFromSubs(fileDetails){
    //this.usersService.setRequestedFileDetails(fileDetails);
    console.log(this.usersService.userData);
    console.log(fileDetails);
    let data = {
      userEmail: this.usersService.userData.email,
      fileName: fileDetails.title
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



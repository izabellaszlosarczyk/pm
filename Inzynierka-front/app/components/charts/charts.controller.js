export default class ChartsController {
  constructor(infoService, usersService) {
    this.infoService = infoService;
    this.usersService = usersService;
    this.userData;
    this.filesDetails = [];
    this.loading = true;
  }

  $onInit() {
    this.userData = this.usersService.getUserDataValues();
    console.log(this.userData.savedFiles);
    // for (let file of this.userData.savedFiles){
    //   this.usersService.getFileDetails(file).then(function successCallback(response, status, headers, config) {
    //     this.filesDetails.push(response.data);
    //     console.log(this.filesDetails);
    //   }.bind(this), function errorCallback(response) {
    //     console.log(response);
    //     console.log("whyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
    //   }.bind(this));
    // }
    this.usersService.getFilesDetails(this.userData.savedFiles).then(function successCallback(response, status, headers, config) {
      this.loading = false;
      this.filesDetails= response.data;
      console.log("duuuuuuuuuuuuuuuupa");
      console.log(this.filesDetails);
    }.bind(this));
  }

  deleteFromSubs(chart){

  }

  viewFile(chart){

  }
}



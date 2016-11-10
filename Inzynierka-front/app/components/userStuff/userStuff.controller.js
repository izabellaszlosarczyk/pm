export default class UserStuffController {
  constructor(infoService, usersService) {
    this.infoService = infoService;
    this.usersService = usersService;
    this.userData;
    this.filesDetails = [];
  }

  $onInit() {
    this.userData = this.usersService.getUserDataValues();
    console.log("dupa");
    console.log(this.userData);
    console.log(this.userData.savedFiles);
    for (let file of this.userData.savedFiles){
      console.log(file);
      this.infoService.getFileDetails(file).then(function successCallback(response, status, headers, config) {
        this.filesDetails.push(response.data);
        console.log(response.data);
      }.bind(this), function errorCallback(response) {
        console.log(response);
      }.bind(this));
    }
    //this.usersService.getFileDetails()
    //this.userNewData = this.userData;
    //console.log(this.userNewData);
  }
}



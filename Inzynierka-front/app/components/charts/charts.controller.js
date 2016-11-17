export default class ChartsController {
  constructor(infoService, usersService) {
    this.infoService = infoService;
    this.usersService = usersService;
    this.userData;
    this.filesDetails = [];
    this.loading = true;
    this.chartFiles = [];
  }

  $onInit() {
    this.userData = this.usersService.getUserDataValues();
    console.log(this.userData.savedFiles);
    this.usersService.getFilesDetails(this.userData.savedFiles).then(function successCallback(response, status, headers, config) {
      this.loading = false;
      this.filesDetails= response.data;
      console.log("duuuuuuuuuuuuuuuupa");
      console.log(this.filesDetails);
    }.bind(this));
    for (var i in this.filesDetails) {
      if (this.filesDetails[i].type == "chart"){
        console.log(this.userData.savedFiles[i]);
        this.chartFiles.push(this.filesDetails[i]);
        console.log("MASAKRA");

      }
    }
    console.log(this.chartFiles);
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
    //this.usersService.setRequestedFileDetails(fileDetails);
    console.log(fileDetails);
  }
}



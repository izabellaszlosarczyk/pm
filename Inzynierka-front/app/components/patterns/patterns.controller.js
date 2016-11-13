export default class PatternsController {
  constructor(infoService, usersService) {
    this.infoService = infoService;
    this.usersService = usersService;
    this.userData;
    this.patternFiles = [];
    this.filesDetails = [];
    this.loading = true;
  }

  $onInit() {
    console.log("PATTERNS");
    this.userData = this.usersService.getUserDataValues();
    console.log(this.userData.savedFiles);
    this.usersService.getFilesDetails(this.userData.savedFiles).then(function successCallback(response, status, headers, config) {
      this.loading = false;
      this.filesDetails= response.data;
      console.log(this.filesDetails);
    }.bind(this));
    for (var i in this.filesDetails) {
      if (this.filesDetails[i].type == "pattern"){
        console.log(this.userData.savedFiles[i]);
        this.patternFiles.push(this.filesDetails[i]);
      }
    }
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



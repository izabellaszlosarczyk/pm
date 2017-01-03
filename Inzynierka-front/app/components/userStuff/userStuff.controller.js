export default class UserStuffController {
  constructor(infoService, usersService,  $state, $http,$scope, $stateParams) {
    this.infoService = infoService;
    this.usersService = usersService;
    this.userData;
    this.filesDetails = [];
    this.loading = true;
    this.inputContent;
    this.allFiles = [];
    this.state = $state;
    this.stateParams = $stateParams;
    this.$scope = $scope;
    this.$http = $http;
  }

  $onInit() {
    this.userData = this.usersService.getUserDataValues();
    console.log(this.userData);
    this.allFiles.push(this.userData.savedFiles);
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
    this.usersService.analysesType = fileDetails.type;
    this.usersService.getFile(fileDetails.title).then(function successCallback(response, status, headers, config) {
      var decoder = new TextDecoder("utf-8");
      //decoder.decode(new Uint8Array(response.data));
      if (this.usersService.analysesType == "apriori" || this.usersService.analysesType == "decision"){
        this.jsonToVizualization = JSON.parse(decoder.decode(new Uint8Array(response.data)));
      }else {
        this.jsonToVizualization = decoder.decode(new Uint8Array(response.data));
      }
      console.log(decoder.decode(new Uint8Array(response.data)));
      this.usersService.jsonToVisualisation = this.jsonToVizualization;
      this.state.go('logged.fileDetails', fileDetails.title);
      //console.log(this.jsonToVizualization);
    }.bind(this));
  }
  
  uploadNewFile(){
    
  }
}



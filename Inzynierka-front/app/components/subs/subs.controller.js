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
    this.empty = 1;
  }

  $onInit() {
    console.log("sgtggeffrghghhhhhhhhhhhhtththththrththtrh");
    this.userData = this.usersService.getUserDataValues();
    console.log(this.userData);


    this.usersService.getFilesDetails(this.userData.subscribedFiles).then(function successCallback(response, status, headers, config) {
      this.loading = false;
      this.filesDetails= response.data;
      console.log(this.filesDetails);
      if (typeof this.filesDetails !== 'undefined' && this.filesDetails.length > 0) {
        this.empty = 0;
      }
    }.bind(this));
    // this.filesDetails.push(response.data);
    //this.usersService.getFileDetails()
    //this.userNewData = this.userData;
    //console.log(this.userNewData);
  }

  deleteFromSubs(fileDetails){
    //this.usersService.setRequestedFileDetails(fileDetails);
    let data = {
      userEmail: this.usersService.userData.email,
      fileName: fileDetails.title
    };
    this.usersService.deleteFileFromSubs(data).then(function successCallback(response, status, headers, config) {
      var index = this.usersService.userData.subscribedFiles.indexOf(fileDetails.title);
      if (index > -1) {
        console.log("usuwam");
        this.usersService.userData.subscribedFiles.splice(index, 1);
      }
      console.log(this.usersService.$storage.userData.subscribedFiles);
      index = this.usersService.$storage.userData.subscribedFiles.indexOf(fileDetails.title);
      if (index > -1) {
        console.log("usuwam");
        this.usersService.$storage.userData.subscribedFiles.splice(index, 1);
      }
      console.log(this.userData.subscribedFiles);
      index = this.userData.subscribedFiles.indexOf(fileDetails.title);
      if (index > -1) {
        console.log("usuwam");
        this.userData.subscribedFiles.splice(index, 1);
      }
    }.bind(this));
  }

  viewFile(fileDetails){
    this.usersService.addRequestedFileDetails(fileDetails);
    this.usersService.analysesType = fileDetails.type;
    this.usersService.getFile(fileDetails.title).then(function successCallback(response, status, headers, config) {
      var decoder = new TextDecoder("utf-8");
      //decoder.decode(new Uint8Array(response.data));
      if (this.usersService.analysesType == "apriori" || this.usersService.analysesType == "decision"){
        this.jsonToVizualization = JSON.parse(decoder.decode(new Uint8Array(response.data)));
      }else {
        this.jsonToVizualization = decoder.decode(new Uint8Array(response.data));
      }
      this.usersService.jsonToVisualisation = this.jsonToVizualization;
      this.state.go('logged.fileDetails', fileDetails.title);
      //console.log(this.jsonToVizualization);
    }.bind(this));
  }
}



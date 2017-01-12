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
    this.filesTmp = [];
  }

  $onInit() {
    this.userData = this.usersService.getUserDataValues();


    this.usersService.getFilesDetails(this.userData.subscribedFiles).then(function successCallback(response, status, headers, config) {
      this.loading = false;
      for (var i in  response.data) {
        if (response.data[i].type !== "image") {
          this.filesTmp.push(response.data[i]);
        }
      }
      this.filesDetails= this.filesTmp;
      if (typeof this.filesDetails !== 'undefined' && this.filesDetails.length > 0) {
        this.empty = 0;
      }
    }.bind(this));
  }

  deleteFromSubs(fileDetails){
    let data = {
      userEmail: this.usersService.userData.email,
      fileName: fileDetails.title
    };
    this.usersService.deleteFileFromSubs(data).then(function successCallback(response, status, headers, config) {
      var index = this.usersService.userData.subscribedFiles.indexOf(this.usersService.userData.email);
      if (index > -1) {
        this.usersService.userData.subscribedFiles.splice(index, 1);
      }
      index = this.usersService.$storage.userData.subscribedFiles.indexOf(this.usersService.userData.email);
      if (index > -1) {
        this.usersService.$storage.userData.subscribedFiles.splice(index, 1);
      }
      index = this.userData.subscribedFiles;
      if (index > -1) {
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
      if(typeof this.jsonToVizualization !== "object"){
        this.usersService.jsonToVizualization = JSON.parse( this.jsonToVizualization );
      }else{
        this.usersService.jsonToVisualisation = this.jsonToVizualization;
      }
      this.state.go('logged.fileDetails', fileDetails.title);
      //console.log(this.jsonToVizualization);
    }.bind(this));
  }
}



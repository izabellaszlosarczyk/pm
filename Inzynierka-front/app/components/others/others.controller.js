export default class OthersController {
  constructor(infoService, usersService, $state, $http,$scope, $stateParams) {
    this.infoService = infoService;
    this.usersService = usersService;
    this.userData;
    this.filesDetails = [];
    this.loading = true;
    this.otherFiles = [];
    this.state = $state;
    this.stateParams = $stateParams;
    this.$scope = $scope;
    this.$http = $http;
    this.empty = 1;
  }
  //straight
  $onInit() {
    this.userData = this.usersService.getUserDataValues();
    this.usersService.getFilesDetails(this.userData.savedFiles).then(function successCallback(response, status, headers, config) {
      this.loading = false;
      this.filesDetails= response.data;
      for (var i in this.filesDetails) {
        if (this.filesDetails[i].type == "straight"){
          this.otherFiles.push(this.filesDetails[i]);
        }
      }
      if (typeof this.otherFiles !== 'undefined' && this.otherFiles.length > 0) {
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
      var index = this.usersService.userData.subscribedFiles.indexOf(fileDetails.title);
      if (index > -1) {
        this.usersService.userData.subscribedFiles.splice(index, 1);
      }
      index = this.usersService.$storage.userData.subscribedFiles.indexOf(fileDetails.title);
      if (index > -1) {
        this.usersService.$storage.userData.subscribedFiles.splice(index, 1);
      }
      index = this.userData.subscribedFiles.indexOf(fileDetails.title);
      if (index > -1) {
        this.userData.subscribedFiles.splice(index, 1);
      }
    }.bind(this));
  }

  viewFile(fileDetails){
    //this.usersService.setRequestedFileDetails(fileDetails);
    this.usersService.addRequestedFileDetails(fileDetails);
    this.usersService.getFile(fileDetails.title).then(function successCallback(response, status, headers, config) {
      var decoder = new TextDecoder("utf-8");
      //decoder.decode(new Uint8Array(response.data));
      this.jsonToVizualization = decoder.decode(new Uint8Array(response.data));
      if(typeof this.jsonToVizualization !== "object"){
        this.usersService.jsonToVizualization = JSON.parse( this.jsonToVizualization );
      }else{
        this.usersService.jsonToVisualisation = this.jsonToVizualization;
      }
      //this.usersService.jsonToVisualisation = this.jsonToVizualization;
      this.usersService.analysesType = fileDetails.type;
      this.state.go('logged.fileDetails', fileDetails.title);
    }.bind(this));
  }
}



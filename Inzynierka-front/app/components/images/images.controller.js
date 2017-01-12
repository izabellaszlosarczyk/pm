export default class ImagesController {
  constructor(infoService, usersService,$state, $http,$scope, $stateParams) {
    this.state = $state;
    this.stateParams = $stateParams;
    this.$scope = $scope;
    this.infoService = infoService;
    this.usersService = usersService;
    this.userData;
    this.filesDetails = [];
    this.imageFiles = [];
    this.loading = true;
    this.$http = $http;
    this.jsonToVizualization = "";
    this.empty = 1;
  }
  //graph
  $onInit() {
    this.userData = this.usersService.getUserDataValues();
    
    this.usersService.getFilesDetails(this.userData.savedFiles).then(function successCallback(response, status, headers, config) {
      this.loading = false;
      this.filesDetails= response.data;
      for (var i in this.filesDetails) {
        if (this.filesDetails[i].type == "graph"){
          this.imageFiles.push(this.filesDetails[i]);
        }
      }
      if (typeof this.imageFiles !== 'undefined' && this.imageFiles.length > 0) {
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
  html_creator(fileDetails){
    var sref_name =  "home(logged/fileDetails/{id:" +fileDetails.title+"})"
    return sref_name
  }

  viewFile(fileDetails){
    this.usersService.addRequestedFileDetails(fileDetails);
    this.usersService.getFile(fileDetails.title).then(function successCallback(response, status, headers, config) {
      var decoder = new TextDecoder("utf-8");
      //decoder.decode(new Uint8Array(response.data));
      if (this.usersService.analysesType == "apriori" || this.usersService.analysesType == "decision"){
        this.jsonToVizualization = JSON.parse(decoder.decode(new Uint8Array(response.data)));
      }else {
        this.jsonToVizualization = decoder.decode(new Uint8Array(response.data));
      }
      if(typeof this.jsonToVizualization !== "object")
        this.jsonToVizualization = JSON.parse( this.jsonToVizualization );
      console.log(decoder.decode(new Uint8Array(response.data)));
      this.usersService.jsonToVisualisation = this.jsonToVizualization;
      this.usersService.analysesType = fileDetails.type;
      this.state.go('logged.fileDetails', fileDetails.title);
    }.bind(this));
  }

}



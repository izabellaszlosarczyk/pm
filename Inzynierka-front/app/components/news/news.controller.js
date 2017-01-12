export default class NewsController {
  constructor(infoService, usersService, $state, $http,$scope, $stateParams) {
    this.infoService = infoService;
    this.usersService = usersService;
    this.loading = true;
    this.state = $state;
    this.stateParams = $stateParams;
    this.$scope = $scope;
    this.$http = $http;


    this.filesDetails = [];
    this.empty = 1;
  }

  $onInit() {
    this.usersService.getNewsFiles(this.usersService.userData.email).then(function successCallback(response, status, headers, config) {
      this.loading = false;
      console.log(this.filesDetails);
      this.filesDetails= response.data;
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
      var index = this.usersService.userData.subscribedFiles.indexOf(fileDetails.title);
      if (index > -1) {
        this.usersService.userData.subscribedFiles.splice(index, 1);
      }
      index = this.usersService.$storage.userData.subscribedFiles.indexOf(fileDetails.title);
      if (index > -1) {
        this.usersService.$storage.userData.subscribedFiles.splice(index, 1);
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
      //this.usersService.jsonToVisualisation = this.jsonToVizualization;
      this.state.go('logged.fileDetails', fileDetails.title);
    }.bind(this));
  }


}





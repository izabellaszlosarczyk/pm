export default class ChartsController {
  constructor(infoService, usersService, $state, $http,$scope, $stateParams) {
    this.infoService = infoService;
    this.usersService = usersService;
    this.loading = true;
    this.state = $state;
    this.stateParams = $stateParams;
    this.$scope = $scope;
    this.$http = $http;
    this.filesTmp = [];


    this.filesDetails = [];
  }
  
  $onInit() {
    this.usersService.loadFiles().then(function successCallback(response, status, headers, config) {
      this.loading = false;
      for (var i in  response.data) {
        if (response.data[i].type !== "image") {
          this.filesTmp.push(response.data[i]);
          this.empty = 0;
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
      email: this.usersService.userData.email,
      title: fileDetails.title
    };
    this.usersService.deleteFileFromSubs(data);
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
      if(typeof this.jsonToVizualization !== "object")
        this.jsonToVizualization = JSON.parse( this.jsonToVizualization );
      console.log(decoder.decode(new Uint8Array(response.data)));
      this.usersService.jsonToVisualisation = this.jsonToVizualization;
      this.state.go('logged.fileDetails', fileDetails.title);
    }.bind(this));
  }


}



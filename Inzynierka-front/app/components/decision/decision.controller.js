export default class DecisionController {
  constructor(infoService, usersService, $state, $http,$scope, $stateParams) {
    this.infoService = infoService;
    this.usersService = usersService;
    this.userData;
    this.filesDetails = [];
    this.loading = true;
    this.chartFiles = [];
    this.state = $state;
    this.stateParams = $stateParams;
    this.$scope = $scope;
    this.$http = $http;
  }
  //barCharts

  $onInit() {
    this.userData = this.usersService.getUserDataValues();
    this.usersService.getFilesDetails(this.userData.savedFiles).then(function successCallback(response, status, headers, config) {
      this.loading = false;
      console.log("MASAKRACJA");
      this.filesDetails= response.data;
      console.log(this.filesDetails);
      for (var i in this.filesDetails) {
        if (this.filesDetails[i].type == "decision") {
          this.chartFiles.push(this.filesDetails[i]);
          console.log("MASAKRA");
        }
      }
    }.bind(this));


  }

  deleteFromSubs(fileDetails){
    //this.usersService.setRequestedFileDetails(fileDetails);
    console.log(this.usersService.userData);
    console.log(fileDetails);
    let data = {
      email: this.usersService.userData.email,
      title: fileDetails.title
    };
    this.usersService.deleteFileFromSubs(data);
  }


  viewFile(fileDetails){
    console.log(fileDetails);
    this.usersService.addRequestedFileDetails(fileDetails);
    this.usersService.getFile(fileDetails.title).then(function successCallback(response, status, headers, config) {
      var decoder = new TextDecoder("utf-8");
      //decoder.decode(new Uint8Array(response.data));
      this.jsonToVizualization = JSON.parse(decoder.decode(new Uint8Array(response.data)));
      //console.log(decoder.decode(new Uint8Array(response.data)));
      this.usersService.jsonToVisualisation = this.jsonToVizualization;
      this.usersService.analysesType = fileDetails.type;
      console.log("dupa");
      console.log(this.usersService.requestedFileDetails);
      console.log(this.usersService.analysesType);
      this.state.go('logged.fileDetails', fileDetails.title);
      //console.log(this.jsonToVizualization);
    }.bind(this));
  }


}


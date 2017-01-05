export default class AprioriController {
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
    this.empty = 1;
  }
  //apriori

  $onInit() {
    this.userData = this.usersService.getUserDataValues();
    this.usersService.getFilesDetails(this.userData.savedFiles).then(function successCallback(response, status, headers, config) {
      this.loading = false;
      console.log("MASAKRACJA");
      this.filesDetails= response.data;
      console.log(this.filesDetails);
      for (var i in this.filesDetails) {
        console.log(i);
        if (this.filesDetails[i].type == "apriori") {
          console.log(this.userData.savedFiles[i]);
          this.chartFiles.push(this.filesDetails[i]);
          console.log("MASAKRA");
        }
        if (typeof this.chartFiles !== 'undefined' && this.chartFiles.length > 0) {
          this.empty = 0;
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
    this.usersService.addRequestedFileDetails(fileDetails);
    console.log(this.usersService.requestedFileDetails);
    this.usersService.analysesType = fileDetails.type;
    this.usersService.getFile(fileDetails.title).then(function successCallback(response, status, headers, config) {
      var decoder = new TextDecoder("utf-8");
      //decoder.decode(new Uint8Array(response.data));
      this.jsonToVizualization = JSON.parse(decoder.decode(new Uint8Array(response.data)));
      console.log(decoder.decode(new Uint8Array(response.data)));
      this.usersService.jsonToVisualisation = this.jsonToVizualization;
      this.state.go('logged.fileDetails', fileDetails.title);
      //console.log(this.jsonToVizualization);
    }.bind(this));
  }


}



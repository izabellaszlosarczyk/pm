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
    this.empty = 1;
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
      if (typeof this.chartFiles !== 'undefined' && this.chartFiles.length > 0) {
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



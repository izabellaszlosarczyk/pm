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
  }

  $onInit() {
    console.log("NEWS");
    console.log(this.usersService.userData.email);
    this.usersService.getNewsFiles(this.usersService.userData.email).then(function successCallback(response, status, headers, config) {
      this.loading = false;
      this.filesDetails= response.data;
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


}





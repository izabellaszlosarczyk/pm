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
    this.jsonToVizualization = ""
  }
  //graph
  $onInit() {
    this.userData = this.usersService.getUserDataValues();
    console.log(this.userData.savedFiles);
    
    this.usersService.getFilesDetails(this.userData.savedFiles).then(function successCallback(response, status, headers, config) {
      this.loading = false;
      this.filesDetails= response.data;
      console.log(this.filesDetails);
      for (var i in this.filesDetails) {
        if (this.filesDetails[i].type == "graph"){
          console.log(this.userData.savedFiles[i]);
          this.imageFiles.push(this.filesDetails[i]);
        }
      }
    }.bind(this));

    // this.filesDetails.push(response.data);
    //this.usersService.getFileDetails()
    //this.userNewData = this.userData;
    //console.log(this.userNewData);
  }

  deleteFromSubs(fileDetails){
    console.log(this.usersService.userData);
    console.log(fileDetails);
    let data = {
      email: this.usersService.userData.email,
      title: fileDetails.title
    };
    this.usersService.deleteFileFromSubs(data);
  }
  html_creator(fileDetails){
    var sref_name =  "home(logged/fileDetails/{id:" +fileDetails.title+"})"
    return sref_name
  }

  viewFile(fileDetails){
    this.usersService.addRequestedFileDetails(fileDetails);
    console.log(this.usersService.requestedFileDetails);
    console.log(fileDetails);
    console.log(fileDetails.title);
    this.usersService.getFile(fileDetails.title).then(function successCallback(response, status, headers, config) {
      this.jsonToVizualization = response.data;
    //   var decoder = new TextDecoder("utf-8");
    //  // decoder.decode(new Uint8Array(response.data));
    //
    //   console.log(JSON.stringify(decoder.decode(new Uint8Array(response.data))));
    // //console.log(JSON.stringify(this.jsonToVizualization));
    }.bind(this));
    //
    // this.jsonToVizualization = this.usersService.getUrl(`/content/load/${fileDetails.title}`);
    // console.log(this.jsonToVizualization);
    // this.usersService.jsonToVisualisation = this.jsonToVizualization;
    // this.state.go('logged.fileDetails', fileDetails.title);
  }

}



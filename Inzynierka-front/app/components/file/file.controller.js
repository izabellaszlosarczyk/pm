export default class FileController {
  constructor(usersService, $state, $http, tokenService, $rootScope,$scope, $stateParams) {
    this.usersService = usersService;
    this.tokenService = tokenService;
    this.$rootScope = $rootScope;
    this.$scope = $scope;
    this.stateParams = $stateParams;
    this.state = $state;
    this.loading = false;
    this.$http = $http;
    this.fileDetails = [];
    this.content;
    this.comment = "";
    this.flagType = false; // if text - true  <--> image -false

  }
  $onInit() {
    if (this.fileDetails.type != "image" && this.fileDetails.type != "chart"){
      this.flagType = true;
    }

    //obrazek
    var tmp = new Object();
    //this.fileDetails = this.usersService.requestedFileDetails;
    tmp = this.usersService.requestedFileDetails;
    this.fileDetails = tmp;
    console.log(this.fileDetails);

    this.content = this.usersService.getUrl(`/content/load/${this.fileDetails.title}`);
    // this.usersService.getFile(this.fileDetails.title).then(function successCallback(response, status, headers, config) {
    //   this.loading = false;
    //   console.log(response);
    // }.bind(this));

    // this.usersService.getFile(this.usersService.requestedFileName).then(function successCallback(response, status, headers, config) {
    //   //dane
    //   this.usersService.getFilesDetails(this.usersService.requestedFileName).then(function successCallback(response2, status, headers, config) {
    //     this.loading = false;
    //     this.fileDetails= response.data;
    //     //console.log(this.filesDetails);
    //   }.bind(this));}.bind(this));
  }



  addComment(){
    let data ={
      comment: this.comment,
      fileName: this.fileDetails.title
    };
    this.usersService.addCommentToFile(data).then(function successCallback(response, status, headers, config) {

    }.bind(this));
  }
  addScore(score){
    let data ={
      score: score,
      fileName: this.fileDetails.title
    };
    this.usersService.addOpinionToFile(data).then(function successCallback(response, status, headers, config) {

    }.bind(this));
  }
}



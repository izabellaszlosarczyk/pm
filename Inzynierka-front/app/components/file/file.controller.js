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
    this.userFilesSaved = [];
    this.userFilesSubs = [];
    this.flagType = false; // if text - true  <--> image -false
    this.flagIs = false;

  }
  $onInit() {
    if (this.fileDetails.type != "image" && this.fileDetails.type != "chart"){
      this.flagType = true;
    }

    if (this.fileDetails.type != "interactive"){
      console.log("interactive");
    }

    //obrazek
    var tmp = new Object();
    //this.fileDetails = this.usersService.requestedFileDetails;
    tmp = this.usersService.requestedFileDetails;
    this.fileDetails = tmp;
    console.log(this.fileDetails);
    console.log("TERAZ CZESC UZYTKOWNIKA");
    this.userFilesSubs = this.usersService.userData.subscribedFiles;
    this.userFilesSaved = this.usersService.userData.savedFiles;
    console.log(this.userFilesSubs);
    console.log(this.userFilesSaved);
    console.log(this.fileDetails.title);
    if ((this.userFilesSaved.indexOf(this.fileDetails.title) > -1) || ( this.userFilesSubs.indexOf(this.fileDetails.title) > -1)) {
      this.flagIs = true;
      console.log("okej");
    }

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
    console.log("dodaje komcia sdsadsfsdgsrgrg");
    let data ={
      comment: this.comment,
      fileName: this.fileDetails.title
    };
    console.log(data);
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
  subscribe(){
    let addData = {
    email: this.usersService.userData.email,
    title: this.fileDetails.title
  };
    this.usersService.addFileToSubs(addData).then(function successCallback(response, status, headers, config) {
      console.log("Dupa234235243543t542524w");
    }.bind(this));
  }
  
  delSubs(){
    let delData = {
      email: this.usersService.userData.email,
      title: this.fileDetails.title
    };
    this.usersService.deleteFileFromSubs(delData).then(function successCallback(response, status, headers, config) {
      console.log("Dupahgjy7t7ti7i6i24w");
    }.bind(this));
  }
}



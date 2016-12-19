export default class UserDataViewController {

    constructor(usersService, $state, $http, tokenService, $rootScope,$scope, $stateParams) {
        this.usersService = usersService;
        this.state = $state;
        this.$http = $http;
        this.$scope = $scope;
        this.$stateParams = $stateParams;
        this.userData = {
            name: '',
            surname: '',
            email: '',
            files: ''
        };
        this.avatar = '';
        this.requestedFile ='';
        this.newFileType = '';
        this.newFile = '';

        this.newFile2 = '';
        this.$scope.fileNew = '';
        this.$scope.fileNew2 = '';
        this.$scope.file_upload = function(element) {
            this.$scope.$apply(function(scope) {
                //Take the first selected file
                var photofile = element.files[0];
                console.log("dupa111111111111111111111111111111111111111111111111111111");
                console.log(element.files);
                var reader = new FileReader();
                $scope.fileNew = photofile;
                console.log($scope.fileNew);
                reader.onload = function(e) {
                    $scope.prev_img = e.target.result;
                    console.log(reader.result);
                };
                console.log($scope.fileNew.data);
                reader.readAsDataURL($scope.fileNew);
            });
        }.bind(this);
        this.$scope.file_upload2 = function(element) {
            this.$scope.$apply(function(scope) {
                var photofile = element.files[0];
                console.log("dupa222222222222222222222222222222222222222222222222222222222");
                console.log(element.files);
                var reader = new FileReader();
                $scope.fileNew2 = photofile;
                reader.onload = function(e) {
                    $scope.prev_img = e.target.result;
                    console.log(reader.result);
                };
                console.log($scope.fileNew2.data);
                reader.readAsDataURL($scope.fileNew2);
            });
        }.bind(this);
    }

    $onInit() {
        console.log("INIT");
        this.userData = this.usersService.getUserDataValues();
        console.log(this.userData.profilePhoto);
        this.avatar = this.usersService.getUrl(`/content/load/${this.userData.profilePhoto}`);
        console.log(this.userData);
        console.log(this.userData.files);
    }

    editUser(){

    }

    randomFile(){
        console.log("DDDDDDDDDDDDDDDDDDDDDDDDDDUPA");
        this.usersService.getRandomFile().then(function successCallback(response, status, headers, config) {
            console.log(response.data);
            this.requestedFile = response.data;
            this.usersService.addRequestedFileDetails(this.requestedFile);
            this.state.go('logged.fileDetails',this.requestedFile);
        }.bind(this));

    }

    addNewFile(){
        console.log(this.$scope.fileNew);
        console.log(this.$scope.fileNew2);
        //save file to database through backend
        this.usersService.saveNewFileBackend(this.$scope.fileNew,this.$scope.fileNew2, this.newFileType).then(function successCallback(response, status, headers, config) {
            console.log(response.data);
            console.log(status);
            console.log(headers);
        }.bind(this));
        // let fileEntity = {
        //     type : this.newFileType,
        //     title : this.newFileName
        // }
        // this.usersService.addFileEntity(fileEntity).then(function successCallback(response2, status2, headers2, config2) {
        //     console.log(response2);
        //     console.log("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        // }.bind(this));
        // //save details
    }

}

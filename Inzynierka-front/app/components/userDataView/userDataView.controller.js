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
        this.analysesType = '';
        this.newFile = '';

        this.fileName = '';
        this.newFile2 = '';
        this.$scope.fileNew = '';
        this.$scope.fileNew2 = '';

        this.fileDetails = {};
        
        this.barChart = 0;
        this.graph = 0;
        this.straight = 0;
        this.dendo = 0;
        this.radial = 0;

        this.update = 0;

        this.jsonToVizualization = ""; //tutaj siedzi co wizualizujemy


        this.$scope.file_upload = function(element) {
            this.$scope.$apply(function(scope) {
                //Take the first selected file
                var photofile = element.files[0];
                var reader = new FileReader();
                $scope.fileNew = photofile;
                reader.onload = function(e) {
                    $scope.prev_img = e.target.result;
                };
                reader.readAsDataURL($scope.fileNew);
            });
        }.bind(this);
        this.$scope.file_upload2 = function(element) {
            this.$scope.$apply(function(scope) {
                var photofile = element.files[0];
                var reader = new FileReader();
                $scope.fileNew2 = photofile;
                reader.onload = function(e) {
                    $scope.prev_img = e.target.result;
                };
                reader.readAsDataURL($scope.fileNew2);
            });
        }.bind(this);
    }

    $onInit() {
        if (!this.usersService.$storage.logged){
            this.state.go('login');
        }
        this.userData = this.usersService.getUserDataValues();
        this.avatar = this.usersService.getUrl(`/content/load/${this.userData.profilePhoto}`);
    }

    editUser(){

    }

    randomFile(){

        this.usersService.getRandomFile().then(function successCallback(response, status, headers, config) {
            this.requestedFile = response.data;
            this.usersService.addRequestedFileDetails(this.requestedFile);
            this.usersService.analysesType = this.requestedFile.type;
            //this.state.go('logged.fileDetails',this.requestedFile.title);
            this.usersService.getFile(this.requestedFile.title).then(function successCallback(response, status, headers, config) {
                var decoder = new TextDecoder("utf-8");
                this.jsonToVizualization = JSON.parse(decoder.decode(new Uint8Array(response.data)));
                this.usersService.jsonToVisualisation = this.jsonToVizualization;
                this.state.go('logged.fileDetails',this.requestedFile.title);
            }.bind(this));

        }.bind(this));

    }

    addNewFile(){
        //save file to database through backend
        this.usersService.saveNewFileBackend(this.$scope.fileNew,this.$scope.fileNew2, this.newFileType, this.analysesType, this.fileName, this.userData.email).then(function successCallback(response, status, headers, config) {
            this.jsonToVizualization = response.data;
            let fileEntity = {
                type : this.analysesType,
                title : this.fileName
            };
            this.usersService.jsonToVisualisation = this.jsonToVizualization;
            this.usersService.addFileEntity(fileEntity).then(function successCallback(response4, status4, headers4, config4) {
                this.fileDetails = response4.data;
                this.usersService.addRequestedFileDetails(this.fileDetails);
                this.usersService.analysesType = this.analysesType;
                var flagEx = 0;
                console.log(this.usersService.userData.savedFiles);
                console.log(this.usersService.$storage.userData.savedFiles);
                for (var i in  this.usersService.userData.savedFiles) {
                    if ( this.usersService.userData.savedFiles[i] == fileEntity.title) {
                        flagEx = 1;
                    }
                }
                if (flagEx == 0)this.usersService.userData.savedFiles.push(fileEntity.title);

                for (var i in  this.usersService.$storage.userData.savedFiles) {
                    if (this.usersService.$storage.userData.savedFiles[i] == fileEntity.title) {
                        flagEx = 1;
                    }
                }
                if (flagEx == 0)this.usersService.$storage.userData.savedFiles.push(fileEntity.title);

                this.state.go('logged.fileDetails', this.fileDetails.title);
            }.bind(this));

        }.bind(this));

    }

}

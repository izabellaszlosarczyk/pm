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
                console.log(element.files);
                var reader = new FileReader();
                $scope.fileNew = photofile;
                console.log($scope.fileNew);
                reader.onload = function(e) {
                    $scope.prev_img = e.target.result;
                    //console.log(reader.result);
                };
                console.log($scope.fileNew.data);
                reader.readAsDataURL($scope.fileNew);
            });
        }.bind(this);
        this.$scope.file_upload2 = function(element) {
            this.$scope.$apply(function(scope) {
                var photofile = element.files[0];
                console.log(element.files);
                var reader = new FileReader();
                $scope.fileNew2 = photofile;
                reader.onload = function(e) {
                    $scope.prev_img = e.target.result;
                    //console.log(reader.result);
                };
                console.log($scope.fileNew2.data);
                reader.readAsDataURL($scope.fileNew2);
            });
        }.bind(this);
    }

    $onInit() {
        if (!this.usersService.$storage.logged){
            this.state.go('login');
        }
        console.log("INIT");
        console.log(this.usersService.$storage.userData);
        console.log("INIT FINISH");

        this.userData = this.usersService.getUserDataValues();

        console.log(this.userData.profilePhoto);
        this.avatar = this.usersService.getUrl(`/content/load/${this.userData.profilePhoto}`);
        console.log(this.userData);
        console.log(this.userData.files);
    }

    editUser(){

    }

    randomFile(){
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
        console.log("SPRAWDZAM MAIL");
        console.log(this.userData.email);
        this.usersService.saveNewFileBackend(this.$scope.fileNew,this.$scope.fileNew2, this.newFileType, this.analysesType, this.fileName, this.userData.email).then(function successCallback(response, status, headers, config) {
            console.log(response.data);
            console.log("koumnikacja z pythonem - ok");
            this.jsonToVizualization = response.data;
            let fileEntity = {
                type : this.analysesType,
                title : this.fileName
            };
            this.usersService.jsonToVisualisation = this.jsonToVizualization;
            this.usersService.addFileEntity(fileEntity).then(function successCallback(response4, status4, headers4, config4) {
                console.log(response4);
                console.log("zapis klasy pliku - ok");
                this.fileDetails = response4.data;
                this.usersService.addRequestedFileDetails(this.fileDetails);
                this.usersService.analysesType = this.analysesType;
                console.log(this.usersService.requestedFileDetails);
                console.log(this.fileDetails);
                this.state.go('logged.fileDetails', this.fileDetails.title);
            }.bind(this));

        }.bind(this));

    }

}

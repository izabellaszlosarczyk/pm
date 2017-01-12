export default class UserDataEditController {

    constructor(usersService , $scope, $state, $http, tokenService, $rootScope, $stateParams) {
        this.usersService = usersService;
        this.userData = {
            name: "",
            surname: "",
            email: ""
        };
        this.userNewData = {
            name: "",
            surname: "",
            oldemail: "",
            email: "",
            password: ""
        };
        this.$state = $state;
        this.$http = $http;
        this.$stateParams = $stateParams;
        this.tokenService = tokenService;
        this.$rootScope = $rootScope;
        this.newFileName;
        this.loadingFlag = 0;
        this.$scope = $scope;
        this.$scope.newImage = '';
        this.$scope.newFile = '';
        this.$scope.newName ='';
        //this.$scope.photoChanged = 0;
        this.$scope.file_changed = function(element) {
            this.$scope.$apply(function(scope) {
                var photofile = element.files[0];
                $scope.newName = photofile.name;
                $scope.newFile = photofile;
                //this.$scope.photoChanged = 1;
                var reader = new FileReader();
                reader.onload = function(e) {

                    // handle onload
                    var image = new Object();
                    image.src = reader.result;
                    $scope.newImage = image.src;
                };
                reader.readAsDataURL(photofile);
            });
        }.bind(this);
    }

    $onInit() {
        this.userData = this.usersService.getUserDataValues();
    }

    editUser(){
        this.userNewData.oldemail = this.userData.email;

        this.usersService.edit(JSON.stringify(this.userNewData));
        if (this.userNewData.name.length > 1){
            this.usersService.userData.firstName = this.userNewData.name;
            this.usersService.$storage.userData.firstName = this.userNewData.name;
            this.userData.name = this.userNewData.name;
        }
        if (this.userNewData.email.length > 1){
            this.usersService.userData.email = this.userNewData.email;
            this.usersService.$storage.userData.email = this.userNewData.email;
            this.userData.email = this.userNewData.email;
        }
        if (this.userNewData.surname.length > 1){
            this.usersService.userData.lastName = this.userNewData.surname;
            this.usersService.$storage.userData.lastName = this.userNewData.surname;
            this.userData.surname = this.userNewData.surname;
        }
    }



    getImage(){

            this.usersService.saveNewImage(this.$scope.newFile,this.$scope.newName).then(function successCallback(response, status, headers, config) {
                let userData = {
                    email : this.userData.email,
                    title : this.$scope.newName
                };
                this.usersService.editPhoto(userData).then(function successCallback(response2, status2, headers2, config2) {
                    this.usersService.userData.profilePhoto = this.$scope.newName;
                    this.$state.go('logged');
                }.bind(this));
            }.bind(this));
    }
}

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
        this.newFileName = '';
        this.newFileType = '';
        this.newFile = '';
        this.$scope.fileNew = '';
        this.$scope.file_upload = function(element) {
            console.log("LADDDDDDDDDDDDDDDDDDDDDDDDDDDDDUJE");
            this.$scope.$apply(function(scope) {
                var fd = new FormData();
                //Take the first selected file

                var photofile = element.files[0];
                var reader = new FileReader();
                //this.$scope.fileNew = photofile;
                //this.newFile = photofile;
                console.log("DUPPPPPPPPPPPPPPPPPPA");
               //console.log(this.newFile);
                $scope.fileNew = photofile;
                console.log($scope.fileNew);
                reader.onload = function(e) {
                    var file = new Object();
                    file.src = reader.result;
                };
                reader.readAsDataURL(photofile);
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
        this.usersService.getRandomFileName().then(function successCallback(response, status, headers, config) {
            this.requestedFile = response.data;
            this.usersService.addRequestedFileDetails(this.requestedFile);
            this.state.go('logged.fileDetails',this.requestedFile);
        }.bind(this));

    }

    addNewFile(){
        console.log(this.newFileName);
        console.log(this.newFileType);
        console.log("TERAZ PLIK");
        console.log(this.$scope.fileNew);
        //save file
        this.usersService.saveNewImage(this.$scope.fileNew, this.newFileName).then(function successCallback(response, status, headers, config) {
            console.log(response.data);
            console.log("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
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

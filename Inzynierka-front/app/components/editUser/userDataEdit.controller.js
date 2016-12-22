export default class UserDataEditController {

    constructor(usersService , $scope) {
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
                console.log($scope.newName);
                //this.$scope.photoChanged = 1;
                var reader = new FileReader();
                reader.onload = function(e) {

                    // handle onload
                    var image = new Object();
                    console.log("IMAGE");
                    image.src = reader.result;
                    $scope.newImage = image.src;
                    console.log("DUPPPPPPPPPPPPPPPPPPA");;
                };
                reader.readAsDataURL(photofile);
            });
        }.bind(this);
    }

    $onInit() {
        console.log("EDYCJA");
        this.userData = this.usersService.getUserDataValues();
    }

    editUser(){
        this.userNewData.oldemail = this.userData.email;
        this.usersService.edit(JSON.stringify(this.userNewData));
    }



    getImage(){
        // if ( this.$scope.photoChanged == 1){
            console.log(this.$scope.newFile);
            console.log("UPLOADOWANIE UPLOADOWANIE");

            this.usersService.saveNewImage(this.$scope.newFile,this.$scope.newName).then(function successCallback(response, status, headers, config) {
                console.log(response.data);
                console.log("obrazek");
                let userData = {
                    email : this.userData.email,
                    title : this.$scope.newName
                };
                this.usersService.editPhoto(userData).then(function successCallback(response2, status2, headers2, config2) {
                    console.log(response2.data);
                    console.log("Uzytkownik update");
                }.bind(this));

            }.bind(this));

        //}

        // var files = element.files;
        // var l = files.length;
        // var namesArr = [];
        //
        // for (var i = 0; i < l; i++) {
        //     namesArr.push(files[i].name);
        // }
        // console.log(namesArr);
    }
}

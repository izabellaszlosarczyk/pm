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
        this.$scope.file_changed = function(element) {
            this.$scope.$apply(function(scope) {
                var photofile = element.files[0];
                $scope.newFile = photofile;
                var reader = new FileReader();
                reader.onload = function(e) {

                    // handle onload
                    var image = new Object();
                    image.src = reader.result;
                    $scope.newImage = image.src;
                    console.log("DUPPPPPPPPPPPPPPPPPPA");
                    console.log($scope.newImage);
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
        console.log(this.$scope.newFile);
        console.log("UPLOADOWANIE UPLOADOWANIE");
        
        this.usersService.saveNewImage(userFile,this.newFileName).then(function successCallback(response, status, headers, config) {
            console.log(response.data);
            console.log("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
            let fileEntity = {
                type : "avatar",
                name : this.newFileName
            }
        }.bind(this));
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

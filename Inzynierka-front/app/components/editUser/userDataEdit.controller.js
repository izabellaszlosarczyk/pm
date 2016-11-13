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
        this.newImage = '';
        this.loadingFlag = 0;
        this.$scope = $scope;
        this.$scope.file_changed = function(element) {
            $scope.$apply(function(scope) {
                var photofile = element.files[0];
                var reader = new FileReader();
                reader.onload = function(e) {
                    // handle onload
                    var newImage = new Object();
                    newImage.src = reader.result;
                    this.newImage = newImage;
                    console.log("DUPPPPPPPPPPPPPPPPPPA");
                    console.log(this.newImage);
                };
                reader.readAsDataURL(photofile);
            });
        };
    }

    $onInit() {
        console.log("EDYCJA");
        this.userData = this.usersService.getUserDataValues();
    }

    editUser(){
        this.userNewData.oldemail = this.userData.email;
        this.usersService.edit(JSON.stringify(this.userNewData));
    }



    getImage(element){
        console.log(element);
        console.log("UPLOADOWANIE UPLOADOWANIE");
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

export default class UserDataEditController {

    constructor(usersService) {
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
    }

    $onInit() {
        this.userData = this.usersService.getUserDataValues();
        //this.userNewData = this.userData;
        console.log(this.userData);
    }

    editUser(){
        console.log("_____________________________________");
        console.log(this.userNewData);
        console.log("DOOOOOOOOOOOOOOOOOOOOAAAAOOA");
        this.userNewData.oldemail = this.userData.email;
        this.usersService.edit(JSON.stringify(this.userNewData));
    }
}

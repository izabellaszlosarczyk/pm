export default class UserDataEditController {

    constructor(usersService) {
        this.usersService = usersService;
        this.userData = {
            name: '',
            surname: '',
            email: ''
        };
        this.userNewData = {
            name: '',
            surname: '',
            email: '',
            password: ''
        };
    }

    $onInit() {
        this.userData = this.usersService.getUserDataValues();
        //this.userNewData = this.userData;
        //console.log(this.userNewData);
    }

    editUser(){
        console.log("_____________________________________");
        console.log(this.userNewData);
        this.usersService.edit(this.userNewData);
    }
}

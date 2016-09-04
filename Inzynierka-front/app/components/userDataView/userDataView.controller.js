export default class UserDataViewController {

    constructor(usersService) {
        this.usersService = usersService;
        this.userData = {
            name: '',
            surname: '',
            email: ''
        };
        this.avatar = '';
    }

    $onInit() {
        this.userData = this.usersService.getUserDataValues();
        this.avatar = this.usersService.getUrl(`/content/load/${this.userData.profilePhoto}`);
    }

    editUser(){

    }
}

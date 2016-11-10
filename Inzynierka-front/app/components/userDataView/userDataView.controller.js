export default class UserDataViewController {

    constructor(usersService, $state, $http, tokenService, $rootScope,$scope, $stateParams) {
        this.usersService = usersService;
        this.$state = $state;
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
    }

    $onInit() {
        this.userData = this.usersService.getUserDataValues();
        console.log(this.userData.profilePhoto);
        this.avatar = this.usersService.getUrl(`/content/load/${this.userData.profilePhoto}`);
        console.log(this.userData);
        console.log(this.userData.files);
    }

    editUser(){

    }
}

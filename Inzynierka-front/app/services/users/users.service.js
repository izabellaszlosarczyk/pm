class UserService {
    /*@ngInject*/

    constructor($http, pmServerUrl, AuthorizationToken, $base64, $sessionStorage) {
        this.$http = $http;
        this.baseUrl = pmServerUrl;
        this.base64 = $base64;
        this.$storage = $sessionStorage;
        this.userSessionData = {
            'userId' : '',
            'token' : AuthorizationToken.NO_AUTH
        };
        this.userData = {};
    }

    getUrl(url) {
        return `${this.baseUrl}/${url}`;
    }

    login(loginData) {
        const basicAuth = 'Basic ' + this.base64.encode(loginData.email + ':' + loginData.password);
        return this.$http({
            url: this.getUrl('user/info'),
            method: "GET",
            headers: {
                'Authorization': basicAuth
            }
        });
    }

    getUserData(loginData){
        return this.$http({
            url: this.getUrl('edit/getAfterLogin'),
            method: "POST",
            data: loginData
        });
    }

    getUserImage(fileName){
        return this.$http({
            url: this.getUrl('content/' + fileName),
            method: "GET"
        });
    }

    saveNewImage(userFile){
        var formdata = new FormData();
        formdata.append( 'name', userFile.name );
        formdata.append( 'file', userFile.file );
        return this.$http({
            url: this.getUrl('content/save/upload/image/'),
            method: "POST",
            data: formdata,
            cache: false,
            contentType: false,
            processData: false
        });

    }
    logout() {
        return this.$http({
            url: this.getUrl('logout'),
            method: "POST"
        });
    }

    register(userData) {
        return this.$http({
            url: this.getUrl('users/new'),
            method: "POST",
            data: userData
        });
    }

    edit(userData) {
        return this.$http({
            url: this.getUrl(`edit/saveEdited`),
            method: "POST",
            data: userData
        });
    }

    getUser(userId) {
        return this.$http({
            url: this.getUrl(`users/${userId}`),
            method: "GET"
        });
    }
    deleteUser(userId) {
        return this.$http({
            url: this.getUrl(`users/${userId}/delete`),
            method: "DELETE"
        });
    }

    setUserSessionData(userSessionData){
        this.userSessionData = userSessionData;
        this.$storage.userSessionData = this.userSessionData;
    }

    getUserSessionData(){
        if (!this.userSessionData && this.$storage.userSessionData) {
            this.userSessionData = this.$storage.userSessionData;
        }
        return this.userSessionData;
    }
    removeUserSessionData() {
        delete this.userSessionData;
        delete this.$storage.userSessionData;
    }

    getUserDataValues(){
        if (this.$storage.userData) {
            this.userData = this.$storage.userData;
        }
        return this.userData;
    }

    setUserDataValues(userData){
        this.userData = userData;
        this.$storage.userData = this.userData;
    }

    removeUserDataValues(){
        delete this.userData;
        delete this.$storage.userData;
    }
}

export default UserService;

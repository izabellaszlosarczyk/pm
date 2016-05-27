class UserService {
    /*@ngInject*/
    constructor($http, pmServerUrl, AuthorizationToken) {
        this.$http = $http;
        this.baseUrl = pmServerUrl;
        this.userSessionData = {
            'userId' : '',
            'token' : AuthorizationToken.NO_AUTH
        };
    }

    getUrl(url) {
        return `${this.baseUrl}/${url}`;
    }

    login(loginData) {
        return this.$http({
            url: this.getUrl('login'),
            method: "POST",
            data: loginData
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

    edit(userId, userData) {
        return this.$http({
            url: this.getUrl(`users/${userId}/edit`),
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
    }
    getUserSessionData(){
        return this.userSessionData;
    }
}

export default UserService;

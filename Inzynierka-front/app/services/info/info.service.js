class InfoService {
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
    }

    getUrl(url) {
        return `${this.baseUrl}/${url}`;
    }

    getFileDetails(fileName){
        console.log("get po details");
        return this.$http({
            url: this.getUrl(`file/loadDetails/${fileName}`),
            method: "GET"
        });

    }

    getUserFiles(userId) {
        return this.$http({
            url: this.getUrl(`files/users/${userId}`),
            method: "GET"
        });
    }
    getFile(fileName) {
        return this.$http({
            url: this.getUrl(`file/load/${fileName}`),
            method: "GET"
        });
    }
    addOpinionToFile(fileName,grade){
        let request = {
            file: fileName,
            grade: grade
        };
        return this.$http({
            url: this.getUrl(`file/load/${fileName}`),
            method: "POST",
            data: request
        });
    }
    addCommentToFile(fileName,comment){
        let request = {
            file: fileName,
            comment: comment
        };
        return this.$http({
            url: this.getUrl(`file/load/${fileName}`),
            method: "POST",
            data: request
        });
    }
    // getUserOffers(userId) {
    //     return this.$http({
    //         url: this.getUrl(`users/${userId}/offers`),
    //         method: "GET"
    //     });
    // }
    // createUserOffer(userId, offerData) {
    //     return this.$http({
    //         url: this.getUrl(`users/${userId}/offers/new`),
    //         method: "POST",
    //         data: offerData
    //     });
    // }
    // updateUserOffer(userId, offerId, offerData) {
    //     return this.$http({
    //         url: this.getUrl(`users/${userId}/offers/${offerId}/edit`),
    //         method: "PUT",
    //         data: offerData
    //     });
    // }
    // getUserOffer(userId, offerId) {
    //     return this.$http({
    //         url: this.getUrl(`users/${userId}/offers/${offerId}`),
    //         method: "GET"
    //     });
    // }
    // deleteUserOffer(userId, offerId) {
    //     return this.$http({
    //         url: this.getUrl(`users/${userId}/offers/${offerId}/delete`),
    //         method: "DELETE"
    //     });
    // }
    // deleteOffer(offerId) {
    //     return this.$http({
    //         url: this.getUrl(`offers/${offerId}/delete`),
    //         method: "DELETE"
    //     });
    // }

}

export default InfoService;

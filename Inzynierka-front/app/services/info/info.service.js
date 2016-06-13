class InfoService {
    /*@ngInject*/
    constructor($http, pmServerUrl) {
        this.$http = $http;
        this.baseUrl = pmServerUrl;

    }

    getUrl(url) {
        return `${this.baseUrl}/${url}`;
    }
    setUserSessionData(userSessionData){
        this.userSessionData = userSessionData;
    }
    getUserSessionData(){
        return this.userSessionData;
    }

    getOffer(offerId) {
        return this.$http({
            url: this.getUrl(`offers/${userId}`),
            method: "GET"
        });
    }
    getOffers() {
        return this.$http({
            url: this.getUrl(`offers`),
            method: "GET"
        });
    }
    getUserOffers(userId) {
        return this.$http({
            url: this.getUrl(`users/${userId}/offers`),
            method: "GET"
        });
    }
    createUserOffer(userId, offerData) {
        return this.$http({
            url: this.getUrl(`users/${userId}/offers/new`),
            method: "POST",
            data: offerData
        });
    }
    updateUserOffer(userId, offerId, offerData) {
        return this.$http({
            url: this.getUrl(`users/${userId}/offers/${offerId}/edit`),
            method: "PUT",
            data: offerData
        });
    }
    getUserOffer(userId, offerId) {
        return this.$http({
            url: this.getUrl(`users/${userId}/offers/${offerId}`),
            method: "GET"
        });
    }
    deleteUserOffer(userId, offerId) {
        return this.$http({
            url: this.getUrl(`users/${userId}/offers/${offerId}/delete`),
            method: "DELETE"
        });
    }
    deleteOffer(offerId) {
        return this.$http({
            url: this.getUrl(`offers/${offerId}/delete`),
            method: "DELETE"
        });
    }

}

export default InfoService;

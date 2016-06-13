/**
 * Created by arkadiusz on 15.04.16.
 */
export default class OffersViewController {
    constructor(infoService, usersService) {
        this.infoService = infoService;
        this.usersService = usersService;
        this.$state = $state;
        this.infoList = this.getInfoList();
    }

    getInfoList() {
        var result = [];
        this.infoService.getOffers()
            .then(successResponse => {
                result = successResponse.data;
            });
        return result;
    }

}

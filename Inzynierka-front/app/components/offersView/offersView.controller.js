/**
 * Created by arkadiusz on 15.04.16.
 */
export default class OffersViewController {
    constructor(offersService, usersService) {
        this.offersService = offersService;
        this.usersService = usersService;
        this.$state = $state;
        this.offersList = this.getOffersList();
    }

    getOffersList() {
        var result = [];
        this.offersService.getOffers()
            .then(successResponse => {
                result = successResponse.data;
            });
        return result;
    }

}

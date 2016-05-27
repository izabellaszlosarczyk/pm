import offersService from './offers.service';

export default app => {
  app.service('offersService', offersService);
}

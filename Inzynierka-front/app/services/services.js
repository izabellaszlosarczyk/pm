import usersService from './users/users';
import offersService from './offers/offers';

export default app => {
  INCLUDE_ALL_MODULES([usersService, offersService], app);
}

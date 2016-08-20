import usersService from './users/users';
import infoService from './info/info';
import tokenService from './token/token';

export default app => {
  INCLUDE_ALL_MODULES([usersService, infoService, tokenService], app);
}

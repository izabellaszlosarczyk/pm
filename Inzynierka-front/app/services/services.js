import usersService from './users/users';
import infoService from './info/info';

export default app => {
  INCLUDE_ALL_MODULES([usersService, infoService], app);
}

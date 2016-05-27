import usersService from './users.service';

export default app => {
  app.service('usersService', usersService);
}

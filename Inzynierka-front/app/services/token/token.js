import tokenService from './token.service.js';

export default app => {
  app.service('tokenService', tokenService);
}

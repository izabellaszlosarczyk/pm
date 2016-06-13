import infoService from './info.service.js';

export default app => {
  app.service('infoService', infoService);
}

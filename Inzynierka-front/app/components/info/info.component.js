import controller from './info.controller.js';

export default {
  restrict: 'E',
  scope: {},
  template: 'templates/app/info.html',
  controller,
  controllerAs: 'infoCtrl',
  bindToController: true
};

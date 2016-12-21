import controller from './logout.controller.js';

export default {
  restrict: 'E',
  scope: {},
  template: '<h1 style="margin-left: auto;margin-right: auto;text-align: center; display: block;padding-top: 50px; color: #00a379">Dziękuję!</h1>',
  controller,
  controllerAs: 'logoutCtrl',
  bindToController: true
};

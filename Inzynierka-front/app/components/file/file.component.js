import controller from './file.controller.js';

export default {
  restrict: 'E',
  scope: {},
  templateUrl: 'templates/login.html',
  controller,
  controllerAs: 'fileCtrl',
  bindToController: true
};

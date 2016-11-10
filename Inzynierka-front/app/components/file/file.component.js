import controller from './file.controller.js';

export default {
  restrict: 'E',
  scope: {},
  templateUrl: 'templates/app/file.html',
  controller,
  controllerAs: 'fileCtrl',
  bindToController: true
};

import controller from './patterns.controller.js';

export default {
  restrict: 'E',
  scope: {},
  templateUrl: 'templates/app/patterns.html',
  controller,
  controllerAs: 'patternsCtrl',
  bindToController: true
};

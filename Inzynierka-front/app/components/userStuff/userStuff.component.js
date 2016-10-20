import controller from './userStuff.controller.js';

export default {
  restrict: 'E',
  scope: {},
  templateUrl: 'templates/app/yourStuff.html',
  controller,
  controllerAs: 'userStuffCtrl',
  bindToController: true
};

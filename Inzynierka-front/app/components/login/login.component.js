import controller from './login.controller';

export default {
  restrict: 'E',
  scope: {},
  templateUrl: 'templates/login.html',
  controller,
  controllerAs: 'loginCtrl',
  bindToController: true
};

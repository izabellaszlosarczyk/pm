import controller from './news.controller.js';

export default {
  restrict: 'E',
  scope: {},
  templateUrl: 'templates/app/news.html',
  controller,
  controllerAs: 'newsCtrl',
  bindToController: true
};

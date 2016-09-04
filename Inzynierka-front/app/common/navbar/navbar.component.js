import template from './navbar.html';
import controller from './navbar.controller';

let navbarComponent = function () {
  return {
    restrict: 'AEC',
    scope: {},
    template,
    controller,
    controllerAs: 'navBarCtrl',
    bindToController: true
  };
};

export default navbarComponent;

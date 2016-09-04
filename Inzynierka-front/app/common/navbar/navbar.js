import navbarComponent from './navbar.component';

export default app => {
  app.directive('myNavbarHeader', navbarComponent);

  if (ENVIRONMENT === 'test') {
    require('./navbar.test.js');
  }
}


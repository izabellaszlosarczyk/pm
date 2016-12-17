export default app => {
  app.config([configFn]);
  app.constant('pmServerUrl', 'http://localhost:8080');
  app.constant('AuthorizationToken', {
    'ADMIN' : 'ADMIN',
    'USER' : 'USER',
    'NO_AUTH' : 'NO_AUTH'});

  function configFn() {

  }
}

import homeComponent from './home/home';
import loginComponent from './login/login';
import registerComponent from './register/register';
import offersViewComponent from './offersView/offersView';
import userDataViewComponent from './userDataView/userDataView';

export default app => {
  INCLUDE_ALL_MODULES([homeComponent,
    loginComponent,
    registerComponent,
    offersViewComponent,
    userDataViewComponent], app);
}

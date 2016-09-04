import homeComponent from './home/home';
import loginComponent from './login/login';
import registerComponent from './register/register';
import offersViewComponent from './offersView/offersView';
import userDataViewComponent from './userDataView/userDataView';
import userDataEditComponent from './editUser/userDataEdit';


export default app => {
  INCLUDE_ALL_MODULES([homeComponent,
    loginComponent,
    registerComponent,
    offersViewComponent,
    userDataEditComponent,
    userDataViewComponent], app);
}

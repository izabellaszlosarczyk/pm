import homeComponent from './home/home';
import loginComponent from './login/login';
import registerComponent from './register/register';
import offersViewComponent from './offersView/offersView';
import userDataViewComponent from './userDataView/userDataView';
import userDataEditComponent from './editUser/userDataEdit';
import imagesComponent from './images/images';
import othersComponent from './others/others';
import chartsComponent from './charts/charts';
import cvsComponent from './cvs/cvs';
import patternsComponent from './patterns/patterns';
import newsComponent from './news/news';
import userStuffComponent from './userStuff/userStuff'
import subsComponent from './subs/subs'
import fileComponent from './file/file';
import logoutComponent from './logout/logout';
import infoComponent from './info/info';

export default app => {
  INCLUDE_ALL_MODULES([homeComponent,
    loginComponent,
    registerComponent,
    offersViewComponent,
    userDataEditComponent,
    imagesComponent,
    userDataViewComponent,
    chartsComponent,
    cvsComponent,
    othersComponent,
    patternsComponent,
    fileComponent,
    userStuffComponent,
    newsComponent,
    subsComponent,
    infoComponent,
    logoutComponent  
  ], app);
}

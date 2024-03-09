import HomePage from '../Pages/HomePage';
import Logout from '../Logout/Logout';
import LoginPage from '../Pages/LoginPage';
import RegisterPage from '../Pages/RegisterPage';
import UserDataPage from '../Pages/UserDataPage';
import modifyUserDataPage from '../Pages/ModifyUserDataPage';

const routes = {
  '/': HomePage,
  '/login': LoginPage,
  '/register': RegisterPage,
  '/users/userData': UserDataPage,
  '/users/changeData': modifyUserDataPage,
  '/logout': Logout
};

export default routes;

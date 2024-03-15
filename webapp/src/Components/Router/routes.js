import HomePage from '../Pages/HomePage';
import Logout from '../Logout/Logout';
import LoginPage from '../Pages/LoginPage';
import RegisterPage from '../Pages/RegisterPage';
import UserDataPage from '../Pages/UserDataPage';
import modifyUserDataPage from '../Pages/ModifyUserDataPage';
import AddContactPage from '../Pages/AddContact';
import UsersPage from '../Pages/Users';

const routes = {
  '/': HomePage,
  '/login': LoginPage,
  '/register': RegisterPage,
  '/seeUsers' : UsersPage,
  '/users/userData': UserDataPage,
  '/users/changeData': modifyUserDataPage,
  '/logout': Logout,
  '/addcontact': AddContactPage,
};

export default routes;

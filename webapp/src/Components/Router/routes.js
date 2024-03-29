import HomePage from '../Pages/HomePage';
import Logout from '../Logout/Logout';
import LoginPage from '../Pages/LoginPage';
import RegisterPage from '../Pages/RegisterPage';
import UserDataPage from '../Pages/UserDataPage';
import modifyUserDataPage from '../Pages/ModifyUserDataPage';
import AddContactPage from '../Pages/AddContact';
import UsersPage from '../Pages/Users';
import AddEnterprisePage from '../Pages/AddEnterprise';
import EntreprisesPage from '../Pages/AllEntreprises';

const routes = {
  '/': HomePage,
  '/login': LoginPage,
  '/register': RegisterPage,
  '/seeUsers' : UsersPage,
  '/users/userData': UserDataPage,
  '/users/changeData': modifyUserDataPage,
  '/logout': Logout,
  '/addContact': AddContactPage,
  '/addEnterprise': AddEnterprisePage,
  '/allentreprises': EntreprisesPage,
};

export default routes;

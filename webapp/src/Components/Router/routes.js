import HomePage from '../Pages/HomePage';
import Logout from '../Logout/Logout';
import LoginPage from '../Pages/LoginPage';
import RegisterPage from '../Pages/RegisterPage';
import AddContactPage from '../Pages/AddContact';

const routes = {
  '/': HomePage,
  '/login': LoginPage,
  '/register': RegisterPage,
  '/logout': Logout,
  '/addcontact': AddContactPage,
};

export default routes;

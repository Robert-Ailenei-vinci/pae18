import HomePage from '../Pages/HomePage';
import Logout from '../Logout/Logout';
import LoginPage from '../Pages/LoginPage';
import RegisterPage from '../Pages/RegisterPage';
import UserDataPage from '../Pages/UserDataPage';
import modifyUserDataPage from '../Pages/ModifyUserDataPage';
import AddContactPage from '../Pages/AddContact';
import UsersPage from '../Pages/UsersPage';
import AddEnterprisePage from '../Pages/AddEnterprise';
import EntreprisesListPage from '../Pages/EntreprisesListPage';
import EntrepriseDetailsPage from '../Pages/EntrepriseDetailsPage';
import EtudiantDetailPage from '../Pages/EtudiantDetailPage';
import CreateStagePage from "../Pages/CreateStagePage";
import AddSupervisorPage from "../Pages/AddSupervisorPage";

const routes = {
  '/': HomePage,
  '/login': LoginPage,
  '/register': RegisterPage,
  '/seeUsers': UsersPage,
  '/users/userData': UserDataPage,
  '/users/changeData': modifyUserDataPage,
  '/logout': Logout,
  '/addContact': AddContactPage,
  '/addEnterprise': AddEnterprisePage,
  '/seeEntreprises': EntreprisesListPage,
  '/detailsEntreprise/:id': EntrepriseDetailsPage,
  '/detailsEtudiant/:id': EtudiantDetailPage,
  '/accepte-stage/:id': CreateStagePage,
  '/addSupervisor': AddSupervisorPage,

};

export default routes;

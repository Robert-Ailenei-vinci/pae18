import { getAuthenticatedUser, isAuthenticated } from '../../utils/auths';

const HomePage = () => {
  const authenticatedUser = getAuthenticatedUser();
  const main = document.querySelector('main');
  const anonymousUser = `<h3>Bienvenue sur la page d'accueil, Veuillez vous connecter!</h3>`;
  const connectedUser = `<h3>Bienvenue sur la page d'accueil, ${authenticatedUser?.firstname} ${authenticatedUser?.lastname} !</h3>`;
  main.innerHTML = isAuthenticated() ? connectedUser : anonymousUser;
};

export default HomePage;

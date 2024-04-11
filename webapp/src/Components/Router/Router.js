import {removePathPrefix, usePathPrefix} from '../../utils/path-prefix';
import {getAuthenticatedUser, setAuthenticatedUser} from '../../utils/auths';
import routes from './routes';

const Router = () => {
  onFrontendLoad();
  onNavBarClick();
  onHistoryChange();
};

function onNavBarClick() {
  const navbarWrapper = document.querySelector('#navbarWrapper');

  navbarWrapper.addEventListener('click', (e) => {
    e.preventDefault();
    const navBarItemClicked = e.target;
    const uri = navBarItemClicked?.dataset?.uri;
    if (uri) {
      const componentToRender = routes[uri];
      if (!componentToRender) {
        throw Error(
            `The ${uri} ressource does not exist.`);
      }

      componentToRender();
      window.history.pushState({}, '', usePathPrefix(uri));
    }
  });
}

function onHistoryChange() {
  window.addEventListener('popstate', () => {
    const uri = removePathPrefix(window.location.pathname);
    const componentToRender = routes[uri];
    componentToRender();
  });
}

function onFrontendLoad() {
  console.log("onFrontendLoad")
  window.addEventListener('load', async () => {
    const uri = removePathPrefix(window.location.pathname);
    const componentToRender = routes[uri];
    if (!componentToRender) {
      throw Error(`The ${uri} ressource does not exist.`);
    }

    const user = getAuthenticatedUser();

    if (user) {

      console.log("Refresh");
      const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `${user.token}`,
        },
      };
      try {

        const response = await fetch('http://localhost:3000/auths/user',
            options);

        if (!response.ok) {
          alert('Failed to fetch user');
        }
        const user = await response.json();
        setAuthenticatedUser(user);
      } catch (error) {
        console.error('Failed to fetch user:', error);
      }
    }

    componentToRender();
  });
}

export default Router;

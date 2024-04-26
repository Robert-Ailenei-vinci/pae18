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
        const uriSegments = uri.split('/'); // Sépare l'URI en segments
        const lastSegment = uriSegments.pop(); // Retire le dernier segment

        // Vérifie si le dernier segment est un nombre
        if (!isNaN(lastSegment)) {
            // Reconstruct l'URI sans le dernier segment s'il est un nombre
            const uriWithoutLastSegment = uriSegments.join('/');
            const uriFinal = uriWithoutLastSegment+'/:id';
            const componentToRender = routes[uriFinal];
            if (componentToRender) {
                componentToRender();
            } else {
                // Gérer le cas où aucun composant correspondant n'est trouvé
                console.error("Aucun composant correspondant trouvé pour l'URI :", uriWithoutLastSegment);
            }
        } else {
            // Si le dernier segment n'est pas un nombre, reconstruire l'URI avec le dernier segment
            const uriWithLastSegment = uriSegments.join('/') + '/' + lastSegment;
            const componentToRender = routes[uriWithLastSegment];
            if (componentToRender) {
                componentToRender();
            } else {
                // Gérer le cas où aucun composant correspondant n'est trouvé
                console.error("Aucun composant correspondant trouvé pour l'URI :", uriWithLastSegment);
            }
        }
    });
}


function matchRoute(uri, route) {
    const uriParts = uri.split('/');
    const routeParts = route.split('/');
    if (uriParts.length !== routeParts.length) {
        return false;
    }
    for (let i = 0; i < routeParts.length; i++) {
        if (routeParts[i].startsWith(':')) {
            continue;
        }
        if (routeParts[i] !== uriParts[i]) {
            return false;
        }
    }
    return true;
}

function onFrontendLoad() {
    console.log("onFrontendLoad")
    window.addEventListener('load', async () => {
        const uri = removePathPrefix(window.location.pathname);

        let componentToRender;
        for (const route in routes) {
            if (matchRoute(uri, route)) {
                componentToRender = routes[route];
                break;
            }
        }

        if (!componentToRender) {
            throw Error(`The ${uri} resource does not exist.`);
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

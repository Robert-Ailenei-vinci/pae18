// eslint-disable-next-line no-unused-vars
import { Navbar as BootstrapNavbar } from 'bootstrap';
import { getAuthenticatedUser, isAuthenticated } from '../../utils/auths';

const SITE_NAME = 'StaGo';

const Navbar = () => {
  renderNavbar();
};

function renderNavbar() {
  const authenticatedUser = getAuthenticatedUser();

  const anonymousUserNavbar = `
<nav class="navbar navbar-expand-lg navbar-light bg-info">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">${SITE_NAME}</a>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
               
            <li id="loginItem" class="nav-item">
              <a class="nav-link" href="#" data-uri="/login">Login</a>
            </li>
            <li id="registerItem" class="nav-item">
              <a class="nav-link" href="#" data-uri="/register">Register</a>
            </li>            
          </ul>
        </div>
      </div>
    </nav>
`;

  const authenticatedAdminOrProfNavbar = `
  <nav class="navbar navbar-expand-lg navbar-light bg-info">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">StaGo</a>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                     
          <li class="nav-item">
            <a class="nav-link" href="#" data-uri="/logout">Logout</a>
          </li> 
          <li class="nav-item">
            <a class="nav-link" href="#" data-uri="/users/userData">Voir données personelles</a>
          </li>   
          <li class="nav-item">
            <a class="nav-link disabled" href="#">${authenticatedUser?.email}</a>
          </li>   
          <li class="nav-item">
            <a class="nav-link" href="#" data-uri="/seeUsers">Voir Utilisateurs</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#" data-uri="/seeEntreprises">Voir les entreprises</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>`

  const authenticatedStudentNavbar = `
  <nav class="navbar navbar-expand-lg navbar-light bg-info">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">StaGo</a>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    
          <li class="nav-item">
            <a class="nav-link" href="#" data-uri="/logout">Logout</a>
          </li> 
          <li class="nav-item">
            <a class="nav-link" href="#" data-uri="/users/userData">Voir données personelles</a>
          </li>   
          <li class="nav-item">
            <a class="nav-link disabled" href="#">${authenticatedUser?.email}</a>
          </li>  
        </ul>
      </div>
    </div>
  </nav>`
  const navbar = document.querySelector('#navbarWrapper');

  if (!isAuthenticated()){
    navbar.innerHTML = anonymousUserNavbar;
  }
  else {
    if (authenticatedUser.role === 'administratif' || authenticatedUser.role === 'professeur'){
      navbar.innerHTML = authenticatedAdminOrProfNavbar;
    }
    else {
      navbar.innerHTML = authenticatedStudentNavbar;
    }
  }
}

export default Navbar;

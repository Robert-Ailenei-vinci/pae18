/* eslint-disable spaced-comment */
/* eslint-disable no-unused-vars */
import { getRememberMe, setAuthenticatedUser, setRememberMe , getAuthenticatedUser} from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';

const UserDataPage = () => {
  clearPage();
  renderPageTitle('Données Personelles');
  renderPersonnalInfoPage();
};

async function renderPersonnalInfoPage() {
    const main = document.querySelector('main');
    const ul = document.createElement('ul');
    ul.className = 'p-5';
    
    const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      };

    // Fetch user data from the server
    const response = await fetch(`http://localhost:3000/users/userData`, options);
    const user = getAuthenticatedUser();
    console.log(user)
    if (!response.ok) {
        if (response.status === 401) {
          // Display a popup message for incorrect username or password
          alert("Username or password is incorrect. Please try again.");
      } else {
          // For other errors, handle them accordingly
          console.error("An error occurred:", response.statusText);
      }
          return;
      }
    const items = [
      { label: 'Nom de famille: ', value: user.lastname },
      { label: 'Prénom: ', value: user.firstname },
      { label: 'Email: ', value: user.email },
      { label: 'Numéro de Téléphone: ', value: user.phone },
      { label: 'Date d\'enregistrement: ', value: user.registrationDate },
      { label: 'Année académique: ', value: user.schoolYear },
      { label: 'Role: ', value: user.role}
    ];
  
    items.forEach(item => {
      const li = document.createElement('li');
      li.textContent = item.label + item.value;
      ul.appendChild(li);
    });

    const submit = document.createElement('input');
    submit.value = 'Changer mes données personelles';
    submit.type = 'button';
    submit.className = 'btn btn-info';
    submit.addEventListener('click', () => {
      Navigate('/users/changeData');
  });

    //ajouter tableaux contacts et stages
  
    main.appendChild(ul);
    main.appendChild(submit);
    //append les tableaux



  }
  Navbar();

  Navigate('/');


export default UserDataPage;

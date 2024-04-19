/* eslint-disable camelcase */
import { getAuthenticatedUser, setAuthenticatedUser } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';
import baseURL from '../../../config';



const modifyUserDataPage = () => {
  clearPage();
  renderPageTitle('Changer Données Personelles');
  renderInfoChangeForm();
};

function renderInfoChangeForm() {
  const user = getAuthenticatedUser();

  const main = document.querySelector('main');
  const form = document.createElement('form');
  form.className = 'p-5';
  
  const expl = document.createElement('h3');
  expl.type = 'text';
  expl.textContent ='Ne rentrez des données que dans les champs où vous voulez changer vos informations :'


  const br1 = document.createElement('div');
  br1.type = 'text';
  br1.textContent ='Le mail ne peut pas être modifié'
  
  const login = document.createElement('input');
  login.type = 'email';
  login.id = 'login';
  login.value = user.email;
  login.required = true;
  login.className = 'form-control mb-3';
  login.readOnly = true; // Make the input field read-only
  login.title = "This email can't be modified"; // Add a tooltip
 

  const password = document.createElement('input');
  password.type = 'password';
  password.id = 'password';
  password.placeholder = 'Rentrez votre nouveau mot de passe';
  password.className = 'form-control mb-3';
  
  const passwordRepet = document.createElement('input');
  passwordRepet.type = 'password';
  passwordRepet.id = 'password2';
  passwordRepet.placeholder = 'Confirmez le nouveau mot de passe';
  passwordRepet.className = 'form-control mb-3';
  passwordRepet.disabled = true; 

  password.addEventListener('input', function() {
    if (this.value) { 
        passwordRepet.disabled = true; 
    }
    passwordRepet.disabled = false;
});

  const last_name = document.createElement('input');
  last_name.type = 'text';
  last_name.id = 'l_name';
  last_name.placeholder = 'Nom';
  last_name.className = 'form-control mb-3';

  const first_name = document.createElement('input');
  first_name.type = 'text';
  first_name.id = 'f_name';
  first_name.placeholder = 'Prénom';
  first_name.className = 'form-control mb-3';

  const phone_num = document.createElement('input');
  phone_num.type = 'text';
  phone_num.id = 'phone_number';
  phone_num.placeholder = 'Numéro de téléphone';
  phone_num.className = 'form-control mb-3';

  // eslint-disable-next-line no-unused-vars
  phone_num.addEventListener('input', function (event) {
    const phoneNumberPattern = /^04\d{8}$/;
    if (!phoneNumberPattern.test(this.value)) {
      this.setCustomValidity('Numéro de téléphone invalide. Il doit commencer par 04 et être suivi de 8 chiffres.');
    } else {
      this.setCustomValidity('');
    }
  });

  const submit = document.createElement('input');
  submit.value = 'Confirmer';
  submit.type = 'submit';
  submit.className = 'btn btn-info';
  const formCheckWrapper = document.createElement('div');
  formCheckWrapper.className = 'mb-3 form-check';

  form.appendChild(expl);
  form.appendChild(br1);
  form.appendChild(login);


  form.appendChild(password);
  form.appendChild(passwordRepet);
  form.appendChild(first_name);
  form.appendChild(last_name);
  form.appendChild(phone_num);
  form.appendChild(formCheckWrapper);
  form.appendChild(submit);

  main.appendChild(form);
  form.addEventListener('submit', onSubmit);
}

async function onSubmit(e) {
  const user = getAuthenticatedUser();
  e.preventDefault();

  const login = document.querySelector('#login').value;
  const password = document.querySelector('#password').value;
  const passwordRepet = document.querySelector('#password2').value
  const f_name = document.querySelector('#f_name').value;
  const l_name = document.querySelector('#l_name').value;
  const phone_number = document.querySelector('#phone_number').value;
  const version = user.version;

  if (password !== passwordRepet) {
    alert('Les mots de passe sont différent. Veuillez réessayer.');
    return; // Stop the function if the passwords do not match
  }

  const userData = {
    login,
    f_name,
    l_name,
    phone_number,
    version
  };

  // Only include the password in the request body if it's not empty
  if (password) {
    userData.password = password;
  }

  const options = {
    method: 'POST',
    body: JSON.stringify(userData),
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `${user.token}`,
    },
  };

  const response = await fetch(`${baseURL}/users/changeData`, options);
  console.log(response);
  if (!response.ok) {
    if (response.status === 400) {
      // Display a popup message for incorrect username or password
      alert("L'utilisateur n'a pas été trouvé");
  } else {
      // For other errors, handle them accordingly
      alert("Une erreur est survenue : ", response.statusText);
      console.error("An error occurred:", response.statusText);
  }
      return;
  }
  
  // If the request is successful, update the user data in local storage
  const updatedUser = await response.json();
  console.log(updatedUser);
  localStorage.setItem('user', JSON.stringify(updatedUser));
  setAuthenticatedUser(updatedUser);
  Navbar();
  Navigate('/users/userData');
}

export default modifyUserDataPage;

/* eslint-disable camelcase */
import { getAuthenticatedUser } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';



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
  expl.textContent ='Ne rentrez des donées que dans les champs ou vous voulez changer vos informations:'


  const br1 = document.createElement('div');
  br1.type = 'text';
  br1.textContent ='Le mail ne peut pas etre modifie'
  
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
        passwordRepet.disabled = false; 
        passwordRepet.disabled = true; 
    }
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
      this.setCustomValidity('Invalid phone number. It should start with 04 and followed by 8 digits.');
    } else {
      this.setCustomValidity('');
    }
  });








  const submit = document.createElement('input');
  submit.value = 'Envoyer';
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
  e.preventDefault();
  const form = document.querySelector('form');



  const login = document.querySelector('#login').value;
  const password = document.querySelector('#password').value;
  const passwordRepet = document.querySelector('#password2').value
  const f_name = document.querySelector('#f_name').value;
  const l_name = document.querySelector('#l_name').value;
  const phone_number = document.querySelector('#phone_number').value;
  
  
  form.addEventListener('submit', (event) => {
    if (password.value !== passwordRepet.value) {
        event.preventDefault();
        alert('The passwords do not match. Please try again.');
    }
});

  const options = {
    method: 'POST',
    body: JSON.stringify({
      login,
      password,
      f_name,
      l_name,
      phone_number
    }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = await fetch(`http://localhost:3000/changeData`, options);
  console.log(response);
  if (!response.ok) {
    if (response.status === 400) {
      // Display a popup message for incorrect username or password
      alert("L'utilisateur existe déjà, veuillez vous connecter ou créer un autre compte.");
  } else {
      // For other errors, handle them accordingly
      console.error("An error occurred:", response.statusText);
  }
      return;
  }
  Navbar();
  Navigate('/');
}

export default modifyUserDataPage;

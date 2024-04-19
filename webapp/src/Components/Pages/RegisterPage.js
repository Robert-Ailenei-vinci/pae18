/* eslint-disable camelcase */
import { getRememberMe, setAuthenticatedUser, setRememberMe } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';
import baseURL from "../../../config";

const RegisterPage = () => {
  clearPage();
  renderPageTitle('Register');
  renderRegisterForm();
};

function renderRegisterForm() {
  const main = document.querySelector('main');
  const form = document.createElement('form');
  form.className = 'p-5 shadow-lg rounded-lg fade';

  const login = document.createElement('input');
  login.type = 'email';
  login.id = 'login';
  login.placeholder = 'Email';
  login.required = true;
  login.className = 'form-control mb-3';
  login.addEventListener('input', onEmailInput); // Add event listener for email input

  const password = document.createElement('input');
  password.type = 'password';
  password.id = 'password';
  password.required = true;
  password.placeholder = 'Mot de passe';
  password.className = 'form-control mb-3';

  const last_name = document.createElement('input');
  last_name.type = 'text';
  last_name.id = 'l_name';
  last_name.required = true;
  last_name.placeholder = 'Nom';
  last_name.className = 'form-control mb-3';

  const first_name = document.createElement('input');
  first_name.type = 'text';
  first_name.id = 'f_name';
  first_name.required = true;
  first_name.placeholder = 'Prénom';
  first_name.className = 'form-control mb-3';

  const phone_num = document.createElement('input');
  phone_num.type = 'text';
  phone_num.id = 'phone_number';
  phone_num.required = true;
  phone_num.placeholder = 'Numéro de téléphone';
  phone_num.className = 'form-control mb-3';

  // eslint-disable-next-line no-unused-vars
  phone_num.addEventListener('input', function (event) {
    const phoneNumberPattern = /^04\d{8}$/;
    if (!phoneNumberPattern.test(this.value)) {
      this.setCustomValidity(
          `Numéro de téléphone invalide. Il doit commencer par "04" et suivi de 8 chiffres.`);
    } else {
      this.setCustomValidity('');
    }
  });

  const roleAdmin = document.createElement('input');
  roleAdmin.type = 'checkbox';
  roleAdmin.id = 'roleAdmin';
  roleAdmin.className = 'form-check-input';
  roleAdmin.style.display = 'none'; // Hide by default  

  // Create labels for the checkboxes
  const roleAdminLabel = document.createElement('label');
  roleAdminLabel.htmlFor = 'roleAdmin';
  roleAdminLabel.textContent = 'Administratif';
  roleAdminLabel.id = 'roleAdminLabel'; // Define ID for the label
  roleAdminLabel.style.display = 'none'; // Hide by default

  const roleProf = document.createElement('input');
  roleProf.type = 'checkbox';
  roleProf.id = 'roleProf';
  roleProf.className = 'form-check-input';
  roleProf.style.display = 'none'; // Hide by default

  const roleProfLabel = document.createElement('label');
  roleProfLabel.htmlFor = 'roleProf';
  roleProfLabel.textContent = 'Professeur';
  roleProfLabel.id = 'roleProfLabel'; // Define ID for the label
  roleProfLabel.style.display = 'none'; // Hide by default

  const submit = document.createElement('input');
  submit.value = 'Register';
  submit.type = 'submit';
  submit.className = 'btn btn-info bg-custom';
  const formCheckWrapper = document.createElement('div');
  formCheckWrapper.className = 'mb-3 form-check';

  const rememberme = document.createElement('input');
  rememberme.type = 'checkbox';
  rememberme.className = 'form-check-input';
  rememberme.id = 'rememberme';
  const remembered = getRememberMe();
  rememberme.checked = remembered;
  rememberme.addEventListener('click', onCheckboxClicked);

  const checkLabel = document.createElement('label');
  checkLabel.htmlFor = 'rememberme';
  checkLabel.className = 'form-check-label';
  checkLabel.textContent = 'Remember me';

  formCheckWrapper.appendChild(rememberme);
  formCheckWrapper.appendChild(checkLabel);

  form.appendChild(login);
  // Create a wrapper for each checkbox and its label
  const roleAdminWrapper = document.createElement('div');
  roleAdminWrapper.appendChild(roleAdmin);
  roleAdminWrapper.appendChild(roleAdminLabel);
  form.appendChild(roleAdminWrapper);

  const roleProfWrapper = document.createElement('div');
  roleProfWrapper.appendChild(roleProf);
  roleProfWrapper.appendChild(roleProfLabel);
  form.appendChild(roleProfWrapper);

  form.appendChild(password);
  form.appendChild(first_name);
  form.appendChild(last_name);
  form.appendChild(phone_num);
  form.appendChild(formCheckWrapper);
  form.appendChild(submit);
  form.offsetWidth;

  // Add the 'show' class to start the animation
  form.classList.add('show');
  main.appendChild(form);
  form.addEventListener('submit', onRegister);
}

function onCheckboxClicked(e) {
  setRememberMe(e.target.checked);
}

let role;

function onEmailInput(e) {
  const email = e.target.value;
  const roleAdmin = document.querySelector('#roleAdmin');
  const roleProf = document.querySelector('#roleProf');
  const roleAdminLabel = document.querySelector('#roleAdminLabel');
  const roleProfLabel = document.querySelector('#roleProfLabel');

  if (email.endsWith('@student.vinci.be')) {
    role = 'etudiant';
  } else if (email.endsWith('@vinci.be')) {
    roleAdmin.style.display = 'block';
    roleProf.style.display = 'block';
    roleAdminLabel.style.display = 'block'; // Show label
    roleProfLabel.style.display = 'block'; // Show label
  } else {

    roleAdmin.style.display = 'none';
    roleProf.style.display = 'none';
    roleAdminLabel.style.display = 'none'; // Hide label
    roleProfLabel.style.display = 'none'; // Hide label
    return;
  }

  // Add event listeners to checkboxes
  roleAdmin.addEventListener('change', function () {
    if (this.checked) {
      roleProf.checked = false;
    }
  });

  roleProf.addEventListener('change', function () {
    if (this.checked) {
      roleAdmin.checked = false;
    }
  });
}

async function onRegister(e) {
  e.preventDefault();

  const email = document.querySelector('#login').value;
  const password = document.querySelector('#password').value;
  const firstName = document.querySelector('#f_name').value;
  const lastName = document.querySelector('#l_name').value;
  const phoneNum = document.querySelector('#phone_number').value;
  const roleAdmin = document.querySelector('#roleAdmin').checked;
  const roleProf = document.querySelector('#roleProf').checked;

  if (!email.endsWith('@student.vinci.be') && !email.endsWith('@vinci.be')) {
    alert('Mail doit finir en @vinci.be ou en @student.vinci.be');
    return;
  }

  if (roleAdmin) {
    role = 'administratif';
  }
  if (roleProf) {
    role = 'professeur';
  }
// Check if at least one checkbox is checked
  if (!roleAdmin && !roleProf && role !== "etudiant") {
    alert('Veuillez indiquer si vous êtes un administratif ou un professeur: ');
    return;
  }
  const options = {
    method: 'POST',
    body: JSON.stringify({
      email,
      password,
      firstName,
      lastName,
      phoneNum,
      role
    }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = await fetch(`${baseURL}/auths/register`, options);

  if (!response.ok) {
    response.text().then((errorMessage) => {
      alert(errorMessage);
    });
    return;
  }
  const authenticatedUser = await response.json();
  setAuthenticatedUser(authenticatedUser);
  Navbar();
  Navigate('/users/userData');
  }


export default RegisterPage;

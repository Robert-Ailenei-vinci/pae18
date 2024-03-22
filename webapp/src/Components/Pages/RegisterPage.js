/* eslint-disable camelcase */
import { getRememberMe, setRememberMe } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';



const RegisterPage = () => {
  clearPage();
  renderPageTitle('Register');
  renderRegisterForm();
};

function renderRegisterForm() {
  const main = document.querySelector('main');
  const form = document.createElement('form');
  form.className = 'p-5';
  const login = document.createElement('input');
  login.type = 'email';
  login.id = 'login';
  login.placeholder = 'Email address';
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
      this.setCustomValidity('Invalid phone number. It should start with 04 and followed by 8 digits.');
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
  submit.className = 'btn btn-info';
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
  roleAdmin.addEventListener('change', function() {
    if (this.checked) {
      roleProf.checked = false;
    }
  });

  roleProf.addEventListener('change', function() {
    if (this.checked) {
      roleAdmin.checked = false;
    }
  });
}




async function onRegister(e) {
  e.preventDefault();

  const login = document.querySelector('#login').value;
  const password = document.querySelector('#password').value;
  const f_name = document.querySelector('#f_name').value;
  const l_name = document.querySelector('#l_name').value;
  const phone_number = document.querySelector('#phone_number').value;
  const roleAdmin = document.querySelector('#roleAdmin').checked;
  const roleProf = document.querySelector('#roleProf').checked;


  
  if (roleAdmin) role = 'administratif';
  if (roleProf) role = 'professeur';
// Check if at least one checkbox is checked
if (!roleAdmin && !roleProf && role!=="etudiant") {
  alert('Veuillez indiquer si vous etes un administratif ou un professeur: ');
  return;
}
  // Check if the email is either @vinci.be or @student.vinci.be
  if (!login.endsWith('@vinci.be') && !login.endsWith('@student.vinci.be')) {
    alert('Invalid email. Please use a @vinci.be or @student.vinci.be email.');
    return;
  }

  const options = {
    method: 'POST',
    body: JSON.stringify({
      login,
      password,
      f_name,
      l_name,
      phone_number,
      role
    }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = await fetch(`http://localhost:3000/auths/register`, options);

  if (!response.ok) {
    if (response.status === 409) {
      // Display a popup message for incorrect username or password
      alert("L'utilisateur existe déjà, veuillez vous connecter ou créer un autre compte.");
  } else {
      // For other errors, handle them accordingly
      console.error("An error occurred:", response.statusText);
  }
      return;
  }
  Navbar();
  Navigate('/login');
}

export default RegisterPage;

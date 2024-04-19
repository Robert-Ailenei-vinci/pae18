import { getRememberMe, setAuthenticatedUser, setRememberMe } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';
import baseURL from '../../../config';

const LoginPage = () => {
  clearPage();
  renderPageTitle('Login');
  renderLoginForm();
};

function renderLoginForm() {
  const main = document.querySelector('main');

  // Create form using Bootstrap classes
  const form = document.createElement('form');
  form.className = 'p-5 shadow-lg rounded-lg fade';
  
  const mailGroup = document.createElement('div');
  mailGroup.className = 'mb-3';
  const mailLabel = document.createElement('label');
  mailLabel.textContent = 'Email';
  mailLabel.setAttribute('for', 'mail');
  const mail = document.createElement('input');
  mail.type = 'email';
  mail.id = 'mail';
  mail.className = 'form-control';
  mail.required = true;
  mail.placeholder = 'Enter your email';

  mailGroup.appendChild(mailLabel);
  mailGroup.appendChild(mail);

  const passwordGroup = document.createElement('div');
  passwordGroup.className = 'mb-3';
  const passwordLabel = document.createElement('label');
  passwordLabel.textContent = 'Password';
  passwordLabel.setAttribute('for', 'password');
  const password = document.createElement('input');
  password.type = 'password';
  password.id = 'password';
  password.className = 'form-control';
  password.required = true;
  password.placeholder = 'Enter your password';

  passwordGroup.appendChild(passwordLabel);
  passwordGroup.appendChild(password);

  const remembermeGroup = document.createElement('div');
  remembermeGroup.className = 'mb-3 form-check';
  const rememberme = document.createElement('input');
  rememberme.type = 'checkbox';
  rememberme.className = 'form-check-input';
  rememberme.id = 'rememberme';
  const remembered = getRememberMe();
  rememberme.checked = remembered;
  rememberme.addEventListener('click', onCheckboxClicked);
  const remembermeLabel = document.createElement('label');
  remembermeLabel.htmlFor = 'rememberme';
  remembermeLabel.className = 'form-check-label';
  remembermeLabel.textContent = 'Remember me';

  remembermeGroup.appendChild(rememberme);
  remembermeGroup.appendChild(remembermeLabel);

  const submit = document.createElement('button');
  submit.textContent = 'Login';
  submit.type = 'submit';
  submit.className = 'btn btn-info btn-block mt-4 bg-custom';

  form.appendChild(mailGroup);
  form.appendChild(passwordGroup);
  form.appendChild(remembermeGroup);
  form.appendChild(submit);
  main.appendChild(form);
  form.offsetWidth;

  // Add the 'show' class to start the animation
  form.classList.add('show');
  // Add event listener for form submission
  form.addEventListener('submit', onLogin);
}

// Function to handle remember me checkbox
function onCheckboxClicked(e) {
  setRememberMe(e.target.checked);
}

// Function to handle form submission
async function onLogin(e) {
  e.preventDefault();

  const email = document.querySelector('#mail').value;
  const password = document.querySelector('#password').value;

  const options = {
    method: 'POST',
    body: JSON.stringify({ email, password }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  try {
    const response = await fetch(`${baseURL}/auths/login`, options);

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

    const authenticatedUser = await response.json();

    console.log('Authenticated user : ', authenticatedUser);

    setAuthenticatedUser(authenticatedUser);

    Navbar();

    Navigate('/users/userData');
  } catch (error) {
    console.error("An error occurred:", error.message);
  }
}

export default LoginPage;

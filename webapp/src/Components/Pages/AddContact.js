import { getAuthenticatedUser } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';

const AddContactPage = () => {
  clearPage();
  renderPageTitle('Créer un contact');
  renderRegisterForm();
};

function renderRegisterForm() {
  const main = document.querySelector('main');
  const form = document.createElement('form');
  form.className = 'p-5';
  const entreprise = document.createElement('input');
  entreprise.type = 'text';
  entreprise.id = 'entreprise';
  entreprise.placeholder = 'Sélectionner une entreprise';
  entreprise.required = true;
  entreprise.className = 'form-control mb-3';
  const submit = document.createElement('input');
  submit.value = 'Confirmer';
  submit.type = 'submit';
  submit.className = 'btn btn-info';
  const cancel = document.createElement('input');
  cancel.value = 'Annuler';
  cancel.className = 'btn btn-info';
  const addEntreprise = document.createElement('input');
  addEntreprise.value = 'Ajouter une Entreprise';
  addEntreprise.className = 'btn btn-info';
  const formCheckWrapper = document.createElement('div');
  formCheckWrapper.className = 'mb-3 form-check';

  form.appendChild(entreprise);
  form.appendChild(addEntreprise);
  form.appendChild(formCheckWrapper);
  form.appendChild(cancel);
  form.appendChild(submit);
  form.addEventListener('submit', onSubmit);
  cancel.addEventListener('click', onCancel);
  addEntreprise.addEventListener('click', onAddEntreprise);
  main.appendChild(form);
}

async function onSubmit(e) {
  e.preventDefault();

  const entreprise = document.querySelector('#entreprise').value;

  const options = {
    method: 'POST',
    body: JSON.stringify({
      login: entreprise,
      user: getAuthenticatedUser(),
    }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = await fetch(`http://localhost:3000/contact/add`, options);

  if (!response.ok) {
    if (response.status === 401) {
      // Display a popup message for incorrect username or password
      alert('Entreprise not found, please add it before anything.');
    } else {
      // For other errors, handle them accordingly
      console.error('An error occurred:', response.statusText);
    }
    return;
  }

  const newContact = await response.json();

  console.log('Added contact : ', newContact);

  Navbar();

  Navigate('/');
}

function onCancel(e) {
  e.preventDefault();
  // TODO: Redirect to previous page
  console.log('Canceling');

  Navbar();

  Navigate('/');
}

function onAddEntreprise(e) {
  e.preventDefault();
  // TODO: Redirect to add entreprise
  console.log('Adding entreprise');

  Navbar();

  Navigate('/');
}

export default AddContactPage;

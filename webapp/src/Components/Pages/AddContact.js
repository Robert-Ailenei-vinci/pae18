import { getAuthenticatedUser } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';

const AddContactPage = async () => {
  clearPage();
  renderPageTitle('Créer un contact');
  renderRegisterForm();
  await renderEntreprises();
};

function renderRegisterForm() {
  const main = document.querySelector('main');
  const form = document.createElement('form');
  form.className = 'p-5';
  form.id = 'entreprise-form';
  // Entreprise Dropdown
  const dropdown = document.createElement('input');
  dropdown.type = 'checkbox';
  dropdown.id = 'dropdown';
  dropdown.name = 'dropdown';
  dropdown.className = 'dropdown';
  dropdown.required = true;
  dropdown.className = 'form-control mb-3';
  // Entreprise Label
  const dropdownLabel = document.createElement('label');
  dropdownLabel.className = 'for-dropdown';
  dropdownLabel.htmlFor = 'dropdown';
  dropdownLabel.textContent = 'Dropdown Menu ';
  const dropdownIcon = document.createElement('i');
  dropdownIcon.className = 'uil uil-arrow-down';

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

  dropdownLabel.appendChild(dropdownIcon);
  form.appendChild(dropdown);
  form.appendChild(dropdownLabel);
  form.appendChild(addEntreprise);
  form.appendChild(formCheckWrapper);
  form.appendChild(cancel);
  form.appendChild(submit);
  form.addEventListener('submit', onSubmit);
  cancel.addEventListener('click', onCancel);
  addEntreprise.addEventListener('click', onAddEntreprise);
  main.appendChild(form);
}

async function renderEntreprises() {

  const form = document.getElementById('entreprise-form');
  // Create dropdown content section
  const dropdownSection = document.createElement('div');
  dropdownSection.className = 'section-dropdown';
  // Create dropdown link
  const dropdownLink = document.createElement('a');
  dropdownLink.href = '#';
  dropdownLink.textContent = 'Dropdown Link ';
  const dropdownLinkIcon = document.createElement('i');
  dropdownLinkIcon.className = 'uil uil-arrow-right';
  dropdownLink.appendChild(dropdownLinkIcon);
  dropdownSection.appendChild(dropdownLink);
  // Create dropdown sub checkbox
  const dropdownSubCheckbox = document.createElement('input');
  dropdownSubCheckbox.type = 'checkbox';
  dropdownSubCheckbox.id = 'dropdown-sub';
  dropdownSubCheckbox.name = 'dropdown-sub';
  dropdownSubCheckbox.className = 'dropdown-sub';
  dropdownSection.appendChild(dropdownSubCheckbox);
  // Create dropdown sub content section
  const dropdownSubSection = document.createElement('div');
  dropdownSubSection.className = 'section-dropdown-sub';
  const entrepriseList = await allEntreprises();
  entrepriseList.forEach(entreprise => {
    const dropdownSubLink = document.createElement('a');
    dropdownSubLink.href = '#';
    dropdownSubLink.textContent = entreprise;
    const dropdownSubLinkIcon1 = document.createElement('i');
    dropdownSubLink.appendChild(dropdownSubLinkIcon1);
    dropdownSubSection.appendChild(dropdownSubLink);
  });
  // Append dropdown sub content to dropdown section
  dropdownSection.appendChild(dropdownSubSection);

  // Append dropdown section to form
  form.appendChild(dropdownSection);
}


async function allEntreprises() {
    const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  };
  const response = await fetch(`http://localhost:3000/entreprise/getAll`, options);

  if (!response.ok) {
    if (response.status === 401) {
      // Display a popup message for incorrect username or password
      alert('No entreprise have been found');
    } else {
      // For other errors, handle them accordingly
      console.error('An error occurred:', response.statusText);
    }
  }
  return response;
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

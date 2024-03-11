import { getAuthenticatedUser } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';

const AddContactPage = async () => {
  clearPage();
  renderPageTitle('Créer un contact');
  renderRegisterForm();
};

function renderRegisterForm() {
  const entreprises = ["Entreprise 1", "Entreprise 2", "Entreprise 3"];

  const form = createFormElement();

  const row = document.createElement('div');
  row.className = 'row';

  const dropdownContainer = createDropdownContainer();
  dropdownContainer.appendChild(createDropdownButton());
  const dropdownContent = createDropdownContent(entreprises);
  dropdownContainer.appendChild(dropdownContent);
  row.appendChild(dropdownContainer);

  const addEntrepriseButtonContainer = createAddEntrepriseButtonContainer();
  addEntrepriseButtonContainer.appendChild(createAddEntrepriseButton());
  row.appendChild(addEntrepriseButtonContainer);

  form.appendChild(row);

  const buttonGroup = document.createElement('div');
  buttonGroup.className = 'form-group mt-3';
  buttonGroup.appendChild(createSubmitButton());
  buttonGroup.appendChild(createCancelButton());
  form.appendChild(buttonGroup);

  const container = document.createElement('div');
  container.className = 'container';
  container.appendChild(form);

  document.body.appendChild(container);

  document.getElementById('dropbtn').addEventListener('click', toggleDropdown);
  document.getElementById('myForm').addEventListener('submit', handleSubmit);

  dropdownContent.querySelectorAll('.dropdown-item').forEach(item => {
    item.addEventListener('click', () => {
      document.getElementById('dropbtn').textContent = item.textContent;
      // TODO: Add logic for retrieving entreprise name
    });
  });
}

function createFormElement() {
  const form = document.createElement('form');
  form.id = 'myForm';
  form.className = 'm-3';
  return form;
}

function createDropdownContainer() {
  const dropdownContainer = document.createElement('div');
  dropdownContainer.className = 'col-md-6 mb-3';
  return dropdownContainer;
}

function createDropdownButton() {
  const dropdownButton = document.createElement('button');
  dropdownButton.id = 'dropbtn';
  dropdownButton.className = 'btn btn-secondary dropdown-toggle btn-block';
  dropdownButton.type = 'button';
  dropdownButton.textContent = 'Select Entreprise';
  return dropdownButton;
}

function createDropdownContent(entreprises) {
  const dropdownContent = document.createElement('div');
  dropdownContent.id = 'myDropdown';
  dropdownContent.className = 'dropdown-menu';
  entreprises.forEach(entreprise => {
    const option = document.createElement('button');
    option.className = 'dropdown-item';
    option.type = 'button';
    option.textContent = entreprise;
    dropdownContent.appendChild(option);
  });
  return dropdownContent;
}

function createAddEntrepriseButtonContainer() {
  const container = document.createElement('div');
  container.className = 'col-md-6 mb-3';
  return container;
}

function createAddEntrepriseButton() {
  const addEntrepriseButton = document.createElement('button');
  addEntrepriseButton.className = 'btn btn-primary btn-block';
  addEntrepriseButton.textContent = 'Ajouter une entreprise';
  addEntrepriseButton.addEventListener('click', () => {
    alert('Add entreprise clicked');
    // TODO: Add logic for adding an entreprise here
  });
  return addEntrepriseButton;
}

function createSubmitButton() {
  const submitButton = document.createElement('button');
  submitButton.type = 'submit';
  submitButton.className = 'btn btn-primary mr-2';
  submitButton.textContent = 'Submit';
  return submitButton;
}

function createCancelButton() {
  const cancelButton = document.createElement('button');
  cancelButton.type = 'button';
  cancelButton.className = 'btn btn-secondary';
  cancelButton.textContent = 'Cancel';
  cancelButton.addEventListener('click', () => {
    alert('Form cancelled');
    // TODO: Add cancellation logic here
  });
  return cancelButton;
}

function toggleDropdown() {
  const dropdownContent = document.getElementById('myDropdown');
  // eslint-disable-next-line no-unused-expressions
  dropdownContent.style.display === 'block' ? dropdownContent.style.display = 'none' : dropdownContent.style.display = 'block';
}

async function handleSubmit() {
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
    handleError(response);
    return;
  }

  const newContact = await response.json();
  console.log('Added contact : ', newContact);
  Navbar();
  Navigate('/');
}

function handleError(response) {
  if (response.status === 401) {
    alert("Username or password is incorrect. Please try again.");
  } else {
    console.error("An error occurred:", response.statusText);
  }
}

export default AddContactPage;

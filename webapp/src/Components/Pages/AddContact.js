import { getAuthenticatedUser } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';
import baseURL from '../../../config';

const AddContactPage = async () => {
  clearPage();
  renderPageTitle('Créer un contact');
  await renderNewContactForm();
};

async function renderNewContactForm() {
  const form = createFormElement();

  const row = document.createElement('div');
  row.className = 'row';

  const dropdownContainer = createDropdownContainer();
  dropdownContainer.appendChild(createDropdownButton());
  const dropdownContent = createDropdownContent(await fetchEntreprises());
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
  const main = document.querySelector('main');
  main.appendChild(container);

  let selectedEntrepriseId = -1;

  dropdownContent.querySelectorAll('.dropdown-item').forEach(item => {
    item.addEventListener('click', () => {
      selectedEntrepriseId = item.id;
      document.getElementById('dropbtn').textContent = item.textContent;
    });
  });
  document.getElementById('dropbtn').addEventListener('click', toggleDropdown);
  document.getElementById('myForm').addEventListener('submit', () =>{
    handleSubmit(selectedEntrepriseId);
    Navigate('/users/userData');
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
  dropdownButton.textContent = 'Choisir une entreprise';
  return dropdownButton;
}

function createDropdownContent(entreprises) {
  const dropdownContent = document.createElement('div');
  dropdownContent.id = 'myDropdown';
  dropdownContent.className = 'dropdown-menu';
  entreprises.forEach(entreprise => {
    if (!entreprise.blacklisted) {
      const option = document.createElement('button');
      option.className = 'dropdown-item';
      option.type = 'button';
      option.textContent = entreprise.tradeName;
      option.id = entreprise.id;
      dropdownContent.appendChild(option);
    }
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
    Navigate('/addEnterprise');
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
  cancelButton.textContent = 'Annuler';
  cancelButton.addEventListener('click', () => {
    Navbar();
    Navigate('/users/userData');
  });
  return cancelButton;
}

function toggleDropdown() {
  const dropdownContent = document.getElementById('myDropdown');
  // eslint-disable-next-line no-unused-expressions
  dropdownContent.style.display === 'block' ? dropdownContent.style.display = 'none' : dropdownContent.style.display = 'block';
}

async function handleSubmit(selectedEntrepriseId) {
  if (selectedEntrepriseId === -1) {
    alert('Veuillez choisir une entreprise.')
  } else {
  const entreprise = selectedEntrepriseId;
  console.log(entreprise);
  const currentYearId = "1";
  const options = {
    method: 'POST',
    body: JSON.stringify({
      entreprise,
      user: getAuthenticatedUser().id,
      schoolYear: currentYearId,
    }),
    headers: {
      'Content-Type': 'application/json',
      'authorization': getAuthenticatedUser().token,
    },
  };

  const response = await fetch(`${baseURL}/contacts/add`, options);

  if (!response.ok) {
    handleError(response);
    return;
  }

  Navbar();
  Navigate('/users/userData');
  window.location.reload();
  }

}

function handleError(response) {
  if (response.status === 409){
    alert("Vous avez deja un contact pour cet entreprise");
  }
  if (response.status === 401) {
    alert("An error occured while fetching with the server.");
  } else {
    alert("Une erreur est survenue : ", response.statusText);
    console.error("An error occurred:", response.statusText);
  }
}

async function fetchEntreprises() {
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'authorization': getAuthenticatedUser().token,
    },
  };

  const response = await fetch(`${baseURL}/entreprise/getAll`, options);

  if (!response.ok) {
    handleError(response);
    return null;
  }

  const entreprises = await response.json();
  console.log('All entreprises : ', entreprises);
  return entreprises;
}

export default AddContactPage;

import { getAuthenticatedUser } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';
import baseURL from '../../../config';

const AddEnterprisePage = async () => {
  clearPage();
  renderPageTitle('Ajouter une entreprise');
  await renderNewEnterpriseForm();
};

async function renderNewEnterpriseForm() {
  const form = createFormElement();

  const tradeNameInput = createTextInput('trade_name', 'Nom Commercial');
  form.appendChild(tradeNameInput);

  const designationInput = createTextInput('designation', 'Appelation');
  form.appendChild(designationInput);

  const addressInput = createTextInput('address', 'Adresse');
  form.appendChild(addressInput);

  const phoneNumInput = createTextInput('phone_num', 'Numéro de téléphone');
  form.appendChild(phoneNumInput);

  const emailInput = createTextInput('email', 'Email');
  form.appendChild(emailInput);

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

  document.getElementById('myForm').addEventListener('submit', (event) => {
    event.preventDefault();
    const tradeName = document.getElementById('trade_name').value;
    const designation = document.getElementById('designation').value;
    const address = document.getElementById('address').value;
    const phoneNum = document.getElementById('phone_num').value;
    const email = document.getElementById('email').value;
    handleSubmit(tradeName, designation, address, phoneNum, email);
  });
}

function createFormElement() {
  const form = document.createElement('form');
  form.id = 'myForm';
  form.className = 'm-3';
  return form;
}

function createTextInput(id, label) {
  const div = document.createElement('div');
  div.className = 'form-group';
  const input = document.createElement('input');
  input.type = 'text';
  input.className = 'form-control';
  input.id = id;
  input.placeholder = label;
  div.appendChild(input);
  return div;
}

function createSubmitButton() {
  const submitButton = document.createElement('button');
  submitButton.type = 'submit';
  submitButton.className = 'btn btn-primary mr-2';
  submitButton.textContent = 'Valider';
  return submitButton;
}

function createCancelButton() {
  const cancelButton = document.createElement('button');
  cancelButton.type = 'button';
  cancelButton.className = 'btn btn-secondary';
  cancelButton.textContent = 'Annuler';
  cancelButton.addEventListener('click', () => {
    Navbar();
    Navigate('/addContact');
  });
  return cancelButton;
}

async function handleSubmit(trade_name, designation, address, phone_num, email) {
  if (!trade_name || !address) {
    alert("Le nom et l'adresse de l'entreprise sont nécessaires.");
    return;
  }

  const options = {
    method: 'POST',
    body: JSON.stringify({
      trade_name,
      designation,
      address,
      phone_num,
      email,
      user: getAuthenticatedUser().id,
    }),
    headers: {
      'Content-Type': 'application/json',
      'authorization': getAuthenticatedUser().token,
    },
  };

  try {
    const response = await fetch(`${baseURL}/entreprise/addOne`, options);
    if (!response.ok) {
      throw new Error(`Failed to add contact: ${response.statusText}`);
    }
    await response.json();
    alert(`Entreprise ajouté.`);
    Navbar();
    Navigate('/addContact');
  } catch (error) {
    console.error('Error adding contact:', error.message);
    alert(`Une erreur est survenue lors de l'ajout d'un contact. Veuillez réessayer.`);
  }
}

export default AddEnterprisePage;

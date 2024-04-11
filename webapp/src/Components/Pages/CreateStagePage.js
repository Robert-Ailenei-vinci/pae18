/* eslint-disable no-unused-vars */
import {clearPage, renderPageTitle} from "../../utils/render";
import {main} from "@popperjs/core";
import Navbar from "../Navbar/Navbar";
import {getAuthenticatedUser} from "../../utils/auths";
import baseURL from "../../../config";

const CreateStagePage = (contact) => {
  clearPage();
  renderPageTitle(`Accepté le contact et crée un stage pour ${contact.entreprise.tradeName}`);
  renderCreateStageForm(contact);

}

function renderCreateStageForm(contact) {
  console.log(contact);
  const main = document.querySelector('main');

  const form = createFormElement();

  const dropdownContainer = createDropdownContainer();
  dropdownContainer.appendChild(createDropdownButton());
  const dropdownContent = createDropdownContent(getAllSupervisor(contact.entrepriseId));
  dropdownContainer.appendChild(dropdownContent);

  const inputProject = createTextInput(`project`, `Objectif du stage`);
  form.appendChild(inputProject);

  const dateSign = createDateInput('dateSign','Date de Signature')
  form.appendChild(dateSign);


  const buttonGroup = document.createElement('div');
  buttonGroup.className = 'form-group mt-3';
  buttonGroup.appendChild(createSubmitButton());
  buttonGroup.appendChild(createCancelButton());
  form.appendChild(buttonGroup);


  main.appendChild(form);
}

async function createStage() {

}

async function getAllSupervisor(entrepriseId) {

  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'authorization': getAuthenticatedUser().token,
    },
  };

  const response = await fetch(`${baseURL}/entreprise/getAll`, options);

  if (!response.ok) {
    alert('Prob avec fetch')
    return null;
  }

  const supervisors = await response.json();
  return supervisors;

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

function createDateInput(id, label) {
  const inputGroup = document.createElement('div');
  inputGroup.className = 'form-group';

  const labelElement = document.createElement('label');
  labelElement.setAttribute('for', id);
  labelElement.textContent = label + ' :';

  const input = document.createElement('input');
  input.setAttribute('type', 'date');
  input.setAttribute('id', id);
  input.setAttribute('name', id);
  input.required = true;

  inputGroup.appendChild(labelElement);
  inputGroup.appendChild(input);

  return inputGroup;
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
  dropdownButton.textContent = 'Selectionnez Votre maitre de stage';
  return dropdownButton;
}

function createDropdownContent(supervisors) {
  const dropdownContent = document.createElement('div');
  dropdownContent.id = 'myDropdown';
  dropdownContent.className = 'dropdown-menu';
  supervisors.forEach(supervisor => {
    const option = document.createElement('button');
    option.className = 'dropdown-item';
    option.type = 'button';
    option.textContent = supervisor.lastName+' '+supervisor.firstName;
    option.id = supervisor.id;
    dropdownContent.appendChild(option);
  });
  return dropdownContent;
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
    Navbar();
    location.reload()
  });
  return cancelButton;
}

export default CreateStagePage;
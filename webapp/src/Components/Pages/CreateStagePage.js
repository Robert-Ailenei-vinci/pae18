/* eslint-disable no-unused-vars */
import {clearPage, renderPageTitle} from "../../utils/render";
import {main} from "@popperjs/core";
import Navbar from "../Navbar/Navbar";
import {getAuthenticatedUser} from "../../utils/auths";
import baseURL from "../../../config";
import Navigate from "../Router/Navigate";
import {usePathParams} from "../../utils/path-params";

const CreateStagePage = async () => {
    clearPage();
    const contact = await getOneContactById(usePathParams());
    console.log(contact)
    renderCreateStageForm(contact);

}

async function getOneContactById(contactId) {

    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'authorization': getAuthenticatedUser().token,
        },
    };

    const response = await fetch(
        `${baseURL}/contacts/getOneContactById?contactId=${contactId}`,
        options);

    if (!response.ok) {
        alert(`Une erreur est survenue avec la base de données : ${response.statusText}`)
        return null;
    }

    const contact = await response.json();

    return contact;

}

async function renderCreateStageForm(contact) {
    const main = document.querySelector('main');

    const pageTitle = document.createElement('h4');
    pageTitle.innerText =
        `Accepter le contact et créer un stage pour ${contact.entreprise.tradeName}`;
    main.appendChild(pageTitle);

    const row = document.createElement('div');
    row.className = 'row';

    const form = createFormElement();

    const allSupervisor = await getAllSupervisor(contact.entrepriseId)
    const supervisorRow = document.createElement('div');
    supervisorRow.className = 'd-flex mb-3 align-items-center'; // Flexbox with margin-bottom

    const dropdownContainer = createDropdownContainer();
    dropdownContainer.appendChild(createDropdownButton());
    // Create the new "Add Supervisor" button
    const addSupervisorButton = createAddSupervisorButton();
    dropdownContainer.appendChild(addSupervisorButton);

    const dropdownContent = createDropdownContent(allSupervisor);
    dropdownContainer.appendChild(dropdownContent);
    row.appendChild(dropdownContainer);

    form.appendChild(row);

    const inputProject = createTextInput('project', `Objectif du stage`);
    form.appendChild(inputProject);

    const dateSign = createDateInput('dateSign', 'Date de Signature')
    form.appendChild(dateSign);

    const buttonGroup = document.createElement('div');
    buttonGroup.className = 'form-group mt-3';
    buttonGroup.appendChild(createSubmitButton());
    buttonGroup.appendChild(createCancelButton());
    form.appendChild(buttonGroup);

    const container = document.createElement('div');
    container.className = 'container';
    container.appendChild(form);
    main.appendChild(container);

    let selectedSupervisorId = -1;

    dropdownContent.querySelectorAll('.dropdown-item').forEach(item => {
        item.addEventListener('click', () => {
            selectedSupervisorId = item.id;
            document.getElementById('dropbtn').textContent = item.textContent;
        });
    });
    document.getElementById('dropbtn').addEventListener('click', toggleDropdown);
    document.getElementById('myForm').addEventListener('submit', async () => {
        event.preventDefault();
        if (selectedSupervisorId === -1) {
            alert('Please select a supervisor');
            return;

        }
        const project = document.getElementById('project').value;
        const dateSign = document.getElementById('dateSign').value;
        try {
            await createStage(selectedSupervisorId, contact.id, dateSign, project,
                contact.version);
            alert('Votre stage a été créé');
            location.reload();
        } catch (error) {
            return;
        }
    });
}

function createAddSupervisorButton() {
    const addButton = document.createElement('button');
    addButton.type = 'button';
    addButton.className = 'btn btn-secondary btn-block bg-custom';
    addButton.textContent = 'Ajouter un superviseur';
    addButton.addEventListener('click', () => {
        Navigate('/addSupervisor');
    });
    return addButton;
}

async function createStage(selectedSupervisorId, contactId, dateSign, project,
                           VersionContact) {
    console.log(
        'Data pour stage- idSupervisor:' + selectedSupervisorId + ' idContact:'
        + contactId + ' dateSign:' + dateSign + ' project:' + project
        + ' version contact:' + VersionContact)

    const options = {
        method: 'PUT',
        body: JSON.stringify({
            id_supervisor: selectedSupervisorId,
            id_contact: contactId,
            signatureDate: dateSign,
            intershipProject: project,
            version: VersionContact,
        }),
        headers: {
            'Content-Type': 'application/json',
            'authorization': getAuthenticatedUser().token,
        },
    };

    try {
        const response = await fetch(`${baseURL}/contacts/accept`, options);
        if (!response.ok) {
            alert(`Une erreur est survenue lors de la création de votre stage : ${response.statusText}`);
        }
        const newContact = await response.json();
    } catch (error) {
        alert(`Une erreur est survenue lors de la création de votre stage : ${error.message}`);
        console.error('Error adding contact:', error.message);
        throw error
    }

}

async function getAllSupervisor(entrepriseId) {

    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'authorization': getAuthenticatedUser().token,
        },
    };

    const response = await fetch(
        `${baseURL}/supervisor/getAllForOneEnterprise?entrepriseId=${entrepriseId}`,
        options);

    if (!response.ok) {
        alert(`Une erreur est survenue avec la base de données : ${response.statusText}`)
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
    dropdownButton.textContent = 'Sélectionnez votre maitre de stage';
    return dropdownButton;
}

function createDropdownContent(supervisors) {
    const dropdownContent = document.createElement('div');
    dropdownContent.id = 'myDropdown';
    dropdownContent.className = 'dropdown-menu';

    if (supervisors.length > 0) {

        supervisors.forEach(supervisor => {
            const option = document.createElement('button');
            option.className = 'dropdown-item';
            option.type = 'button';
            option.textContent = supervisor.lastName + ' ' + supervisor.firstName;
            option.id = supervisor.supervisorId;
            dropdownContent.appendChild(option);
        });
    } else {

        const message = document.createElement('span');
        message.className = 'dropdown-item';
        message.textContent = 'Aucun maitre de stage disponible pour cette entreprise.';
        dropdownContent.appendChild(message);

    }

    return dropdownContent;
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
        location.reload()
    });
    return cancelButton;
}

function toggleDropdown() {
    const dropdownContent = document.getElementById('myDropdown');
    // eslint-disable-next-line no-unused-expressions
    dropdownContent.style.display === 'block'
        ? dropdownContent.style.display = 'none'
        : dropdownContent.style.display = 'block';
}

export default CreateStagePage;
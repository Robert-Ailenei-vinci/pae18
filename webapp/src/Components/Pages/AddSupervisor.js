import { clearPage, renderPageTitle } from '../../utils/render';
const AddSupervisorPage = () => {
    clearPage();
    renderPageTitle('Add supervisor');
    createForm();
};

async function createForm() {
    const form = createFormElement();

    const lastNameInput = createInputField('last_name', 'Last Name', 'text', true);
    form.appendChild(lastNameInput);

    const firstNameInput = createInputField('first_name', 'First Name', 'text', true);
    form.appendChild(firstNameInput);

    const entrepriseDropdown = await createDropdown('entreprise', 'Enterprise', await fetchEnterprises(), true);
    form.appendChild(entrepriseDropdown);

    const phoneNumberInput = createInputField('phone_number', 'Phone Number', 'text', true);
    form.appendChild(phoneNumberInput);

    const emailInput = createInputField('email', 'Email', 'email', false);
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

    document.getElementById('mybtn').addEventListener('submit', (event) => {
        event.preventDefault();
        const lastName = document.getElementById('trade_name').value;
        const firstName = document.getElementById('designation').value;
        const entreprise = document.getElementById('address').value;
        const phoneNum = document.getElementById('phone_num').value;
        const email = document.getElementById('email').value;        
        //handle submit et fetch                                                                           
    })
}

function createFormElement() {
    const form = document.createElement('form');
    form.id = 'addSupervisorForm';
    return form;
}

function createInputField(id, label, type, required) {
    const div = document.createElement('div');
    div.className = 'form-group';

    const labelElement = document.createElement('label');
    labelElement.for = id;
    labelElement.innerText = label;
    div.appendChild(labelElement);

    const inputElement = document.createElement('input');
    inputElement.id = id;
    inputElement.name = id;
    inputElement.type = type;
    if (required) {
        inputElement.required = true;
    }
    div.appendChild(inputElement);

    return div;
}

async function createDropdown(id, label, options, required) {
    const div = document.createElement('div');
    div.className = 'form-group';

    const labelElement = document.createElement('label');
    labelElement.for = id;
    labelElement.innerText = label;
    div.appendChild(labelElement);

    const selectElement = document.createElement('select');
    selectElement.id = id;
    selectElement.name = id;
    if (required) {
        selectElement.required = true;
    }

    const defaultOption = document.createElement('option');
    defaultOption.value = '';
    defaultOption.innerText = 'Select ' + label;
    selectElement.appendChild(defaultOption);

    options.forEach(option => {
        const optionElement = document.createElement('option');
        optionElement.value = option.id;
        optionElement.innerText = option.name;
        selectElement.appendChild(optionElement);
    });

    div.appendChild(selectElement);

    return div;
}

async function fetchEnterprises() {
    // Perform your fetch operation here to get enterprises from the server
    // This function should return an array of enterprise objects
    // For demonstration purposes, I'll return a sample array
    return [
        { id: 1, name: 'Enterprise 1' },
        { id: 2, name: 'Enterprise 2' },
        { id: 3, name: 'Enterprise 3' }
    ];
}

function createSubmitButton() {
    const submitButton = document.createElement('button');
    submitButton.className = 'btn btn-primary mr-2';
    submitButton.type = 'submit';
    submitButton.innerText = 'Add Supervisor';
    submitButton.id = 'mybtn';
    return submitButton;
}

function createCancelButton() {
    const cancelButton = document.createElement('button');
    cancelButton.className = 'btn btn-secondary';
    cancelButton.type = 'button';
    cancelButton.innerText = 'Cancel';
    return cancelButton;
}

createForm();

export default AddSupervisorPage;
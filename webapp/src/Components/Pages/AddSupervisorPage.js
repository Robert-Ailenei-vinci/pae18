import { clearPage, renderPageTitle } from '../../utils/render';
import { getAuthenticatedUser } from '../../utils/auths';
import baseURL from '../../../config';

const AddSupervisorPage = async () => {
    clearPage();
    renderPageTitle('Ajouter un superviseur');
    await createForm();
};

async function createForm() {
    const form = createFormElement();

    form.appendChild(createInputField('last_name', 'Nom', 'text', true));
    form.appendChild(createInputField('first_name', 'Prénom', 'text', true));
    
    const entreprises = await fetchEnterprises();
    form.appendChild(await createDropdown('entreprise', 'Enterprise', entreprises, true));
    
    form.appendChild(createInputField('phone_number', 'N°telephone', 'text', true));
    form.appendChild(createInputField('email', 'Email', 'email', false));

    // Horizontal alignment for buttons
    const buttonGroup = document.createElement('div');
    buttonGroup.className = 'd-flex justify-content-between mt-3';
    buttonGroup.appendChild(createSubmitButton());
    buttonGroup.appendChild(createCancelButton());
    form.appendChild(buttonGroup);

    const container = document.createElement('div');
    container.className = 'container mt-4'; // Add some top margin for spacing
    container.appendChild(form);
    
    const main = document.querySelector('main');
    main.appendChild(container);

    document.getElementById('addSupervisorForm').addEventListener('submit', async (event) => {
        event.preventDefault(); // Prevent default form submission behavior
        await handleFormSubmit();
    });
}

// Create the form container
function createFormElement() {
    const form = document.createElement('form');
    form.id = 'addSupervisorForm';
    form.className = 'form-container'; // Adding class for potential styling
    return form;
}

// Create an input field with a label
function createInputField(id, label, type, required) {
    const div = document.createElement('div');
    div.className = 'form-group';

    const labelElement = document.createElement('label');
    labelElement.htmlFor = id; // Use `htmlFor` instead of `for`
    labelElement.innerText = label;
    div.appendChild(labelElement);

    const inputElement = document.createElement('input');
    inputElement.id = id;
    inputElement.name = id;
    inputElement.type = type;
    inputElement.className = 'form-control'; // Bootstrap form-control for styling
    if (required) {
        inputElement.required = true;
    }
    div.appendChild(inputElement);

    return div;
}

// Create a dropdown with a label
async function createDropdown(id, label, options, required) {
    const div = document.createElement('div');
    div.className = 'form-group';

    const labelElement = document.createElement('label');
    labelElement.htmlFor = id; // Use `htmlFor`
    labelElement.innerText = label;
    div.appendChild(labelElement);

    const selectElement = document.createElement('select');
    selectElement.id = id;
    selectElement.name = id;
    selectElement.className = 'form-control'; // Bootstrap form-control
    if (required) {
        selectElement.required = true;
    }

    const defaultOption = document.createElement('option');
    defaultOption.value = '';
    defaultOption.innerText = 'Selectionner une  ' + label;
    selectElement.appendChild(defaultOption);

    options.forEach(option => {
        const optionElement = document.createElement('option');
        optionElement.value = option.id;
        optionElement.innerText = `${option.tradeName}`;
        selectElement.appendChild(optionElement);
    });

    div.appendChild(selectElement);

    return div;
}

// Function to handle form submission
async function handleFormSubmit() {
    const lastName = document.getElementById('last_name').value;
    const firstName = document.getElementById('first_name').value;
    const entreprise = document.getElementById('entreprise').value;
    const phoneNum = document.getElementById('phone_number').value;
    const email = document.getElementById('email').value;

    if (!lastName || !firstName || !entreprise || !phoneNum) {
        alert('All required fields must be filled.', 'error'); // Show a toast message
        return;
    }

    const options = {
        method: 'POST',
        body: JSON.stringify({
            lastName,
            firstName,
            entreprise,
            phoneNum,
            email,
            user: getAuthenticatedUser().id,
        }),
        headers: {
            'Content-Type': 'application/json',
            'authorization': getAuthenticatedUser().token,
        },
    };

    try {
        const response = await fetch(`${baseURL}/supervisor/addSupervisor`, options);
        if (!response.ok) {
            throw new Error(`Failed to add supervisor: ${response.statusText}`);
        }
        
        await response.json();
        alert(`Supervisor ${firstName} ${lastName} added successfully!`, 'success'); // Show success message
        history.back();
        // Redirect to precedent page
    } catch (error) {
        alert(`Error adding supervisor: ${error.message}`, 'error'); // Show error message
        console.error('Error adding supervisor:', error);
    }
}

// Create a submit button
function createSubmitButton() {
    const submitButton = document.createElement('button');
    submitButton.className = 'btn btn-primary mr-2';
    submitButton.type = 'submit';
    submitButton.innerText = 'Ajouter un superviseur';
    submitButton.id = 'mybtn';
    return submitButton;
}

// Create a cancel button
function createCancelButton() {
    const cancelButton = document.createElement('button');
    cancelButton.className = 'btn btn-secondary';
    cancelButton.type = 'button';
    cancelButton.innerText = 'Annulée';
    cancelButton.addEventListener('click', () => {
        history.back();
    });
    return cancelButton;
}

// Function to fetch enterprises
async function fetchEnterprises() {
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'authorization': getAuthenticatedUser().token,
        },
    };

    const response = await fetch(
        `${baseURL}/entreprise/getAll`,
        options
    );

    if (!response.ok) {
        alert(`Error fetching enterprises: ${response.statusText}`, 'error'); // Show error message
        return [];
    }

    const entreprises = await response.json();
    return entreprises;
}

export default AddSupervisorPage;

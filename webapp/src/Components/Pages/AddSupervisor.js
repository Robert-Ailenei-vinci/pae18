import { clearPage, renderPageTitle } from '../../utils/render';
import { getAuthenticatedUser } from '../../utils/auths';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';
import baseURL from '../../../config';

const AddSupervisorPage = async () => {
    clearPage();
    renderPageTitle('Add supervisor');
    await createForm();
};

async function createForm() {
    const form = createFormElement();

    const lastNameInput = createInputField('last_name', 'Last Name', 'text', true);
    form.appendChild(lastNameInput);

    const firstNameInput = createInputField('first_name', 'First Name', 'text', true);
    form.appendChild(firstNameInput);

    const entrepriseDropdown = await createDropdown('entreprise', 'Enterprise', fetchEnterprises(), true);
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
        fetchSupervisor(lastName, firstName, entreprise, phoneNum, email);                                                                           
    });
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

/**async function fetchEnterprises() {
    try {
        const token = getAuthenticatedUser().token;
        const response = await fetch(`${baseURL}/entreprise/getAll`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            throw new Error(`Failed to fetch enterprises: ${response.statusText}`);
        }

        const enterprises = await response.json();
        return enterprises;
    } catch (error) {
        console.error('Error fetching enterprises:', error.message);
        // Handle the error gracefully, such as displaying an error message to the user
        return []; // Return an empty array as a fallback
    }
}*/

function fetchEnterprises() {
    return [
        {
            trade_name: "Entreprise 1",
            designation: "Designation 1",
            address: "Address 1",
            phone_num: "1234567890",
            email: "enterprise1@example.com",
            blacklisted: false,
            reason_blacklist: null
        },
        {
            trade_name: "Entreprise 2",
            designation: "Designation 2",
            address: "Address 2",
            phone_num: "0987654321",
            email: "enterprise2@example.com",
            blacklisted: false,
            reason_blacklist: null
        },
        {
            trade_name: "Entreprise 3",
            designation: "Designation 3",
            address: "Address 3",
            phone_num: "5555555555",
            email: "enterprise3@example.com",
            blacklisted: true,
            reason_blacklist: "Raison de la mise en liste noire de l'entreprise 3"
        }
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

async function fetchSupervisor(lastName, firstName, entreprise, phoneNum, email) {
    if (!lastName || !firstName || !email || !entreprise || !phoneNum) {
      alert('All rows are required.');
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
      const response = await fetch(`${baseURL}/supervisor/addOne`, options);
      if (!response.ok) {
        throw new Error(`Failed to add supervisor: ${response.statusText}`);
      }
      const newSupervisor = await response.json();
      alert(`Added supervisor : ${JSON.stringify(newSupervisor)}`);
      Navbar();
      Navigate('/addsupervisor');
    } catch (error) {
      console.error('Error adding supervisor:', error.message);
      alert('An error occurred while adding the supervisor. Please try again later.');
    }
  }

createForm();

export default AddSupervisorPage;
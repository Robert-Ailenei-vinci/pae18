import { clearPage, renderPageTitle } from '../../utils/render';
import { getAuthenticatedUser } from '../../utils/auths';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';
import baseURL from '../../../config';

const AddSupervisorPage = async () => {
    clearPage();
    renderPageTitle('Ajouter un maître de stage');
    await createSupervisorForm(); // Créer et ajouter les composants au DOM
};

// Fonction qui crée le formulaire et l'ajoute à la page
async function createSupervisorForm() {
    const form = createFormElement();

    // Ajouter les champs de saisie
    form.appendChild(createInputField('last_name', 'Nom de famille', 'text', true));
    form.appendChild(createInputField('first_name', 'Prénom', 'text', true));
    
    // Créer un menu déroulant avec les entreprises
    const entreprises = await fetchEnterprises();
    form.appendChild(await createDropdown('entreprise', 'Entreprise', entreprises, true));
    
    // Ajouter des champs supplémentaires
    form.appendChild(createInputField('phone_number', 'Numéro de téléphone', 'text', true));
    form.appendChild(createInputField('email', 'Email', 'email', false));

    // Ajouter un groupe de boutons (soumission et annulation)
    const buttonGroup = document.createElement('div');
    buttonGroup.className = 'd-flex justify-content-between mt-3';
    buttonGroup.appendChild(createSubmitButton());
    buttonGroup.appendChild(createCancelButton());
    form.appendChild(buttonGroup);

    // Ajouter le formulaire à un conteneur et l'insérer dans la page
    const container = document.createElement('div');
    container.className = 'container mt-4'; // Ajouter un espacement
    container.appendChild(form);

    const main = document.querySelector('main'); // Obtenir le conteneur principal
    main.appendChild(container);

    // Ajouter un écouteur d'événements pour gérer la soumission du formulaire
    form.addEventListener('submit', async (event) => {
        event.preventDefault(); // Empêcher le comportement par défaut du formulaire
        await handleFormSubmit(); // Appeler la fonction de soumission
    });
}

// Définition des composants du formulaire
function createFormElement() {
    const form = document.createElement('form');
    form.id = 'addSupervisorForm';
    form.className = 'form-container'; // Classe pour le styling
    return form;
}

function createInputField(id, label, type, required) {
    const div = document.createElement('div');
    div.className = 'form-group';

    const labelElement = document.createElement('label');
    labelElement.htmlFor = id; // Utiliser `htmlFor`
    labelElement.innerText = label;
    div.appendChild(labelElement);

    const inputElement = document.createElement('input');
    inputElement.id = id;
    inputElement.name = id;
    inputElement.type = type;
    inputElement.className = 'form-control'; // Utiliser Bootstrap form-control
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
    labelElement.htmlFor = id;
    labelElement.innerText = label;
    div.appendChild(labelElement);

    const selectElement = document.createElement('select');
    selectElement.id = id;
    selectElement.name = id;
    selectElement.className = 'form-control';
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
        optionElement.innerText = `${option.tradeName}`;
        selectElement.appendChild(optionElement);
    });

    div.appendChild(selectElement);

    return div;
}

function createSubmitButton() {
    const submitButton = document.createElement('button');
    submitButton.className = 'btn btn-primary bg-custom';
    submitButton.type = 'submit';
    submitButton.innerText = 'Add Supervisor';
    submitButton.id = 'mybtn';
    return submitButton;
}

function createCancelButton() {
    const cancelButton = document.createElement('button');
    cancelButton.className = 'btn btn-primary bg-custom';
    cancelButton.type = 'button';
    cancelButton.innerText = 'Cancel';
    cancelButton.addEventListener('click', () => {
        Navbar();
        Navigate('/supervisors'); // Naviguer vers la page des superviseurs en cas d'annulation
    });
    return cancelButton;
}

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
        throw new Error(`Error fetching enterprises: ${response.statusText}`);
    }

    const entreprises = await response.json();
    return entreprises;
}

async function handleFormSubmit() {
    const lastName = document.getElementById('last_name').value;
    const firstName = document.getElementById('first_name').value;
    const entreprise = document.getElementById('entreprise').value;
    const phoneNum = document.getElementById('phone_number').value;
    const email = document.getElementById('email').value;

    const main = document.querySelector('main'); 

    if (!lastName || !firstName || !entreprise || !phoneNum) {
        main.appendChild(createAlert('All required fields must be filled.', 'warning')); // Alerte Bootstrap
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
        main.appendChild(createAlert(`Supervisor ${firstName} ${lastName} added successfully!`, 'success'));
        Navbar(); 
        Navigate('/users/userData'); 
    } catch (error) {
        main.appendChild(createAlert(`Error adding supervisor: ${error.message}`, 'danger'));
        console.error('Error adding supervisor:', error);
    }
}

function createAlert(message, type) {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
    alertDiv.role = 'alert';
    alertDiv.innerText = message;

    const closeButton = document.createElement('button');
    closeButton.type = 'button';
    closeButton.className = 'close';
    closeButton.innerHTML = '&times;'; // Icône de fermeture

    alertDiv.appendChild(closeButton);
    return alertDiv;
}

export default AddSupervisorPage;

/* eslint-disable spaced-comment */
/* eslint-disable no-unused-vars */
/* eslint-disable no-undef */
import {
  getAuthenticatedUser
} from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navigate from '../Router/Navigate';
import {
  meetContact,
  refuseContact,
  stopFollowContact
} from "./utils/ChangeState";

const UserDataPage = () => {
  clearPage();
  renderPageTitle('Données Personelles');
  renderPersonnalInfoPage();
};

async function fetchContactsData(user) {
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `${user.token}`,
    },
  };

  try {
    const responseContacts = await fetch(
        `http://localhost:3000/contacts/allContactsByUserId`, options);

    if (!responseContacts.ok) {
      throw new Error(
          `Failed to fetch contacts: ${responseContacts.statusText}`);
    }

    const contactsData = await responseContacts.json();
    return contactsData;
  } catch (error) {
    throw new Error(
        `An error occurred while fetching contacts: ${error.message}`);
  }
}

async function fetchStageData(user) {
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `${user.token}`,
    },
  };

  try {
    const responseStage = await fetch(
        `http://localhost:3000/stages/stageByUserId`, options);

    if (responseStage == null) {
      return null;
    }

    if (!responseStage.ok) {
      throw new Error(`Failed to fetch stages: ${responseStage.statusText}`);
    }

    const stageData = await responseStage.json();
    return stageData;
  } catch (error) {
    // Returning undefined instead of throwing an error if fetch operation fails
    return undefined;
  }
}

let isRendering = false;

async function renderPersonnalInfoPage() {
  if (isRendering) {
    return;
  }

  const user = getAuthenticatedUser();
  const main = document.querySelector('main');
  renderUserData(user, main);
}

async function renderUserData(user, parentElement) {
  const ul = document.createElement('ul');
  ul.className = 'p-5';

  isRendering = true;
  const contactsData = await fetchContactsData(user);
  const stageData = await fetchStageData(user);
  isRendering = false;

  console.log('Contacts : ', contactsData);
  console.log(user);

  renderUserDetails(user, ul);
  renderChangeUserDataButton(parentElement);
  parentElement.appendChild(ul);

  // Render contact part only for "etudiant" role
  if (user.role === "etudiant") {
    renderContactsTable(parentElement, contactsData);
  }

  // Render stage table if stageData is available
  if (stageData === "etudiant") {
    renderStageTable(parentElement, stageData);
  }
}

function renderUserDetails(user, parentElement) {
let temp_role = user.role;

if (user.role === "etudiant") {
  temp_role = "étudiant";
}

const items = [
  { label: 'Nom de famille: ', value: user.lastName },
  { label: 'Prénom: ', value: user.firstName },
  { label: 'Email: ', value: user.email },
  { label: 'Numéro de Téléphone: ', value: user.phoneNum },
  { label: 'Date d\'enregistrement: ', value: user.registrationDate },
  { label: 'Année académique: ', value: user.schoolYear },
  { label: 'Role: ', value: temp_role } 
];


  const ul = parentElement;
  items.forEach(item => {
    const li = document.createElement('li');
    li.textContent = item.label + item.value;
    ul.appendChild(li);
  });
}

function renderChangeUserDataButton(parentElement) {
  const submit = document.createElement('input');
  submit.value = 'Changer mes données personelles';
  submit.type = 'button';
  submit.className = 'btn btn-info';
  submit.addEventListener('click', () => {
    Navigate('/users/changeData');
  });

  parentElement.appendChild(submit);
}

function renderContactsTable(parentElement, contactsData) {
  // Create a title for the contacts table
  const contactsTitle = document.createElement('h2');
  contactsTitle.textContent = 'Contacts';
  parentElement.appendChild(contactsTitle);
  
  // Create the table element
  const table = document.createElement('table');
  table.className = 'table';

  // Create the table header
  const thead = document.createElement('thead');
  const trHead = document.createElement('tr');
  ['Entreprise', 'Appelation', 'Adresse', 'Mail', 'N°Telephone', 'Etat', ' ',
    'Lieu/Type de rencontre', 'Raison de refus'].forEach(text => {
    const th = document.createElement('th');
    th.textContent = text;
    trHead.appendChild(th);
  });
  thead.appendChild(trHead);
  table.appendChild(thead);

  // Create the table body
  const tbody = document.createElement('tbody');
  contactsData.forEach(contact => {
    const tr = document.createElement('tr');

    // Fields from entreprise object
    ['tradeName', 'designation', 'address', 'email', 'phoneNumber'].forEach(
        key => {
          const td = document.createElement('td');
          td.textContent = contact.entreprise[key] || '-';
          tr.appendChild(td);
        });

    // État
    const tdState = document.createElement('td');
    tdState.textContent = contact.state || '-';
    tr.appendChild(tdState);

    // Bouton pour changer l'état
    const tdButton = document.createElement('td');
    const button = document.createElement('button');
    button.textContent = 'Changer état';
    button.className = 'btn btn-primary';
    button.setAttribute('type', 'button');
    button.setAttribute('data-bs-toggle', 'collapse');
    button.setAttribute('data-bs-target', '#collapseExample_' + contact.id);
    button.setAttribute('aria-expanded', 'false');
    button.setAttribute('aria-controls', 'collapseExample_' + contact.id);

    const divForm = document.createElement('div');
    divForm.className = 'collapse';
    divForm.id = 'collapseExample_' + contact.id;

    if (contact.state === "initié") {
      // Création du formulaire
      const form = document.createElement('form');
      const select = document.createElement('select');
      select.className = 'form-select'; // Ajoutez des classes Bootstrap si nécessaire
      ['Rencontré', 'Suivi stoppé'].forEach(optionText => {
        const option = document.createElement('option');
        option.value = optionText;
        option.textContent = optionText;
        select.appendChild(option);
      });
      form.appendChild(select);

// Création de la zone de texte pour la raison ou le lieu
      const extraInput = document.createElement('input');
      extraInput.type = 'text';
      extraInput.placeholder = 'Lieu';
      extraInput.style.display = 'block'; // Par défaut, cachez la zone de texte

// Gérer l'affichage de la zone de texte en fonction de la sélection
      select.addEventListener('change', () => {
        if (select.value === 'Rencontré') {
          extraInput.style.display = 'block'; // Afficher la zone de texte si "Rencontré" ou "Refusé" est sélectionné
        } else {
          extraInput.style.display = 'none'; // Masquer la zone de texte pour les autres options
        }
      });

// Création du bouton de soumission
      const submitButton = document.createElement('button');
      submitButton.textContent = 'Soumettre';
      submitButton.className = 'btn btn-primary';
      submitButton.type = 'submit'; // Définir le type sur "submit" pour soumettre le formulaire

// Ajout d'un écouteur d'événements pour gérer la soumission du formulaire
      form.addEventListener('submit', (event) => {
        event.preventDefault(); // Empêcher le comportement par défaut du formulaire

        const selectedOption = select.value;
        let additionalInfo = ''; // Informations supplémentaires à envoyer avec la soumission

        // Si "Rencontré" ou "Refusé" est sélectionné, récupérez le contenu de la zone de texte
        if (selectedOption === 'Rencontré') {
          additionalInfo = extraInput.value;
        }

        // Passer les informations supplémentaires à la fonction appropriée
        switch (selectedOption) {
          case 'Rencontré':
            meetContact(contact.id, additionalInfo,contact.version);
            window.location.reload();
            break;
          case 'Suivi stoppé':
            stopFollowContact(contact.id,contact.version);
            window.location.reload();

            break;
          default:
            // Action par défaut ou erreur
            alert("Sélectionnez un état");
        }

        // Fermer le collapsible après la soumission
        const collapse = document.getElementById(
            'collapseExample_' + contact.id);
        const bsCollapse = new bootstrap.Collapse(collapse);
        bsCollapse.hide();
      });

// Ajout des éléments au formulaire
      form.appendChild(extraInput);
      form.appendChild(submitButton);

// Ajout du formulaire au divForm
      divForm.appendChild(form);

      tdButton.appendChild(button);
      tdButton.appendChild(divForm);
      tr.appendChild(tdButton);
    }

    if (contact.state === "rencontré") {
      // Création du formulaire
      const form = document.createElement('form');
      const select = document.createElement('select');
      select.className = 'form-select'; // Ajoutez des classes Bootstrap si nécessaire
      ['Refusé', 'Suivi stoppé'].forEach(optionText => {
        const option = document.createElement('option');
        option.value = optionText;
        option.textContent = optionText;
        select.appendChild(option);
      });
      form.appendChild(select);

// Création de la zone de texte pour la raison ou le lieu
      const extraInput = document.createElement('input');
      extraInput.type = 'text';
      extraInput.placeholder = 'Raison';
      extraInput.style.display = 'block'; // Par défaut, cachez la zone de texte

// Gérer l'affichage de la zone de texte en fonction de la sélection
      select.addEventListener('change', () => {
        if (select.value === 'Refusé') {
          extraInput.style.display = 'block'; // Afficher la zone de texte si "Rencontré" ou "Refusé" est sélectionné
        } else {
          extraInput.style.display = 'none'; // Masquer la zone de texte pour les autres options
        }
      });

// Création du bouton de soumission
      const submitButton = document.createElement('button');
      submitButton.textContent = 'Soumettre';
      submitButton.className = 'btn btn-primary';
      submitButton.type = 'submit'; // Définir le type sur "submit" pour soumettre le formulaire

// Ajout d'un écouteur d'événements pour gérer la soumission du formulaire
      form.addEventListener('submit', (event) => {
        event.preventDefault(); // Empêcher le comportement par défaut du formulaire

        const selectedOption = select.value;
        let additionalInfo = ''; // Informations supplémentaires à envoyer avec la soumission

        // Si "Rencontré" ou "Refusé" est sélectionné, récupérez le contenu de la zone de texte
        if (selectedOption === 'Refusé') {
          additionalInfo = extraInput.value;
        }

        // Passer les informations supplémentaires à la fonction appropriée
        switch (selectedOption) {
          case 'Suivi stoppé':
            stopFollowContact(contact.id,contact.version);
            window.location.reload();

            break;
          case 'Refusé':
            refuseContact(contact.id, additionalInfo,contact.version);
            window.location.reload();

            break;
          default:
            // Action par défaut ou erreur
            alert("Sélectionnez un état");
        }

        // Fermer le collapsible après la soumission
        const collapse = document.getElementById(
            'collapseExample_' + contact.id);
        const bsCollapse = new bootstrap.Collapse(collapse);
        bsCollapse.hide();
      });

// Ajout des éléments au formulaire
      form.appendChild(extraInput);
      form.appendChild(submitButton);

// Ajout du formulaire au divForm
      divForm.appendChild(form);

      tdButton.appendChild(button);
      tdButton.appendChild(divForm);
      tr.appendChild(tdButton);
    }

    if (contact.state === "suspendu" || contact.state === "refusé"){
      const tdVide = document.createElement('td');
      tdVide.textContent = '-';
      tr.appendChild(tdVide);
    }

    // Lieu/Type de rencontre
    const tdMeeting = document.createElement('td');
    tdMeeting.textContent = contact.meetingType || '-';
    tr.appendChild(tdMeeting);

    // Raison de refus
    const tdReason = document.createElement('td');
    tdReason.textContent = contact.reasonForRefusal || '-';
    tr.appendChild(tdReason);

    tbody.appendChild(tr);
  });

  table.appendChild(tbody);

  // Append the table to the parent element
  parentElement.appendChild(table);
}

function renderStageTable(parentElement, stageData) {
  // Create a title for the stage table
  const stageTitle = document.createElement('h2');
  stageTitle.textContent = 'Stage';
  parentElement.appendChild(stageTitle);

  // Create the table element
  const stageTable = document.createElement('table');
  stageTable.className = 'table';

  // Create the table header
  const stageThead = document.createElement('thead');
  const stageTrHead = document.createElement('tr');
  ['Entreprise', 'Appelation', 'Mail', 'N°Téléphone', 'Type de rencontre'].forEach(text => {
    const th = document.createElement('th');
    th.textContent = text;
    stageTrHead.appendChild(th);
  });
  stageThead.appendChild(stageTrHead);
  stageTable.appendChild(stageThead);

  // Create the table body
  const stageTbody = document.createElement('tbody');
  const tr = document.createElement('tr');
  ['tradeName', 'designation', 'email', 'phoneNumber'].forEach(key => {
    const td = document.createElement('td');
    if (stageData) {
      td.textContent = stageData.contact.entreprise[key] || '-';
    }
    tr.appendChild(td);
  });

  const td = document.createElement('td');
  if (stageData) {
    td.textContent = stageData.contact.meetingType || '-';
  }
  tr.appendChild(td);

  stageTbody.appendChild(tr);
  stageTable.appendChild(stageTbody);

  // Append the table to the parent element
  parentElement.appendChild(stageTable);
}


export default UserDataPage;

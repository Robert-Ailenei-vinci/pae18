/* eslint-disable spaced-comment */
/* eslint-disable no-unused-vars */
import {
  getRememberMe,
  setAuthenticatedUser,
  setRememberMe,
  getAuthenticatedUser
} from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';

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

    if (!responseStage.ok) {
      throw new Error(`Failed to fetch stages: ${responseStage.statusText}`);
    }

    const stageData = await responseStage.json();
    return stageData;
  } catch (error) {
    throw new Error(
      `An error occurred while fetching stage data: ${error.message}`);
  }
}

let isRendering = false;

async function renderPersonnalInfoPage() {

  if (isRendering) {
    // Si le rendu est en cours, ignorer l'appel de la fonction
    return;
  }

  const user = getAuthenticatedUser();
  const main = document.querySelector('main');
  const ul = document.createElement('ul');
  ul.className = 'p-5';

  isRendering = true; // Marquer le rendu comme en cours
  const contactsData = await fetchContactsData(user);
  const stageData = await fetchStageData(user);
  isRendering = false; // Marquer le rendu comme terminé

  console.log('Contacts : ', contactsData);
  console.log(user);
  const items = [
    { label: 'Nom de famille: ', value: user.lastName },
    { label: 'Prénom: ', value: user.firstName },
    { label: 'Email: ', value: user.email },
    { label: 'Numéro de Téléphone: ', value: user.phoneNum },
    { label: 'Date d\'enregistrement: ', value: user.registrationDate },
    { label: 'Année académique: ', value: user.schoolYear },
    { label: 'Role: ', value: user.role }
  ];

  items.forEach(item => {
    const li = document.createElement('li');
    li.textContent = item.label + item.value;
    ul.appendChild(li);
  });

  const submit = document.createElement('input');
  submit.value = 'Changer mes données personelles';
  submit.type = 'button';
  submit.className = 'btn btn-info';
  submit.addEventListener('click', () => {
    Navigate('/users/changeData');
  });

  const table = document.createElement('table');
  table.className = 'table';
  const thead = document.createElement('thead');
  const tbody = document.createElement('tbody');
  const trHead = document.createElement('tr');

  ['Entreprise', 'Appelation', 'Adresse', 'Mail', 'N°Telephone', 'État', '‎ ', 'Lieu/Type de rencontre', 'Raison de refus'].forEach(text => {
    const th = document.createElement('th');
    th.textContent = text;
    trHead.appendChild(th);
});

thead.appendChild(trHead);
table.appendChild(thead);

contactsData.forEach(contact => {
    const tr = document.createElement('tr');

    ['tradeName', 'designation', 'address', 'email', 'phoneNumber'].forEach(
        key => {
            const td = document.createElement('td');
            td.textContent = contact.entreprise[key] || '-';
            tr.appendChild(td);
        });

    const stateTd = document.createElement('td');

    const stateText = document.createTextNode(contact.state || '-');
    stateTd.appendChild(stateText);

    const stateSelect = document.createElement('select');
    const stateOptions = ['Option 1', 'Option 2', 'Option 3'];    // changer ici pr mettre les états 
    stateOptions.forEach(optionText => {
        const option = document.createElement('option');
        option.value = optionText;
        option.textContent = optionText;
        stateSelect.appendChild(option);
    });

    stateSelect.value = contact.state || '-';

    stateSelect.addEventListener('change', (event) => {
        stateText.textContent = event.target.value; 
    });

    stateTd.appendChild(stateSelect);
    tr.appendChild(stateTd);

    const changeTd = document.createElement('td');
    changeTd.appendChild(stateSelect);
    tr.appendChild(changeTd); 

    ['meetingType', 'reasonForRefusal'].forEach(key => {
        const td = document.createElement('td');
        td.textContent = contact[key] || '-';
        tr.appendChild(td);
    });

    tbody.appendChild(tr);
});

table.appendChild(tbody);


  const stageTable = document.createElement('table');
  stageTable.className = 'table';
  const stageThead = document.createElement('thead');
  const stageTbody = document.createElement('tbody');
  const stageTrHead = document.createElement('tr');

  ['Entreprise', 'Appelation', 'Mail', 'N°Téléphone',
    'Type de rencontre'].forEach(text => {
      const th = document.createElement('th');
      th.textContent = text;
      stageTrHead.appendChild(th);
    });

  stageThead.appendChild(stageTrHead);
  stageTable.appendChild(stageThead);

  const tr = document.createElement('tr');

  ['tradeName', 'designation', 'email', 'phoneNumber'].forEach(key => {
    const td = document.createElement('td');
    td.textContent = stageData.contact.entreprise[key] || '-';
    tr.appendChild(td);
  });

  const td = document.createElement('td');
  td.textContent = stageData.contact.meetingType || '-';
  tr.appendChild(td);

  stageTbody.appendChild(tr);
  stageTable.appendChild(stageTbody);

  main.appendChild(ul);
  main.appendChild(submit);

  // Create and append a div to act as a spacer
  const spacer = document.createElement('div');
  spacer.style.height = '20px'; // Adjust the height as needed
  main.appendChild(spacer);

  // Create and append the title for the contacts table
  const contactsTitle = document.createElement('h2');
  contactsTitle.textContent = 'Contacts';
  main.appendChild(contactsTitle);
  main.appendChild(table);

  // Create and append the title for the stage table
  const stageTitle = document.createElement('h2');
  stageTitle.textContent = 'Stage';
  main.appendChild(stageTitle);
  main.appendChild(stageTable);

}

export default UserDataPage;
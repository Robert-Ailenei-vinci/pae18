/* eslint-disable no-self-assign */
/* eslint-disable spaced-comment */
/* eslint-disable no-unused-vars */
/* eslint-disable no-undef */
import changeInternshipSubject from '../Pages/utils/ChangeInternship';
import {
  getRememberMe,
  setAuthenticatedUser,
  setRememberMe,
  getAuthenticatedUser
} from '../../utils/auths';
import {clearPage, renderPageTitle} from '../../utils/render';
import Navbar from '../Navbar/Navbar';

import Navigate from '../Router/Navigate';
import {
  meetContact,
  refuseContact,
  stopFollowContact
} from "./utils/ChangeState";
import baseURL from '../../../config';
import CreateStagePage from "./CreateStagePage";

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
        `${baseURL}/contacts/allContactsByUserId`, options);

    if (!responseContacts.ok) {
      throw new Error(
          `Une erreur est survenue lors de la recherche des contacts : ${responseContacts.statusText}`);
    }

    const contactsData = await responseContacts.json();
    return contactsData;
  } catch (error) {
    console.log(error);
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
        `${baseURL}/stages/stageByUserId`, options);

    if (responseStage == null) {
      return null;
    }

    if (!responseStage.ok) {
      throw new Error(
          `Une erreur est survenue lors de la recherche des stages : ${responseStage.statusText}`);
    }

    const stageData = await responseStage.json();

    return stageData;
  } catch (error) {
    console.log(error);
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
  console.log('StageData: ', stageData)
  const items = [
    {label: 'Nom de famille: ', value: user.lastName},
    {label: 'Prénom: ', value: user.firstName},
    {label: 'Email: ', value: user.email},
    {label: 'Numéro de Téléphone: ', value: user.phoneNum},
    {label: 'Date d\'enregistrement: ', value: user.registrationDate},
    {label: 'Année académique: ', value: user.schoolYear},
    {label: 'Rôle: ', value: user.role}
  ];

  items.forEach(item => {
    const li = document.createElement('li');
    li.textContent = item.label + item.value;
    ul.appendChild(li);
  });

  const submit = document.createElement('input');
  submit.value = 'Changer mes données personelles';
  submit.type = 'button';
  submit.className = 'btn btn-info bg-custom';
  submit.addEventListener('click', () => {
    Navigate('/users/changeData');
  });

  // Creating table for contacts
  const table = document.createElement('table');
  table.className = 'table table-bordered table-striped';
  const thead = document.createElement('thead');
  const tbody = document.createElement('tbody');
  const trHead = document.createElement('tr');

  ['Entreprise', 'Appellation', 'Adresse', 'Mail', 'N°Téléphone', 'État', ' ',
    'Lieu/Type de rencontre', 'Raison de refus'].forEach(text => {
    const th = document.createElement('th');
    th.textContent = text;
    trHead.appendChild(th);
  });

  thead.appendChild(trHead);
  table.appendChild(thead);

  contactsData.forEach(contact => {
    const tr = document.createElement('tr');

    // Fields from entreprise object
    ['tradeName', 'designation', 'address', 'email', 'phoneNumber'].forEach(
        key => {
          const td = document.createElement('td');
          if (contact.entreprise.blacklisted) {
            td.style.color = 'red';
          }
          td.textContent = contact.entreprise[key] || '-';
          tr.appendChild(td);
        });

    // État
    const tdState = document.createElement('td');
    if (contact.entreprise.blacklisted) {
      tdState.style.color = 'red';
    }
    tdState.textContent = contact.state || '-';
    tr.appendChild(tdState);

    // Bouton pour changer l'état
    const tdButton = document.createElement('td');
    if (contact.entreprise.blacklisted) {
      tdButton.style.color = 'red';
    }
    const button = document.createElement('button');
    button.textContent = 'Avancement';
    button.className = 'btn btn-primary';
    button.setAttribute('type', 'button');
    button.setAttribute('data-bs-toggle', 'collapse');
    button.setAttribute('data-bs-target', '#collapseExample_' + contact.id);
    button.setAttribute('aria-expanded', 'false');
    button.setAttribute('aria-controls', 'collapseExample_' + contact.id);

    const divForm = document.createElement('div');
    divForm.className = 'collapse';
    divForm.id = 'collapseExample_' + contact.id;

    const avancementButtonDisplayed = contact.state === "initie";

    if (!avancementButtonDisplayed) {
      // If the button is not displayed, display "-"
      const tdAvancement = document.createElement('td');
      if (contact.entreprise.blacklisted) {
        tdButton.style.color = 'red';
      }
      tdAvancement.textContent = '-';
    } else {
      // Création du formulaire
      const form = document.createElement('form');
      const select = document.createElement('select');
      select.className = 'form-select mt-2 mb-2'; // Ajoutez des classes Bootstrap si nécessaire
      ['Rencontré', 'Suivi stoppé'].forEach(optionText => {
        const option = document.createElement('option');
        option.value = optionText;
        option.textContent = optionText;
        select.appendChild(option);
      });
      form.appendChild(select);

      // Création de la zone de texte pour le lieu
      const extraInput = document.createElement('input');
      extraInput.type = 'text';
      extraInput.placeholder = 'Lieu';
      extraInput.className = 'm-2';

      extraInput.style.display = 'block'; // Par défaut, cachez la zone de texte

      // Création des boutons par défaut pour "Rencontré"
      const surPlaceButton = document.createElement('button');
      surPlaceButton.textContent = 'Sur place';
      surPlaceButton.className = 'btn btn-primary me-2';
      surPlaceButton.addEventListener('click', (e) => {
        e.preventDefault();
        extraInput.value = 'Sur place';
      });

      const aDistanceButton = document.createElement('button');
      aDistanceButton.textContent = 'À distance';
      aDistanceButton.className = 'btn btn-primary me-2';
      aDistanceButton.addEventListener('click', (e) => {
        e.preventDefault();
        extraInput.value = 'À distance';
      });

      // Gérer l'affichage de la zone de texte en fonction de la sélection
      select.addEventListener('change', () => {
        if (select.value === 'Rencontré') {
          extraInput.style.display = 'block'; // Afficher la zone de texte si "Rencontré" est sélectionné
          surPlaceButton.style.display = 'inline-block'; // Afficher les boutons par défaut
          aDistanceButton.style.display = 'inline-block';
        } else {
          extraInput.style.display = 'none'; // Masquer la zone de texte pour les autres options
          surPlaceButton.style.display = 'none'; // Masquer les boutons par défaut
          aDistanceButton.style.display = 'none';
        }
      });

      // Création du bouton de soumission
      const submitButton = document.createElement('button');
      submitButton.textContent = 'Soumettre';
      submitButton.className = 'btn btn-primary me-2';
      submitButton.type = 'submit'; // Définir le type sur "submit" pour soumettre le formulaire

      // Ajout d'un écouteur d'événements pour gérer la soumission du formulaire
      form.addEventListener('submit', () => {

        const selectedOption = select.value;
        let additionalInfo = ''; // Informations supplémentaires à envoyer avec la soumission

        // Si "Rencontré" est sélectionné, récupérez le contenu de la zone de texte
        if (selectedOption === 'Rencontré') {
          additionalInfo = extraInput.value;
        }

        // Passer les informations supplémentaires à la fonction appropriée
        switch (selectedOption) {
          case 'Rencontré':
            meetContact(contact.id, additionalInfo, contact.version);
            break;
          case 'Suivi stoppé':
            stopFollowContact(contact.id, contact.version);
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
      form.appendChild(surPlaceButton);
      form.appendChild(aDistanceButton);
      form.appendChild(submitButton);

      // Ajout du formulaire au divForm
      divForm.appendChild(form);

      tdButton.appendChild(button);
      tdButton.appendChild(divForm);
      tr.appendChild(tdButton);

    }

    if (contact.state === "rencontre") {
      // Création du formulaire
      const form = document.createElement('form');
      const select = document.createElement('select');
      select.className = 'form-select mt-2 mb-2'; // Ajoutez des classes Bootstrap si nécessaire
      ['Refusé', 'Suivi stoppé', 'Accepté'].forEach(optionText => {
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
      extraInput.className = 'm-2'
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
      submitButton.className = 'btn btn-primary me-2';
      submitButton.type = 'submit'; // Définir le type sur "submit" pour soumettre le formulaire

// Ajout d'un écouteur d'événements pour gérer la soumission du formulaire
      form.addEventListener('submit', (event) => {

        const selectedOption = select.value;
        let additionalInfo = ''; // Informations supplémentaires à envoyer avec la soumission

        // Si "Rencontré" ou "Refusé" est sélectionné, récupérez le contenu de la zone de texte
        if (selectedOption === 'Refusé') {
          additionalInfo = extraInput.value;
        }

        // Passer les informations supplémentaires à la fonction appropriée
        switch (selectedOption) {
          case 'Suivi stoppé':
            stopFollowContact(contact.id, contact.version);

            break;
          case 'Refusé':
            refuseContact(contact.id, additionalInfo, contact.version);

            break;
          case 'Accepté':
            CreateStagePage(contact);

            return;
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

    if (contact.state === "stop follow" || contact.state === "refuse"
        || contact.state === "annule") {
      const tdVide = document.createElement('td');
      if (contact.entreprise.blacklisted) {
        tdVide.style.color = 'red';
      }
      tdVide.textContent = '-';
      tr.appendChild(tdVide);
    }

    // Lieu/Type de rencontre
    const tdMeeting = document.createElement('td');
    if (contact.entreprise.blacklisted) {
      tdMeeting.style.color = 'red';
    }
    tdMeeting.textContent = contact.meetingType || '-';
    tr.appendChild(tdMeeting);

    // Raison de refus
    const tdReason = document.createElement('td');
    if (contact.entreprise.blacklisted) {
      tdReason.style.color = 'red';
    }
    tdReason.textContent = contact.reasonForRefusal || '-';
    tr.appendChild(tdReason);

    tbody.appendChild(tr);
  });
  table.appendChild(tbody);

  const stageTable = document.createElement('table');
  stageTable.className = 'table table-bordered table-striped';
  const stageThead = document.createElement('thead');
  const stageTbody = document.createElement('tbody');
  const stageTrHead = document.createElement('tr');

  ['Entreprise', 'Appellation', 'Mail', 'N°Téléphone', 'Sujet de stage',
    'Type de rencontre'].forEach(text => {
    const th = document.createElement('th');
    th.textContent = text;
    stageTrHead.appendChild(th);
  });

  stageThead.appendChild(stageTrHead);
  stageTable.appendChild(stageThead);

  const tr = document.createElement('tr');

// Fields from entreprise object
  ['tradeName', 'designation', 'email', 'phoneNumber'].forEach(key => {
    const td = document.createElement('td');
    if (stageData) {
      td.textContent = stageData.contact.entreprise[key] || '----';
    }
    tr.appendChild(td);
  });

  ['internshipProject'].forEach(key => {
    const td = document.createElement('td');
    if (stageData) {
      td.textContent = stageData.internshipProject || '-';
    }
    var btn = document.createElement("button");

    // Set the text of the button
    btn.innerHTML = "Modifier sujet de stage";
    btn.type = 'button'; // Change this to 'button' to prevent form submission on click

    // Add an event listener to the button
    btn.addEventListener('click', function () {
      // Create an input field and a new button
      var input = document.createElement("input");
      var confirmBtn = document.createElement("button");

      // Set the properties of the input field and the new button
      input.type = 'text';
      input.value = stageData.internshipProject
          || 'Pas de sujet de stage defini'; // Set the value of the input field to the text content of the td element
      confirmBtn.innerHTML = 'Confirmer';
      confirmBtn.style.backgroundColor = 'green';
      confirmBtn.type = 'button'; // Change this to 'button' to prevent form submission on click

      // Add an event listener to the confirm button
      confirmBtn.addEventListener('click', () => {
        // Call the changeInternshipSubject method
        changeInternshipSubject(stageData.contact.id, input.value,
            stageData._version);
        // Wait for 2 seconds (2000 milliseconds) and then reload the page
        setTimeout(function () {
          location.reload();
        }, 500);

      });

      // Replace the text content of the td element with the input field and the new button
      td.textContent = '';
      td.appendChild(input);
      td.appendChild(confirmBtn);
    });

    // Append the button to the td element
    td.appendChild(btn);
    tr.appendChild(td);
  });

// Field for meetingType
  const td = document.createElement('td');
  if (stageData) {
    td.textContent = stageData.contact.meetingType || '-';
  }
  tr.appendChild(td);

  stageTbody.appendChild(tr);
  stageTable.appendChild(stageTbody);

  main.appendChild(ul);
  main.appendChild(submit);

  // Create and append a div to act as a spacer
  const spacer = document.createElement('div');
  spacer.style.height = '20px'; // Adjust the height as needed
  main.appendChild(spacer);

  // Create the button for adding a contact
  const addButton = document.createElement('button');
  addButton.textContent = 'Ajouter un contact';
  addButton.className = 'btn btn-info';
  addButton.addEventListener('click', () => {
    Navigate('/addContact');
  });

  // Create and append the title for the contacts table
  const contactsTitle = document.createElement('h2');
  contactsTitle.textContent = 'Contacts';

  if (!stageData) {
    contactsTitle.appendChild(addButton);
  }

  if (user.role === 'etudiant') {
    main.appendChild(contactsTitle);

    if (contactsData.length === 0) {
      const noContacts = document.createElement('p');
      noContacts.textContent = 'Aucun contact trouvé';
      main.appendChild(noContacts);
    } else {
      main.appendChild(table);
    }
    // Create and append the title for the stage table
    if (stageData) {

      const stageTitle = document.createElement('h2');
      stageTitle.textContent = 'Stage';
      main.appendChild(stageTitle);
      main.appendChild(stageTable);

      const supervisorTable = document.createElement('table');
      supervisorTable.className = 'table table-bordered table-striped';
      const supervisorThead = document.createElement('thead');
      const supervisorTbody = document.createElement('tbody');
      const supervisorTrHead = document.createElement('tr');

      ['Email', 'Prénom', 'Nom', 'Numéro de Téléphone'].forEach(text => {
        const th = document.createElement('th');
        th.textContent = text;
        supervisorTrHead.appendChild(th);
      });

      supervisorThead.appendChild(supervisorTrHead);
      supervisorTable.appendChild(supervisorThead);

      const supervisorTr = document.createElement('tr');

      ['email', 'firstName', 'lastName', 'phoneNumber'].forEach(key => {
        const td = document.createElement('td');
        if (stageData.supervisor) {
          td.textContent = stageData.supervisor[key] || '----';
        }
        supervisorTr.appendChild(td);
      });

      supervisorTbody.appendChild(supervisorTr);
      supervisorTable.appendChild(supervisorTbody);

      const supervisorTitle = document.createElement('h2');
      supervisorTitle.textContent = 'Superviseur';
      main.appendChild(supervisorTitle);
      main.appendChild(supervisorTable);
    }
  }
}

export default UserDataPage;

/* eslint-disable no-unused-vars */
import {clearPage, renderPageTitle} from "../../utils/render";
import {getAuthenticatedUser} from "../../utils/auths";
import {usePathParams} from "../../utils/path-params";
import baseURL from "../../../config";

const EntrepriseDetailsPage = async () => {
  clearPage();
  renderPageTitle('Detail étudiant')
  renderEtudiantDetail();
};

async function renderEtudiantDetail() {
  const idUser = usePathParams();
  const connectedUser = getAuthenticatedUser();
  const getuser = await getUser(idUser, connectedUser);
  console.log(getuser);

  const contactsData = await fetchContactsData(idUser, connectedUser);
  console.log(contactsData);

  const stageData = await fetchStageData(idUser, connectedUser);
  console.log(stageData);

  const main = document.querySelector('main');
  const ul = document.createElement('ul');
  ul.className = 'p-5';
  const items = [
    {label: 'Nom de famille: ', value: getuser.lastName},
    {label: 'Prénom: ', value: getuser.firstName},
    {label: 'Email: ', value: getuser.email},
    {label: 'Numéro de Téléphone: ', value: getuser.phoneNum},
    {label: 'Date d\'enregistrement: ', value: getuser.registrationDate},
    {label: 'Année académique: ', value: getuser.schoolYear.yearFormat},
    {label: 'Rôle: ', value: getuser.role}
  ];

  items.forEach(item => {
    const li = document.createElement('li');
    li.textContent = item.label + item.value;
    ul.appendChild(li);
  });

  main.appendChild(ul);

  if (contactsData.length === 0) {
    const noContactMessage = document.createElement('h2');
    noContactMessage.textContent = 'Aucun contact';
    main.appendChild(noContactMessage);
  } else {
    const table = document.createElement('table');
    table.className = 'table table-bordered table-striped';
    const tbody = document.createElement('tbody');

    contactsData.forEach(contact => {
      const tr = document.createElement('tr');

      ['tradeName', 'designation', 'address', 'email', 'phoneNumber'].forEach(
          key => {
            const td = document.createElement('td');
            td.textContent = contact.entreprise[key] || '-';
            tr.appendChild(td);
          });

      const tdState = document.createElement('td');
      tdState.textContent = contact.state || '-';
      tr.appendChild(tdState);

      const tdMeeting = document.createElement('td');
      tdMeeting.textContent = contact.meetingType || '-';
      tr.appendChild(tdMeeting);

      const tdReason = document.createElement('td');
      tdReason.textContent = contact.reasonForRefusal || '-';
      tr.appendChild(tdReason);

      tbody.appendChild(tr);
    });

    const thead = document.createElement('thead');
    const trHead = document.createElement('tr');

    ['Entreprise', 'Appellation', 'Adresse', 'Mail', 'N°Téléphone', 'État',
      'Lieu/Type de rencontre', 'Raison de refus'].forEach(text => {
      const th = document.createElement('th');
      th.textContent = text;
      trHead.appendChild(th);
    });

    thead.appendChild(trHead);
    table.appendChild(thead);
    table.appendChild(tbody);

    const contactsTitle = document.createElement('h2');
    contactsTitle.textContent = 'Contacts';
    main.appendChild(contactsTitle);

    main.appendChild(table);
  }

  if (!stageData) {
    const noStageMessage = document.createElement('h2');
    noStageMessage.textContent = 'Aucun Stage';
    main.appendChild(noStageMessage);

  } else {
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
      tr.appendChild(td);
    });

    const td = document.createElement('td');
    if (stageData) {
      td.textContent = stageData.contact.meetingType || '-';
    }
    tr.appendChild(td);

    stageTbody.appendChild(tr);
    stageTable.appendChild(stageTbody);

    const stageTitle = document.createElement('h2');
    stageTitle.textContent = 'Stages';
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

async function getUser(idUser, connectedUser) {
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `${connectedUser.token}`,
    },
  };
  const response = await fetch(`${baseURL}/users/getOne/${idUser}`, options);
  if (!response.ok) {
    if (response.status === 401) {
      alert("Veuillez vous connecter pour accéder à cette ressource.");
    } else {
      alert("Une erreur est survenue : " + response.statusText);
    }
  }

  const user = await response.json();
  return user;
}

async function fetchContactsData(idUser, connectedUser) {
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `${connectedUser.token}`,
    },
  };

  try {
    const responseContacts = await fetch(
        `${baseURL}/contacts/allContactsByUserId/${idUser}`, options);

    if (!responseContacts.ok) {
      throw new Error(
          `Une erreur est survenue lors de la recherche des contacts : ${responseContacts.statusText}`);
    }

    const contactsData = await responseContacts.json();
    return contactsData;
  } catch (error) {
    return undefined;
  }
}

async function fetchStageData(idUser, connectedUser) {
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `${connectedUser.token}`,
    },
  };

  try {
    const responseStage = await fetch(
        `${baseURL}/stages/stageByUserId/${idUser}`, options);

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
    return undefined;
  }
}

export default EntrepriseDetailsPage;
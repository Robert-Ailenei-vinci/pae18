import {clearPage, renderPageTitle} from '../../utils/render';
import {usePathParams} from "../../utils/path-params";
import {getAuthenticatedUser} from "../../utils/auths";
import Navbar from "../Navbar/Navbar";
import Navigate from "../Router/Navigate";
import {makeStateClean} from "./utils/MakeStateClean";

const EntrepriseDetailsPage = async () => {
  clearPage();
  renderDetailsEntreprise().then(r => console.log(r));
};

async function fetchEntrepriseContacts(user) {
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `${user.token}`,
    },
  };
  const response = await fetch(`http://localhost:3000/entreprise/entrepriseDetailsAllContacts/${usePathParams()}`, options);
  if (!response.ok) {
    if (response.status === 401) {
      alert("Username or password is incorrect. Please try again.");
    } else {
      alert("An error occurred:"+response.statusText);
    }
  }
  return response.json();
}

async function fetchEntrepriseDetails(user) {
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `${user.token}`,
    },
  };
  console.log("usePathParams() : "+usePathParams());
  const response = await fetch(`http://localhost:3000/entreprise/getOne/${usePathParams()}`, options);
  if (!response.ok) {
    if (response.status === 401) {
      alert("Username or password is incorrect. Please try again.");
    } else {
      alert("An error occurred:"+response.statusText);
    }
  }
  return response.json();
}

async function fetchStagesCountForCurrentYear(user) {
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `${user.token}`,
    },
  };
  const response = await fetch(`http://localhost:3000/entreprise/getStagesCountForCurrentYear/${usePathParams()}`, options);
  if (!response.ok) {
    if (response.status === 401) {
      alert("Username or password is incorrect. Please try again.");
    } else {
      alert("An error occurred:"+response.statusText);
    }
  }
  return response.json();
}

let isRendering = false;
async function renderDetailsEntreprise() {
  if (isRendering) {
    return;
  }
  const user = getAuthenticatedUser();
  isRendering = true;
  const entreprise = await fetchEntrepriseDetails(user);
  renderPageTitle('Détails de l\'entreprise : ' + entreprise.tradeName);
  const allContactsData = await fetchEntrepriseContacts(user);
  const nbStages = await fetchStagesCountForCurrentYear(user);
  console.log("nbStages : "+nbStages);
  isRendering = false;
  console.log("Contacts data : "+JSON.stringify(allContactsData));

  const main = document.querySelector('main');

  const nbStudentTitle = document.createElement('h4')
  nbStudentTitle.textContent = 'Nombre d\'étudiants en stage ' +
      'pour l\'année courante : '+nbStages;

  main.appendChild(nbStudentTitle)

  const tableContactsTitle = document.createElement('h5');
  tableContactsTitle.textContent = `Contacts de l'entreprise (${allContactsData.length}) : `;

  const table = document.createElement('table');
  table.className = 'table table-bordered table-striped';

// Create a header row and append it to the table
  const headerRow = document.createElement('tr');
  ['Nom de l\'étudiant','Prénom de l\'étudiant','Année académique','Etat', 'Lieu de rencontre', 'Raison du refus'].forEach(text => {
    const th = document.createElement('th');
    th.textContent = text;
    headerRow.appendChild(th);
  });
  table.appendChild(headerRow);

// Create a row for each contact and append it to the table
  allContactsData.forEach(contact => {
    const row = document.createElement('tr');

    const firstNameStudentCell = document.createElement('td');
    firstNameStudentCell.textContent = contact.user.lastName || '-';
    row.appendChild(firstNameStudentCell);

    const lastNameStudentCell = document.createElement('td');
    lastNameStudentCell.textContent = contact.user.firstName || '-';
    row.appendChild(lastNameStudentCell);

    const schoolYearCell = document.createElement('td');
    schoolYearCell.textContent = contact.schoolYearDTO.yearFormat || '-';
    row.appendChild(schoolYearCell);

    const stateCell = document.createElement('td');
    makeStateClean(contact.state,stateCell)
    row.appendChild(stateCell);

    const meetingLocationCell = document.createElement('td');
    meetingLocationCell.textContent = contact.meetingType || '-';
    row.appendChild(meetingLocationCell);

    const refusalReasonCell = document.createElement('td');
    refusalReasonCell.textContent = contact.reasonForRefusal || '-';
    row.appendChild(refusalReasonCell);

    table.appendChild(row);
  });

  main.appendChild(tableContactsTitle);
  main.appendChild(table);

  Navbar();

  Navigate(`/detailsEntreprise/${usePathParams()}`);

}

export default EntrepriseDetailsPage;
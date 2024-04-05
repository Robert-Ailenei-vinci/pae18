import {clearPage, renderPageTitle} from '../../utils/render';
import {usePathParams} from "../../utils/path-params";
import {getAuthenticatedUser} from "../../utils/auths";
import Navbar from "../Navbar/Navbar";
import Navigate from "../Router/Navigate";

const EntrepriseDetailsPage = () => {
  clearPage();
  renderPageTitle('Détails de l\'entreprise : ' + usePathParams());
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

let isRendering = false;
async function renderDetailsEntreprise() {
  if (isRendering) {
    return;
  }
  const user = getAuthenticatedUser();
  isRendering = true;
  const allContactsData = await fetchEntrepriseContacts(user);
  isRendering = false;
  console.log("Contacts data : "+JSON.stringify(allContactsData));

  const main = document.querySelector('main');

  const subtitle = document.createElement('h5');
  subtitle.textContent = 'Contacts de l\'entreprise : ';

  const table = document.createElement('table');

// Create a header row and append it to the table
  const headerRow = document.createElement('tr');
  ['Etat', 'Lieu de rencontre', 'Raison du refus'].forEach(text => {
    const th = document.createElement('th');
    th.textContent = text;
    headerRow.appendChild(th);
  });
  table.appendChild(headerRow);

// Create a row for each contact and append it to the table
  allContactsData.forEach(contact => {
    const row = document.createElement('tr');

    const stateCell = document.createElement('td');
    stateCell.textContent = contact.state || '-';
    row.appendChild(stateCell);

    const meetingLocationCell = document.createElement('td');
    meetingLocationCell.textContent = contact.meetingType || '-';
    row.appendChild(meetingLocationCell);

    const refusalReasonCell = document.createElement('td');
    refusalReasonCell.textContent = contact.reasonForRefusal || '-';
    row.appendChild(refusalReasonCell);

    table.appendChild(row);
  });

  main.appendChild(subtitle);
  main.appendChild(table);

  Navbar();

  Navigate(`/detailsEntreprise/${usePathParams()}`);

}

export default EntrepriseDetailsPage;
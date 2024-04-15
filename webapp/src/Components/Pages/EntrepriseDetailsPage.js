import {clearPage, renderPageTitle} from '../../utils/render';
import {usePathParams} from "../../utils/path-params";
import {getAuthenticatedUser} from "../../utils/auths";
import Navbar from "../Navbar/Navbar";
import Navigate from "../Router/Navigate";
import {makeStateClean} from "./utils/MakeStateClean";
import {blacklistEntreprise} from "./utils/BlacklistEntreprise";
import {unblacklistEntreprise} from "./utils/UnBlacklistEntreprise";

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
  const user = getAuthenticatedUser();
  const entreprise = await fetchEntrepriseDetails(user);
  let isBlacklisted = entreprise.blacklisted;
  if (isBlacklisted==false) {
  if (isRendering) {
    return;
  }
  
  isRendering = true;
  
  console.log(entreprise);
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

  const submitButton = document.createElement('button');
  submitButton.textContent = 'Blacklister';
  submitButton.className = 'btn btn-primary';
  submitButton.type = 'submit'; // Définir le type sur "submit" pour soumettre le formulaire
// Create the form
const form = document.createElement('form');
form.style.display = 'none'; // Initially hide the form

// Create the label
const label = document.createElement('label');
label.textContent = 'Veuillez indiquer la raison du refus :';
form.appendChild(label);

// Create the input field
const input = document.createElement('input');
input.type = 'text';
form.appendChild(input);

// Create the "Confirm" button
const confirmButton = document.createElement('button');
confirmButton.textContent = 'Confirmer';
confirmButton.type = 'submit'; // Set the type to "submit" to submit the form
form.appendChild(confirmButton);

// Replace the "Blacklister" button with the form when clicked
submitButton.addEventListener('click', function() {
  main.replaceChild(form, submitButton); // Replace the "Blacklister" button with the form
  form.style.display = 'block'; // Show the form
});

// Add an event listener to the form to handle the submit event
form.addEventListener('submit', function(event) {
  event.preventDefault(); // Prevent the form from being submitted normally

  // Show an alert asking for confirmation
  console.log("zaza ", input.value, " zaza ", entreprise.id)
  const isConfirmed = confirm('Are you sure?');
  if (isConfirmed) {
    //
    blacklistEntreprise(input.value, entreprise.id, entreprise.version);
    setTimeout(function() {
      Navigate('/seeEntreprises');
    }, 500);
  }
});
  main.appendChild(nbStudentTitle);
  main.appendChild(submitButton); 
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
  }else {
    const entrepriseBlacklisted = await fetchEntrepriseDetails(user);

    const main = document.querySelector('main');

    // Create a title
    const title = document.createElement('h2');
    title.textContent = 'Cette entreprise a été blacklistée';
    main.appendChild(title);
  
    // Create the "Deblacklister" button
    const deblacklistButton = document.createElement('button');
    deblacklistButton.textContent = 'Deblacklister';
    deblacklistButton.className = 'btn btn-primary';
    deblacklistButton.type = 'button';
  
    // Add an event listener to the "Deblacklister" button
    deblacklistButton.addEventListener('click', function() {
      // Show an alert asking for confirmation
      console.log(entrepriseBlacklisted, " ver")
      const isConfirmed = confirm('Are you sure?');
      if (isConfirmed) {
        // If confirmed, unblacklist the company
        // Replace "unblacklistEntreprise" with the actual function to unblacklist the company
        unblacklistEntreprise(entreprise.id, entreprise.version);
        setTimeout(function() {
          Navigate('/seeEntreprises');
        }, 500);
      }
    });
  
    // Add the "Deblacklister" button to the main element
    main.appendChild(deblacklistButton);
  }
}

export default EntrepriseDetailsPage;
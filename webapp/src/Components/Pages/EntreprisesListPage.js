import {clearPage, renderPageTitle} from '../../utils/render';
import Navbar from "../Navbar/Navbar";
import Navigate from "../Router/Navigate";
import {getAuthenticatedUser} from "../../utils/auths";

const EntreprisesListPage = () => {
    clearPage();
    renderPageTitle('Toutes les entreprises');
    renderAllEntreprises().then(r => console.log(r));
};

async function fetchEntreprisesForSchoolYear(user, schoolYear) {
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${user.token}`,
        },
    };
    const response = await fetch(`http://localhost:3000/entreprise/getAllForSchoolYear/${schoolYear}`, options);
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

async function renderAllEntreprises() {
    const user = getAuthenticatedUser();
    if (isRendering) {
        return;
    }
    isRendering = true;
    const allEntreprisesData = await fetchEntreprises(user);
    isRendering = false;
    console.log("Entreprises data : "+allEntreprisesData);
    const main = document.querySelector('main');
    const table = document.createElement('table');
    const tableHead = document.createElement('tr')
    const columns = ["Nom", "Appelation", "N°Téléphone","Adresse","Blacklisté", "Raison du blacklist"];
    columns.forEach(text => {
            const th = document.createElement('th');
            th.textContent = text;
            tableHead.appendChild(th);
        }
    );
    const tableBody = document.createElement('tbody');
    allEntreprisesData.forEach(entreprise => {
        const tr = document.createElement('tr');
        const tdNom = document.createElement('a');
        tdNom.href = `/detailsEntreprise/${entreprise.id}`;
        tdNom.textContent = entreprise.tradeName;
        const tdAppelation = document.createElement('td');
        tdAppelation.textContent = entreprise.designation;
        const tdPhone = document.createElement('td');
        tdPhone.textContent = entreprise.phoneNumber;
        const tdAdresse = document.createElement('td');
        tdAdresse.textContent = entreprise.address;
        const tdBlacklisted = document.createElement('td');
        if (entreprise.blacklisted) {
            tdBlacklisted.textContent = "Oui";
        } else {
            tdBlacklisted.textContent = "Non";
        }
        const tdBlacklistReason = document.createElement('td');
        if (entreprise.blacklistReason === null) {
            tdBlacklistReason.textContent = "/";
            tdBlacklistReason.style.color = "grey";
        }
        else {
            tdBlacklistReason.textContent = entreprise.blacklistReason;
        }

        tr.appendChild(tdNom);
        tr.appendChild(tdAppelation);
        tr.appendChild(tdPhone);
        tr.appendChild(tdAdresse);
        tr.appendChild(tdBlacklisted);
        tr.appendChild(tdBlacklistReason);
        tableBody.appendChild(tr);
    });
    table.appendChild(tableHead);
    table.appendChild(tableBody);
    main.appendChild(table);

    Navbar();

    Navigate('/seeEntreprises');
}

// Function to render the dropdown and fetch entreprises for the selected school year
async function renderEntreprisesWithSchoolYear() {
    const user = getAuthenticatedUser();
    const main = document.querySelector('main');
    main.innerHTML = ''; // Clear main content before rendering

    const selectSchoolYearLabel = document.createElement('label');
    selectSchoolYearLabel.textContent = 'Choisir une année académique : ';
    const selectSchoolYear = document.createElement('select');

    // Fetch available school years and populate the dropdown
    const schoolYears = await fetchSchoolYears(user);
    schoolYears.forEach(year => {
        const option = document.createElement('option');
        option.value = year;
        option.textContent = year;
        selectSchoolYear.appendChild(option);
    });

    // Add event listener to fetch and render entreprises for the selected school year
    selectSchoolYear.addEventListener('change', async () => {
        const selectedYear = selectSchoolYear.value;
        const entreprises = await fetchEntreprisesForSchoolYear(user, selectedYear);
        renderEntreprisesTable(entreprises);
    });

    main.appendChild(selectSchoolYearLabel);
    main.appendChild(selectSchoolYear);

    // Fetch and render entreprises for the default selected school year
    const defaultYear = await getDefaultSchoolYear();
    const defaultEntreprises = await fetchEntreprisesForSchoolYear(user, defaultYear);
    renderEntreprisesTable(defaultEntreprises);
}

// Function to fetch available school years
async function fetchSchoolYears(user) {
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${user.token}`,
        },
    };
    const response = await fetch('http://localhost:3000/schoolYears/getAllSchoolYears', options);
    if (!response.ok) {
        console.error('Failed to fetch school years');
        return [];
    }
    const schoolYears = await response.json();
    return schoolYears;
}

// Function to fetch the default school year
async function getDefaultSchoolYear() {
    const response = await fetch('http://localhost:3000/schoolYears/getDefaultSchoolYear');
    if (!response.ok) {
        console.error('Failed to fetch default school year');
        return null;
    }
    const defaultYear = await response.text();
    return defaultYear;
}

// Function to render the entreprises table
function renderEntreprisesTable(entreprises) {
    const main = document.querySelector('main');
    const table = document.createElement('table');
    const tableHead = document.createElement('tr')
    const columns = ["Nom", "Appelation", "N°Téléphone","Adresse","Blacklisté", "Raison du blacklist"];
    columns.forEach(text => {
            const th = document.createElement('th');
            th.textContent = text;
            tableHead.appendChild(th);
        }
    );
    const tableBody = document.createElement('tbody');
    entreprises.forEach(entreprise => {
        const tr = document.createElement('tr');
        const tdNom = document.createElement('a');
        tdNom.href = `/detailsEntreprise/${entreprise.id}`;
        tdNom.textContent = entreprise.tradeName;
        const tdAppelation = document.createElement('td');
        tdAppelation.textContent = entreprise.designation;
        const tdPhone = document.createElement('td');
        tdPhone.textContent = entreprise.phoneNumber;
        const tdAdresse = document.createElement('td');
        tdAdresse.textContent = entreprise.address;
        const tdBlacklisted = document.createElement('td');
        if (entreprise.blacklisted) {
            tdBlacklisted.textContent = "Oui";
        } else {
            tdBlacklisted.textContent = "Non";
        }
        const tdBlacklistReason = document.createElement('td');
        if (entreprise.blacklistReason === null) {
            tdBlacklistReason.textContent = "/";
            tdBlacklistReason.style.color = "grey";
        }
        else {
            tdBlacklistReason.textContent = entreprise.blacklistReason;
        }

        tr.appendChild(tdNom);
        tr.appendChild(tdAppelation);
        tr.appendChild(tdPhone);
        tr.appendChild(tdAdresse);
        tr.appendChild(tdBlacklisted);
        tr.appendChild(tdBlacklistReason);
        tableBody.appendChild(tr);
    });
    table.appendChild(tableHead);
    table.appendChild(tableBody);
    main.appendChild(table);

    Navbar();

    Navigate('/seeEntreprises');
}

export default EntreprisesListPage;

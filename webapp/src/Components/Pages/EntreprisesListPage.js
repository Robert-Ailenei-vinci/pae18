import {clearPage, renderPageTitle} from '../../utils/render';
import Navbar from "../Navbar/Navbar";
import Navigate from "../Router/Navigate";
import {getAuthenticatedUser} from "../../utils/auths";

const EntreprisesListPage = () => {
    clearPage();
    renderPageTitle('Toutes les entreprises');
    renderEntreprisesWithSchoolYear().then(r => r);
};

async function fetchEntreprisesForSchoolYear(user, schoolYearId) {
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${user.token}`,
        },
    };
    const response = await fetch(`http://localhost:3000/entreprise/getAllForSchoolYear/${schoolYearId}`, options);
    if (!response.ok) {
        if (response.status === 401) {
            alert("Veuillez vous connecter pour accéder à cette ressource.");
        } else {
            alert(`Une erreur est survenue : ${response.status + " " + response.statusText}`);
        }
    }
    return response.json();
}

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
        alert(`Une erreur est survenue : ${response.status + " " + response.statusText}`);
        console.error('Failed to fetch school years');
        return [];
    }
    const schoolYears = await response.json();
    console.log('Fetched school years:', schoolYears);
    return schoolYears;
}

async function renderEntreprisesWithSchoolYear() {
    const user = getAuthenticatedUser();
    const main = document.querySelector('main');

    const selectSchoolYearLabel = document.createElement('label');
    selectSchoolYearLabel.textContent = 'Choisir une année académique : ';
    const selectSchoolYear = document.createElement('select');

    const schoolYears = await fetchSchoolYears(user);
    schoolYears.forEach(schoolYear => {
        const option = document.createElement('option');
        option.value = schoolYear.id;
        option.textContent = schoolYear.yearFormat;
        console.log('Option:', option);
        selectSchoolYear.appendChild(option);
    });

    // Append the dropdown to the main element before fetching and rendering enterprises
    main.appendChild(selectSchoolYearLabel);
    main.appendChild(selectSchoolYear);

    // Fetch and render enterprises for the default school year when the page is loaded
    const defaultYear = await getDefaultSchoolYear();
    console.log('Default year:', defaultYear);
    if (defaultYear !== null) {
        const defaultEntreprises = await fetchEntreprisesForSchoolYear(user, defaultYear.id);
        renderEntreprisesTable(defaultEntreprises);
    }

    // Update the enterprises whenever a different year is selected from the dropdown
    selectSchoolYear.addEventListener('change', async () => {
        const selectedYearId = selectSchoolYear.value;
        console.log('Selected year:', selectedYearId)
        const entreprises = await fetchEntreprisesForSchoolYear(user, selectedYearId);
        console.log('Fetched entreprises for selected school year:', entreprises);
        // Clear the current enterprises before rendering the new ones
        const table = main.querySelector('table');
        if (table) {
            main.removeChild(table);
        }
        renderEntreprisesTable(entreprises);
    });
}

async function getDefaultSchoolYear() {
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${getAuthenticatedUser().token}`,
        },
    };
    const response = await fetch('http://localhost:3000/schoolYears/getDefaultSchoolYear', options);
    if (!response.ok) {
        alert(`Une erreur est survenue : ${response.status + " " + response.statusText}`);
        console.error('Failed to fetch default school year');
        return null;
    }
    const defaultYear = await response.json();
    return defaultYear;
}

function renderEntreprisesTable(entreprises) {
    console.log('Rendering entreprises:', entreprises);
    const main = document.querySelector('main');
    const table = document.createElement('table');
    table.className = 'table table-bordered table-striped';
    const tableHead = document.createElement('tr')
    const columns = ["Nom", "Appellation", "N° de Téléphone","Adresse","Blacklisté", "Raison"];
    columns.forEach(text => {
            const th = document.createElement('th');
            th.textContent = text;
            tableHead.appendChild(th);
        }
    );
    const tableBody = document.createElement('tbody');
    entreprises.forEach(entreprise => {
        const tr = document.createElement('tr');
        console.log(entreprise);
    
        const tdNom = document.createElement('td');
        const tdAppelation = document.createElement('td');
        const tdPhone = document.createElement('td');
        const tdAdresse = document.createElement('td');
        const tdBlacklisted = document.createElement('td');
        const tdBlacklistReason = document.createElement('td');
        
        const link = document.createElement('a'); // Create an anchor element
        link.href = `/detailsEntreprise/${entreprise.id}`; // Set the href attribute
        link.textContent = entreprise.tradeName; // Set the text content of the link
        // Apply styles directly to the anchor element
        link.style.color = '#007bff'; // Blue color
        link.style.fontWeight = 'bold'; // Bold font
        link.style.textDecoration = 'none'; // Remove underline
        // Add hover effect
        link.addEventListener('mouseover', () => {
            link.style.color = '#0056b3'; // Darker blue color on hover
        });
        link.addEventListener('mouseout', () => {
            link.style.color = '#007bff'; // Restore original color on mouseout
        });
        tdNom.appendChild(link); // Append the link to the td element
        
        tdAppelation.textContent = entreprise.designation;
        tdPhone.textContent = entreprise.phoneNumber;
        tdAdresse.textContent = entreprise.address;
        tdBlacklisted.textContent = entreprise.blacklisted ? "Oui" : "Non";
        tdBlacklistReason.textContent = entreprise.blacklistReason || "/"; // Set default value if null
        tdBlacklistReason.style.color = entreprise.blacklistReason ? "black" : "grey"; // Adjust color
        
        if (entreprise.blacklisted) {
            // Set color for specific cells if entreprise is blacklisted
            tdNom.style.color = 'red';
            tdAppelation.style.color = 'red';
            tdPhone.style.color = 'red';
            tdAdresse.style.color = 'red';
            tdBlacklisted.style.color = 'red';
            tdBlacklistReason.style.color = 'red';
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
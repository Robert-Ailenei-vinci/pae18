import {clearPage, renderPageTitle} from '../../utils/render';
import Navbar from "../Navbar/Navbar";
import Navigate from "../Router/Navigate";
import {getAuthenticatedUser} from "../../utils/auths";

const EntreprisesListPage = () => {
    clearPage();
    renderPageTitle('Toutes les entreprises');
    renderAllEntreprises().then(r => console.log(r));
};

async function fetchEntreprises(user) {
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${user.token}`,
        },
    };
    const response = await fetch('http://localhost:3000/entreprise/getAll', options);
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

export default EntreprisesListPage;
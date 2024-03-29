import {clearPage, renderPageTitle} from '../../utils/render';
import Navbar from "../Navbar/Navbar";
import Navigate from "../Router/Navigate";
import {getAuthenticatedUser} from "../../utils/auths";

const EntreprisesListPage = () => {
    clearPage();
    renderPageTitle('Toutes les entreprises');
    renderAllEntreprises();
};

let isRendering = false;

async function renderAllEntreprises() {
    const user = getAuthenticatedUser();
    if (isRendering) {
        return;
    }
    isRendering = true;
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${user.token}`,
        },
    };
    const allEntreprises = await fetch('http://localhost:3000/entreprise/getAll', options);
    if (!allEntreprises.ok) {
        if (allEntreprises.status === 401) {
            alert("Username or password is incorrect. Please try again.");
        } else {
            // For other errors, handle them accordingly
            console.error("An error occurred:", allEntreprises.statusText);
        }
    }
    const allEntreprisesData = allEntreprises.json();
    isRendering = false;
    const main = document.querySelector('main');
    const table = document.createElement('table');
    const tableHead = document.createElement('tr')
    const columns = ["Nom", "Appelation", "N°Téléphone", "Nombre d'étudiants en stage", "Blacklisté", "Raison du blacklist"];
    columns.forEach(text => {
            const th = document.createElement('th');
            th.textContent = text;
            tableHead.appendChild(th);
        }
    )
    console.log(allEntreprisesData);
    table.appendChild(tableHead);
    main.appendChild(table);

    Navbar();

    Navigate('/seeEntreprises');
}

export default EntreprisesListPage;
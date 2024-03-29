import { clearPage, renderPageTitle } from "../../utils/render";

const EntreprisesPage = () => {
    clearPage();
    renderPageTitle('Entreprises Page');
    console.log('entreprise page');
    renderEntreprises();
};

async function fetchEntreprises() {
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    };
    try {
        const responseEntreprises = await fetch(
            `http://localhost:3000/entreprises/getAll`, options);
    
        if (!responseEntreprises.ok) {
          throw new Error(
              `Failed to fetch entreprises: ${responseEntreprises.statusText}`);
        }
    
        const entreprisesData = await responseEntreprises.json();
        return entreprisesData;
      } catch (error) {
        throw new Error(
            `An error occurred while fetching contacts: ${error.message}`);
      }
}



async function renderEntreprises(){

    const entreprisesData = await fetchEntreprises();

    const table = document.createElement('table');
    table.className = 'table';
    const thead = document.createElement('thead');
    const tbody = document.createElement('tbody');
    const trHead = document.createElement('tr');
    
    ['Nom ', 'Appelation', 'Adresse', 'N°Telephone', 'Email', 'Blacklisté', 'Si oui, raison'].forEach(text => {
        const th = document.createElement('th');
        th.textContent = text;
        trHead.appendChild(th);
    });
    
    thead.appendChild(trHead);
    table.appendChild(thead);
    table.appendChild(tbody);

    entreprisesData.forEach(entreprise => {
        const tr = document.createElement('tr');

    // Fields from entreprise object
    ['tradeName', 'designation', 'address', 'email', 'phoneNumber'].forEach(
      key => {
        const td = document.createElement('td');
        td.textContent = entreprise[key] || '-';                                 //a verifier
        tr.appendChild(td);
      });
    })
}



export default EntreprisesPage;


import { getAuthenticatedUser } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import baseURL from '../../../config';

const UsersPage = () => {
    clearPage();
    renderPageTitle('Tous les utilisateurs');
    renderAllUsers2();
};


async function fetchAllUsers(user) {
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `${user.token}`,
        },
    };

    try {
        const response = await fetch(`${baseURL}/users/getAll`, options);
        if (!response.ok) {
            throw new Error(`Une erreur est survenue lors de la recherche des utilisateurs : ${response.statusText}`);
        }
        const usersData = await response.json();
        console.log('Users data:', usersData); // Afficher les données récupérées dans la console
        return usersData;
    } catch (error) {
        alert(error.message);
        console.error('Error fetching users:', error); // Afficher l'erreur dans la console
        throw new Error(`An error occurred while fetching users: ${error.message}`);
    }
}



// Lorsque le fetch fonctionnera
async function renderAllUsers2() {
    const userData = getAuthenticatedUser();
    try {
        const usersData = await fetchAllUsers(userData);
        const main = document.querySelector('main');

        const table = document.createElement('table');
        table.className = 'user-table';

        const thead = document.createElement('thead');
        const headerRow = document.createElement('tr');
        ['Prénom', 'Nom', 'Rôle', 'Année scolaire'].forEach(headerText => {
            const th = document.createElement('th');
            th.textContent = headerText;
            headerRow.appendChild(th);
        });
        thead.appendChild(headerRow);
        table.appendChild(thead);

        const tbody = document.createElement('tbody');
        usersData.forEach(userU => {
            const row = document.createElement('tr');
            ['firstName', 'lastName', 'role'].forEach(fieldName => {
                const cell = document.createElement('td');
                cell.textContent = userU[fieldName];
                row.appendChild(cell);
            });
            const schoolYearCell = document.createElement('td');
            schoolYearCell.textContent = userU.schoolYear.yearFormat;
            row.appendChild(schoolYearCell);
            tbody.appendChild(row);
        });
        table.appendChild(tbody);

        main.appendChild(table);
    } catch (error) {
        alert(`Une erreur est survenue lors de l'affichage des utilisateurs.`);
        console.error('Error rendering all users:', error);
    }
}

export default UsersPage;
import { clearPage, renderPageTitle } from '../../utils/render';

const UsersPage = () => {
    clearPage();
    renderPageTitle('Tous les utilisateurs');
    renderAllUsers();
    renderAllUsers2();
};

async function fetchAllUsers() {
    try {
        const response = await fetch('http://localhost:3000/users/getAll');
        if (!response.ok) {
            throw new Error(`Failed to fetch users: ${response.statusText}`);
        }
        const usersData = await response.json();
        console.log('Users data:', usersData); // Afficher les données récupérées dans la console
        return usersData;
    } catch (error) {
        console.error('Error fetching users:', error); // Afficher l'erreur dans la console
        throw new Error(`An error occurred while fetching users: ${error.message}`);
    }
}

async function renderAllUsers() {
    try {
        
      const usersData = [
        { firstName: 'raf', lastName: 'jsp', role: 'admin', schoolYearFormat: '2023-2024' },
        { firstName: 'robert', lastName: 'ghvg', role: 'professeur', schoolYearFormat: '2023-2024' },
        { firstName: 'tdfytg', lastName: 'ghy', role: 'étudiant', schoolYearFormat: '2023-2024' }
      ];
  
      const main = document.querySelector('main');
      const table = document.createElement('table');
      table.className = 'min-w-full divide-y divide-gray-200';
  
      const thead = document.createElement('thead');
      const headerRow = document.createElement('tr');
      ['Prénom', 'Nom', 'Rôle', 'Année scolaire'].forEach(headerText => {
        const th = document.createElement('th');
        th.className = 'px-6 py-3 bg-gray-50 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider';
        th.textContent = headerText;
        headerRow.appendChild(th);
      });
      thead.appendChild(headerRow);
      table.appendChild(thead);
  
      const tbody = document.createElement('tbody');
      usersData.forEach(user => {
        const row = document.createElement('tr');
        row.className = 'bg-white';
        ['firstName', 'lastName', 'role', 'schoolYearFormat'].forEach(fieldName => {
          const cell = document.createElement('td');
          cell.className = 'px-6 py-4 whitespace-no-wrap text-sm leading-5 text-gray-500';
          cell.textContent = user[fieldName];
          row.appendChild(cell);
        });
        tbody.appendChild(row);
      });
      table.appendChild(tbody);
  
      main.appendChild(table);
    } catch (error) {
      console.error('Error rendering all users:', error);
    }
  }


// QUAND LE FETCH MARCHERA 

async function renderAllUsers2() { 
    try {
      const usersData = await fetchAllUsers();
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
      usersData.forEach(user => {
        const row = document.createElement('tr');
        ['firstName', 'lastName', 'role', 'schoolYearFormat'].forEach(fieldName => {
          const cell = document.createElement('td');
          cell.textContent = user[fieldName];
          row.appendChild(cell);
        });
        tbody.appendChild(row);
      });
      table.appendChild(tbody);
  
      main.appendChild(table);
    } catch (error) {
      console.error('Error rendering all users:', error);
    }
  }

export default UsersPage;
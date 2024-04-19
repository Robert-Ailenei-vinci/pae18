import {getAuthenticatedUser} from '../../utils/auths';
import {clearPage, renderPageTitle} from '../../utils/render';
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
      throw new Error(
          `Une erreur est survenue lors de la recherche des utilisateurs : ${response.statusText}`);
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

const searchUsers = (users, term, selectedYear) => {
  return users.filter(user => {
    const firstNameMatch = user.firstName.toLowerCase().includes(term);
    const lastNameMatch = user.lastName.toLowerCase().includes(term);
    const yearMatch = !selectedYear || user.schoolYear.yearFormat === selectedYear;
    return (firstNameMatch || lastNameMatch) && yearMatch;
  });
};

async function renderAllUsers2() {
  const userData = getAuthenticatedUser();
  try {
    const usersData = await fetchAllUsers(userData);
    const main = document.querySelector('main');

    // Création du bouton de filtrage
    const filterButton = document.createElement('button');
    filterButton.className = 'btn btn-primary m-4'
    filterButton.innerHTML = '<i class="bi bi-filter"></i> Afficher seulement les étudiants';

    filterButton.addEventListener('click', () => {
      toggleFilterButton(filterButton, usersData);
    });
    main.appendChild(filterButton);

    // Création du champ de recherche
    const searchInput = document.createElement('input');
    searchInput.type = 'text';
    searchInput.placeholder = 'Rechercher par prénom ou nom';
    /*
    searchInput.addEventListener('click', () => {
      const searchTerm = searchInput.value.trim().toLowerCase();
      const filteredUsers = searchUsers(usersData, searchTerm);

      renderFilteredUsers(filteredUsers);
    });
    */
    main.appendChild(searchInput);

    // Création du menu déroulant des années scolaires
    const yearSelect = document.createElement('select');
    yearSelect.innerHTML = '<option value="">Toutes les années</option>';
    const distinctYears = [...new Set(
        usersData.map(user => user.schoolYear.yearFormat))];
    distinctYears.forEach(year => {
      const option = document.createElement('option');
      option.value = year;
      option.textContent = year;
      yearSelect.appendChild(option);
    });
    main.appendChild(yearSelect);

    // Création du bouton de recherche
    const searchButton = document.createElement('button');
    searchButton.textContent = 'Rechercher';
    searchButton.className = 'btn btn-primary';
    searchButton.addEventListener('click', () => {
      const searchTerm = searchInput.value.trim().toLowerCase();
      const selectedYear = yearSelect.value;
      const filteredUsers = searchUsers(usersData, searchTerm, selectedYear);
      renderAllUsers(filteredUsers);
    });
    main.appendChild(searchButton);

    const table = document.createElement('table');
    table.className = 'table table-bordered table-striped';

    const thead = document.createElement('thead');
    const headerRow = document.createElement('tr');
    ['-', 'Prénom', 'Nom', 'Rôle', 'Année scolaire'].forEach(headerText => {
      const th = document.createElement('th');
      th.textContent = headerText;
      headerRow.appendChild(th);
    });
    thead.appendChild(headerRow);
    table.appendChild(thead);

    const tbody = document.createElement('tbody');
    usersData.forEach(userU => {
      const row = document.createElement('tr');
      ['-', 'firstName', 'lastName', 'role'].forEach(fieldName => {
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

function toggleFilterButton(button, usersData) {
  if (button.dataset.filtered === "true") {
    button.innerHTML = '<i class="bi bi-filter"></i> Afficher seulement les étudiants';
    button.dataset.filtered = "false";
    renderAllUsers(usersData);
  } else {
    button.innerHTML = '<i class="bi bi-filter"></i> Afficher tous les utilisateurs';
    button.dataset.filtered = "true";
    renderFilteredUsers(usersData);
  }
}

function renderFilteredUsers(usersData) {
  const main = document.querySelector('main');
  const table = main.querySelector('table');
  const tbody = table.querySelector('tbody');
  tbody.innerHTML = ''; // Effacer le contenu actuel du tbody
  usersData.forEach(userU => {
    // Filtrer les utilisateurs pour n'afficher que les étudiants
    if (userU.role === 'etudiant') {

      const row = document.createElement('tr');

      const detailButton = document.createElement('a');
      detailButton.href = `/detailsEtudiant/${userU.id}`;
      detailButton.className = 'bi bi-arrow-through-heart-fill'

      row.appendChild(detailButton);

      [`firstName`, 'lastName', 'role'].forEach(fieldName => {
        const cell = document.createElement('td');
        cell.textContent = userU[fieldName];
        row.appendChild(cell);
      });
      const schoolYearCell = document.createElement('td');
      schoolYearCell.textContent = userU.schoolYear.yearFormat;
      row.appendChild(schoolYearCell);
      tbody.appendChild(row);
    }
  });
}

function renderAllUsers(usersData) {
  const main = document.querySelector('main');
  const table = main.querySelector('table');
  const tbody = table.querySelector('tbody');
  tbody.innerHTML = ''; // Effacer le contenu actuel du tbody
  usersData.forEach(userU => {
    // Filtrer les utilisateurs pour n'afficher que les étudiants
    if (userU.role === 'etudiant') {

      const row = document.createElement('tr');

      const detailButton = document.createElement('a');
      detailButton.href = `/detailsEtudiant/${userU.id}`;
      detailButton.className = 'bi bi-arrow-through-heart-fill'

      row.appendChild(detailButton);

      [`firstName`, 'lastName', 'role'].forEach(fieldName => {
        const cell = document.createElement('td');
        cell.textContent = userU[fieldName];
        row.appendChild(cell);
      });
      const schoolYearCell = document.createElement('td');
      schoolYearCell.textContent = userU.schoolYear.yearFormat;
      row.appendChild(schoolYearCell);
      tbody.appendChild(row);
    } else {
      const row = document.createElement('tr');
      ['-', 'firstName', 'lastName', 'role'].forEach(fieldName => {
        const cell = document.createElement('td');
        cell.textContent = userU[fieldName];
        row.appendChild(cell);
      });
      const schoolYearCell = document.createElement('td');
      schoolYearCell.textContent = userU.schoolYear.yearFormat;
      row.appendChild(schoolYearCell);
      tbody.appendChild(row);
    }
  });
}

export default UsersPage;

import {getAuthenticatedUser} from '../../utils/auths';
import {clearPage, renderPageTitle} from '../../utils/render';
import { makeRoleClean } from './utils/MakeRoleClean';
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

// Fonction pour filtrer les utilisateurs en fonction du type sélectionné
const searchUsers = (users, term, selectedYear, showOnlyStudents) => {
  return users.filter(user => {
    const firstNameMatch = user.firstName.toLowerCase().includes(term);
    const lastNameMatch = user.lastName.toLowerCase().includes(term);
    const yearMatch = !selectedYear || user.schoolYear.yearFormat
        === selectedYear;
    const typeMatch = !showOnlyStudents || (showOnlyStudents && user.role
        === 'etudiant');
    return (firstNameMatch || lastNameMatch) && yearMatch && typeMatch;
  });
};

async function renderAllUsers2() {
  const userData = getAuthenticatedUser();
  try {
    const usersData = await fetchAllUsers(userData);
    const main = document.querySelector('main');

    // Création du champ de recherche
    const searchInput = document.createElement('input');
    searchInput.type = 'text';
    searchInput.placeholder = 'Rechercher par prénom ou nom';
    searchInput.className = ' me-5'
    main.appendChild(searchInput);

    // Création du menu déroulant des années scolaires
    const yearSelect = document.createElement('select');
    yearSelect.innerHTML = '<option value="">Toutes les années</option>';
    yearSelect.className = 'me-3'
    const distinctYears = [...new Set(
        usersData.map(user => user.schoolYear.yearFormat))];
    distinctYears.forEach(year => {
      const option = document.createElement('option');
      option.value = year;
      option.textContent = year;
      yearSelect.appendChild(option);
    });
    main.appendChild(yearSelect);

    // Création de la case à cocher
    const studentCheckboxLabel = document.createElement('label');
    studentCheckboxLabel.textContent = 'Afficher seulement les étudiants';
    studentCheckboxLabel.className = 'me-3';
    const studentCheckbox = document.createElement('input');
    studentCheckbox.type = 'checkbox';
    studentCheckbox.addEventListener('change', () => {
      const searchTerm = searchInput.value.trim().toLowerCase();
      const selectedYear = yearSelect.value;
      const showOnlyStudents = studentCheckbox.checked;
      const filteredUsers = searchUsers(usersData, searchTerm, selectedYear,
          showOnlyStudents);
      renderAllUsers(filteredUsers);
    });
    studentCheckboxLabel.appendChild(studentCheckbox);
    main.appendChild(studentCheckboxLabel);

    // Création du bouton de recherche
    const searchButton = document.createElement('button');
    searchButton.textContent = 'Rechercher';
    searchButton.className = 'btn btn-primary bg-custom';
    searchButton.addEventListener('click', () => {
      const searchTerm = searchInput.value.trim().toLowerCase();
      const selectedYear = yearSelect.value;
      const showOnlyStudents = studentCheckbox.checked;

      const filteredUsers = searchUsers(usersData, searchTerm, selectedYear,
          showOnlyStudents);

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
      if (userU.role === 'etudiant') {

        const row = document.createElement('tr');

    // Amélioration de la cellule pour le bouton de détails
    const detailButtonCell = document.createElement('td');
    detailButtonCell.className = 'text-center';
    detailButtonCell.style.padding = '0.5rem';

    const detailButton = document.createElement('a');
    detailButton.href = `/detailsEtudiant/${userU.id}`;
    detailButton.className = 'btn btn-outline-danger btn-sm';
    detailButton.title = 'Voir les détails de l’étudiant';
    detailButton.innerHTML = '<i class="bi bi-eye-fill"></i>';
    detailButtonCell.style.maxWidth = '5px';

    detailButtonCell.appendChild(detailButton);
    row.appendChild(detailButtonCell);


        [`firstName`, 'lastName', 'role'].forEach(fieldName => {
          const cell = document.createElement('td');
          if (fieldName === 'role' && userU[fieldName] === 'etudiant') {
            makeRoleClean(userU[fieldName], cell);
          } else {
            cell.textContent = userU[fieldName];
          }
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

          if (fieldName === 'role' && userU[fieldName] === 'etudiant') {
            makeRoleClean(userU[fieldName], cell);
          } else {
            cell.textContent = userU[fieldName];
          }
          row.appendChild(cell);
        });
        const schoolYearCell = document.createElement('td');
        schoolYearCell.textContent = userU.schoolYear.yearFormat;
        row.appendChild(schoolYearCell);
        tbody.appendChild(row);
      }
    });
    table.appendChild(tbody);

    main.appendChild(table);
  } catch (error) {
    alert(`Une erreur est survenue lors de l'affichage des utilisateurs.`);
    console.error('Error rendering all users:', error);
  }
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

      // Amélioration de la cellule pour le bouton de détails
      const detailButtonCell = document.createElement('td');
      detailButtonCell.className = 'text-center';
      detailButtonCell.style.padding = '0.5rem';

      const detailButton = document.createElement('a');
      detailButton.href = `/detailsEtudiant/${userU.id}`;
      detailButton.className = 'btn btn-outline-danger btn-sm';
      detailButton.title = 'Voir les détails de l’étudiant';
      detailButton.innerHTML = '<i class="bi bi-eye-fill"></i>';
      detailButtonCell.style.maxWidth = '5px';

      detailButtonCell.appendChild(detailButton);
      row.appendChild(detailButtonCell);

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

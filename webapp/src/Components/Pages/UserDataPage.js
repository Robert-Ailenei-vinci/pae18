/* eslint-disable spaced-comment */
/* eslint-disable no-unused-vars */
import { getRememberMe, setAuthenticatedUser, setRememberMe , getAuthenticatedUser} from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';

const UserDataPage = () => {
  clearPage();
  renderPageTitle('Données Personelles');
  renderPersonnalInfoPage();
};



async function renderPersonnalInfoPage() {
    const user = getAuthenticatedUser();

    const main = document.querySelector('main');
    const ul = document.createElement('ul');
    ul.className = 'p-5';
    
    const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `${user.token}`,
        },
      };

    try {
      const responseContacts = await fetch(`http://localhost:3000/contacts/allContactsByUserId`, options);

      if (!responseContacts.ok) {
          throw new Error(`Failed to fetch contacts: ${responseContacts.statusText}`);
      }

      const contactsData = await responseContacts.json();

      console.log('Contacts : ', contactsData);
      console.log(user);
      const items = [
        { label: 'Nom de famille: ', value: user.lastName },
        { label: 'Prénom: ', value: user.firstName },
        { label: 'Email: ', value: user.email },
        { label: 'Numéro de Téléphone: ', value: user.phoneNum },
        { label: 'Date d\'enregistrement: ', value: user.registrationDate },
        { label: 'Année académique: ', value: user.schoolYear },
        { label: 'Role: ', value: user.role}
      ];
    
      items.forEach(item => {
        const li = document.createElement('li');
        li.textContent = item.label + item.value;
        ul.appendChild(li);
      });

      const submit = document.createElement('input');
      submit.value = 'Changer mes données personelles';
      submit.type = 'button';
      submit.className = 'btn btn-info';
      submit.addEventListener('click', () => {
        Navigate('/users/changeData');
      });

      // Creating table for contacts
      const table = document.createElement('table');
      table.className = 'table';
      const thead = document.createElement('thead');
      const tbody = document.createElement('tbody');
      const trHead = document.createElement('tr');

      ['Entreprise', 'Appelation', 'Adresse', 'Mail', 'N°Telephone', 'Etat', 'Lieu/Type de rencontre', 'Raison de refus'].forEach(text => {
        const th = document.createElement('th');
        th.textContent = text;
        trHead.appendChild(th);
      });

      thead.appendChild(trHead);
      table.appendChild(thead);

      contactsData.forEach(contact => {
        const tr = document.createElement('tr');

        // Fields from entreprise object
        ['tradeName', 'designation', 'address', 'email', 'phoneNumber'].forEach(key => {
          const td = document.createElement('td');
          td.textContent = contact.entreprise[key] || '-';
          tr.appendChild(td);
        });


        ['state', 'meetingType', 'reasonForRefusal'].forEach(key => {
          const td = document.createElement('td');
          td.textContent = contact[key] || '-';
          tr.appendChild(td);
        });

        tbody.appendChild(tr);

      });
      table.appendChild(tbody);

      const responseStages = await fetch(`http://localhost:3000/contacts/allContactsByUserId`, options);

      if (!responseStages.ok) {
          throw new Error(`Failed to fetch stages: ${responseStages.statusText}`);
      }
      

      main.appendChild(ul);
      main.appendChild(submit);

      main.appendChild(table);


      
    } catch (error) {
      console.error('An error occurred:', error.message);
    }


}

  Navbar();

  Navigate('/');


export default UserDataPage;

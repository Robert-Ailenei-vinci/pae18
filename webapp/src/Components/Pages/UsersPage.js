import '../../stylesheets/main.css';
import { getUsersWithNames } from '../../utils/auths';

const UsersPage = async () => {
    const main = document.querySelector('main');

    try {
        const users = await getUsersWithNames();
        const usersList = users.map(user => `<div>${user.first_name} ${user.last_name}</div>`).join('');

        main.innerHTML = `
            <h2>Liste de tous les utilisateurs</h2>
            <div class="users-list">${usersList}</div>
        `;
    } catch (error) {
        console.error('Error fetching users:', error);
        main.innerHTML = 'Une erreur s\'est produite lors de la récupération des utilisateurs.';
    }
};

export default UsersPage;
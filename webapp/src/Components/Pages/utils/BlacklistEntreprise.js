/* eslint-disable no-undef */

import {getAuthenticatedUser} from "../../../utils/auths";

const user = getAuthenticatedUser();

async function blacklistEntreprise(reason, id){
    console.log("blacklist entreprise");
    const options = {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `${user.token}`,
        },
        body: JSON.stringify({
          "reason_blacklist": reason,
          "id_entreprise": id
        }),
      };
      try {
        const responseContacts = await fetch(
            `http://localhost:3000/entreprise/blacklist`, options);
    
        if (!responseContacts.ok) {
          throw new Error(
              `Failed to update contacts: ${responseContacts.statusText}`);
        }
    
        const contactsData = await responseContacts.json();
        return contactsData;
      } catch (error) {
        throw new Error(
            `An error occurred while update contacts: ${error.message}`);
      }
}

export{
    blacklistEntreprise
}
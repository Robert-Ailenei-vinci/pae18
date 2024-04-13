
import {getAuthenticatedUser} from "../../../utils/auths";

const user = getAuthenticatedUser();

async function unblacklistEntreprise(id){
    console.log("unblacklist entreprise");
    const options = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `${user.token}`,
        },
        body: JSON.stringify({
          "id_entreprise": id
        }),
      };
      try {
        const responseContacts = await fetch(
            `http://localhost:3000/entreprise/unblacklist`, options);
    
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
    unblacklistEntreprise
}
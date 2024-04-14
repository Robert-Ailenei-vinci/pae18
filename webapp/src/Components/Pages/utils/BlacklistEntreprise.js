
import {getAuthenticatedUser} from "../../../utils/auths";

const user = getAuthenticatedUser();

async function blacklistEntreprise(reason, id, version){
    console.log("blacklist entreprise");
    const options = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `${user.token}`,
        },
        body: JSON.stringify({
          "reason_blacklist": reason,
          "id_entreprise": id,
          "version": version
        }),
      };
      try {
        const responseContacts = await fetch(
            `http://localhost:3000/entreprise/blacklist`, options);
    
       if(responseContacts.status === 400) {
        alert("Ceci a déjà été modifié par quelqu'un en meme temps que vous")
       }
    
        const contactsData = await responseContacts.json();
        return contactsData;
      } catch (error) {
        console.log(error);
      }
}

export{
    blacklistEntreprise
}
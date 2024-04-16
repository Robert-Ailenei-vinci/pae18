
import {getAuthenticatedUser} from "../../../utils/auths";

const user = getAuthenticatedUser();

async function unblacklistEntreprise(id, version){
    console.log("unblacklist entreprise");
    const options = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `${user.token}`,
        },
        body: JSON.stringify({
          "id_entreprise": id,
          "version": version
        }),
      };
      try {
        const responseContacts = await fetch(
            `http://localhost:3000/entreprise/unblacklist`, options);
    
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
    unblacklistEntreprise
}
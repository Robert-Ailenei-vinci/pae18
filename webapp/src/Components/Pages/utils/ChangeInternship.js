import {getAuthenticatedUser} from "../../../utils/auths";

const user = getAuthenticatedUser();

export default async function changeInternshipSubject(idContact, text) {
    console.log("changeInternshipSubject");
    console.log('text :',text)
    console.log('idcontact :', idContact);
    console.log(user);
    console.log('token: ', user.token);
    const options = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `${user.token}`,
      },
      body: JSON.stringify({
        "id_contact": idContact,
        "internship_project": text
      }),
      
    };
  
    try {
      const responseContacts = await fetch(
          `http://localhost:3000/stages/modifyStage`, options);
  
      if (!responseContacts.ok) {
        throw new Error(
            `Failed to modify stage: ${responseContacts.statusText}`);
      }
  
      const contactsData = await responseContacts.json();
      return contactsData;
      
    } catch (error) {
      throw new Error(
          `An error occurred while update contacts: ${error.message}`);
    }
  }


import {getAuthenticatedUser} from "../../../utils/auths";

const user = getAuthenticatedUser();

export default async function changeInternshipSubject(idContact, idUser, text) {
    console.log("changeInternshipSubject");
    const options = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `${user.token}`,
      },
      body: JSON.stringify({
        "id_contact": idContact,
        "id_user": idUser,
        "internship_project": text
      }),
    };
  
    try {
      const responseContacts = await fetch(
          `http://localhost:3000/stages/modify`, options);
  
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


import {getAuthenticatedUser} from "../../../utils/auths";

const user = getAuthenticatedUser();

export default async function changeInternshipSubject(idContact, text, version) {
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
        "internship_project": text,
        "version": version
      }),
      
    };
  
    try {
      const responseContacts = await fetch(`http://localhost:3000/stages/modifyStage`, options);
    
      if (responseContacts.status === 409) {
        alert('Le sujet de stage a déjà été modifé par quelquun en meme temps que vous');
        throw new Error('Conflict');
      }
    
      if (!responseContacts.ok) {
        throw new Error(`Unexpected response status: ${responseContacts.status}`);
      }
    
      const contactsData = await responseContacts.json();
      return contactsData;
    
    } catch (error) {
      console.log(`An error occurred while update internship: ${error.message}`);
    }
  }


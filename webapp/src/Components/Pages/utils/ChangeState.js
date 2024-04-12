/* eslint-disable no-undef */

import {getAuthenticatedUser} from "../../../utils/auths";
import baseURL from "../../../../config";
const user = getAuthenticatedUser();

async function meetContact(idContact, lieu,version) {
  console.log("meetcontact");
  const options = {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `${user.token}`,
    },
    body: JSON.stringify({
      "id_contact": idContact,
      "meetingType": lieu,
      "version":version,
    }),
  };

  try {
    const responseContacts = await fetch(
        `${baseURL}/contacts/meet`, options);

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

async function stopFollowContact(idContact,version) {
  console.log("stop follow");

  const options = {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `${user.token}`,
    },
    body: JSON.stringify({
      "id_contact": idContact,
      "version":version,
    }),
  };

  try {
    const responseContacts = await fetch(
        `${baseURL}/contacts/stopFollow`, options);

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

async function refuseContact(idContact, reason,version) {
  console.log("refude");

  const options = {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `${user.token}`,
    },
    body: JSON.stringify({
      "id_contact": idContact,
      "refusalReason": reason,
      "version":version,
    }),
  };

  try {
    const responseContacts = await fetch(
        `${baseURL}/contacts/refused`, options);

    if (!responseContacts.ok) {
      throw new Error(
          `Failed to update contacts: ${responseContacts.statusText}`);
    }

    const contactsData = await responseContacts.json();
    return contactsData;
  } catch (error) {
    console.log(
        `An error occurred while update contacts: ${error.message}`);
  }
}

export {
  meetContact,
  stopFollowContact,
  refuseContact
}

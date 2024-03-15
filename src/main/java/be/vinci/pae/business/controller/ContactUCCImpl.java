package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.*;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.BizExceptionNotFound;
import be.vinci.pae.services.ContactDAO;
import be.vinci.pae.services.UserDAO;
import jakarta.inject.Inject;

import java.util.List;

/**
 * This class is an implementation of the {@link ContactUCC} interface.
 */
public class ContactUCCImpl implements ContactUCC {

  @Inject
  private ContactDAO myContactDAO;

  @Override
  public ContactDTO createOne(UserDTO user, EntrepriseDTO entreprise, SchoolYearDTO schoolYear) {
    Contact contact = (Contact) myContactDAO.createOne(user, entreprise, schoolYear);
    if (contact == null) {
      return null;
    }
    return contact;
  }

  @Override
  public List<ContactDTO> getAllContactsByUserId(int userId) {
    return myContactDAO.getAllContactsByUserId(userId);
  }

  @Override
  public ContactDTO meetContact(int idContact, String meetingType, int userId) {

    if (myContactDAO.getOneContactById(idContact).getUserId() != userId) {
      throw new BizExceptionNotFound("Le contact n'appartient pas au user");
    }

    ContactDTO contactToReturn = myContactDAO.meetContact(idContact, meetingType);
    if (contactToReturn == null) {
      return null;
    }
    return contactToReturn;
  }

  @Override
  public ContactDTO stopFollowContact(int contactId, int userId) {

    if (myContactDAO.getOneContactById(contactId).getUserId() != userId) {
      throw new BizExceptionNotFound("Le contact n'appartient pas au user");
    }

    ContactDTO contactToReturn = myContactDAO.stopFollowContact(contactId);
    if (contactToReturn == null) {
      return null;
    }
    return contactToReturn;
  }

  @Override
  public ContactDTO refusedContact(int contactId, String refusalReason, int userId) {
    if (myContactDAO.getOneContactById(contactId).getUserId() != userId) {
      throw new BizExceptionNotFound("Le contact n'appartient pas au user");
    }
    ContactDTO contactToReturn = myContactDAO.refusedContact(contactId, refusalReason);
    if (contactToReturn == null) {
      return null;
    }
    return contactToReturn;
  }
}

package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.Contact;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.BizExceptionNotFound;
import be.vinci.pae.services.ContactDAO;
import be.vinci.pae.services.DALServices;
import jakarta.inject.Inject;
import java.util.List;

/**
 * This class is an implementation of the {@link ContactUCC} interface.
 */
public class ContactUCCImpl implements ContactUCC {

  @Inject
  private ContactDAO myContactDAO;
  @Inject
  private DALServices dalServices;

  @Override
  public ContactDTO createOne(UserDTO user, EntrepriseDTO entreprise, SchoolYearDTO schoolYear) {
    try {
      dalServices.startTransaction();
      Contact contact = (Contact) myContactDAO.createOne(user, entreprise, schoolYear);
      dalServices.commitTransaction();
      return contact;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public List<ContactDTO> getAllContactsByUserId(int userId) {
    return myContactDAO.getAllContactsByUserId(userId);
  }

  @Override
  public ContactDTO meetContact(int contactId, String meetingType, int userId) {
    try {
      dalServices.startTransaction();
      Contact contact = (Contact) myContactDAO.getOneContactById(contactId);
      if (contact.getUserId() != userId) {
        throw new BizExceptionNotFound("Le contact n'appartient pas au user");
      }
      if (!contact.checkMeet()) {
        throw new BizException("the contact cant be meet");
      }
      ContactDTO contactToReturn = myContactDAO.meetContact(contactId, meetingType);
      dalServices.commitTransaction();
      return contactToReturn;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public ContactDTO stopFollowContact(int contactId, int userId) {
    try {
      dalServices.startTransaction();
      Contact contact = (Contact) myContactDAO.getOneContactById(contactId);
      if (contact.getUserId() != userId) {
        throw new BizExceptionNotFound("Le contact n'appartient pas au user");
      }
      if (!contact.checkStopFollow()) {
        throw new BizException("the contact cant be stop follow");
      }
      ContactDTO contactToReturn = myContactDAO.stopFollowContact(contactId);
      dalServices.commitTransaction();
      return contactToReturn;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public ContactDTO refusedContact(int contactId, String refusalReason, int userId) {
    try {
      dalServices.startTransaction();
      Contact contact = (Contact) myContactDAO.getOneContactById(contactId);
      if (contact.getUserId() != userId) {
        throw new BizExceptionNotFound("Le contact n'appartient pas au user");
      }
      if (!contact.checkRefused()) {
        throw new BizException("the contact cant be refused");
      }
      ContactDTO contactToReturn = myContactDAO.refusedContact(contactId, refusalReason);
      dalServices.commitTransaction();
      return contactToReturn;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}

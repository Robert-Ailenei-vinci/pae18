package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.Contact;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.BizExceptionNotFound;
import be.vinci.pae.services.ContactDAO;
import be.vinci.pae.utils.LoggerUtil;
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
    if (!((User) user).checkIsStudent()) {
      LoggerUtil.logError("BizError", new BizException(
          "This user is not a student."));
      throw new BizException(
          "This user is not a student.");
    }
    Contact contact = (Contact) myContactDAO.createOne(user, entreprise, schoolYear);
    if (contact.checkUniqueUserEnterpriseSchoolYear(
        myContactDAO.getAllContactsByUserId(user.getId()), entreprise, schoolYear)) {
      LoggerUtil.logError("BizError", new BizException(
          "This user cannot have a contact with this enterprise for this year."));
      throw new BizException(
          "This user cannot have a contact with this enterprise for this year.");
    }
    return contact;
  }

  @Override
  public List<ContactDTO> getAllContactsByUserId(int userId) {
    return myContactDAO.getAllContactsByUserId(userId);
  }

  @Override
  public ContactDTO meetContact(int contactId, String meetingType, int userId) {

    Contact contact = (Contact) myContactDAO.getOneContactById(contactId);

    if (contact.getUserId() != userId) {
      LoggerUtil.logError("BizError", new BizException(
          "The contact does not belong to the user"));
      throw new BizExceptionNotFound("The contact does not belong to the user");
    }

    if (!contact.checkMeet()) {
      LoggerUtil.logError("BizError", new BizException(
          "The contact cannot be met"));
      throw new BizException("The contact cannot be met");
    }

    ContactDTO contactToReturn = myContactDAO.meetContact(contactId, meetingType);
    if (contactToReturn == null) {
      return null;
    }
    return contactToReturn;
  }

  @Override
  public ContactDTO stopFollowContact(int contactId, int userId) {

    Contact contact = (Contact) myContactDAO.getOneContactById(contactId);

    if (contact.getUserId() != userId) {
      throw new BizExceptionNotFound("The contact does not belong to the user");
    }

    if (!contact.checkStopFollow()) {
      throw new BizException("The contact cannot be stopped from being followed");
    }

    ContactDTO contactToReturn = myContactDAO.stopFollowContact(contactId);
    if (contactToReturn == null) {
      return null;
    }
    return contactToReturn;
  }

  @Override
  public ContactDTO refusedContact(int contactId, String refusalReason, int userId) {
    Contact contact = (Contact) myContactDAO.getOneContactById(contactId);

    if (contact.getUserId() != userId) {
      throw new BizExceptionNotFound("The contact does not belong to the user");
    }

    if (!contact.checkRefused()) {
      throw new BizException("The contact cannot be refused");
    }

    ContactDTO contactToReturn = myContactDAO.refusedContact(contactId, refusalReason);
    if (contactToReturn == null) {
      return null;
    }
    return contactToReturn;
  }
}

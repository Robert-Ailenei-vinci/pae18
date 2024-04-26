package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.Contact;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.SupervisorDTO;
import be.vinci.pae.business.domain.User;
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

  @Inject
  private SupervisorUCC mySupervisorUCC;

  @Inject
  private StageUCC myStageUCC;

  @Override
  public ContactDTO createOne(UserDTO user, EntrepriseDTO entreprise, SchoolYearDTO schoolYear) {
    try {
      dalServices.startTransaction();

      if (!((User) user).checkIsStudent()) {
        throw new BizException(
            "This user is not a student.");
      }
      if (entreprise.isBlacklisted()) {
        throw new BizException("Enterprise is blacklisted");
      }
      for (ContactDTO contactDTO : myContactDAO.getAllContactsByUserId(user.getId())
      ) {
        Contact tempContact = (Contact) contactDTO;

        if (tempContact.checkUniqueUserEnterpriseSchoolYear(entreprise.getId(),
            schoolYear.getId())) {
          throw new BizException(
              "This user cannot have a contact with this enterprise for this year. ");
        }
      }
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
    try {
      dalServices.startTransaction();
      List<ContactDTO> contacts = myContactDAO.getAllContactsByUserId(userId);
      dalServices.commitTransaction();
      return contacts;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public ContactDTO meetContact(int contactId, String meetingType, int userId, int version) {
    try {
      dalServices.startTransaction();

      Contact contact = (Contact) myContactDAO.getOneContactById(contactId);

      if (contact.getUserId() != userId) {
        throw new BizExceptionNotFound("The contact does not belong to the user");
      }

      if (!contact.meetContact(meetingType, version)) {
        throw new BizException("The contact cannot be met");
      }
      ContactDTO contactToReturn = myContactDAO.updateContact(contact);

      dalServices.commitTransaction();
      return contactToReturn;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public ContactDTO stopFollowContact(int contactId, int userId, int version) {
    try {
      dalServices.startTransaction();

      Contact contact = (Contact) myContactDAO.getOneContactById(contactId);

      if (contact.getUserId() != userId) {
        throw new BizExceptionNotFound("The contact does not belong to the user");
      }

      if (!contact.stopFollowContact(version)) {
        throw new BizException("The contact cannot be stopped from being followed");
      }

      ContactDTO contactToReturn = myContactDAO.updateContact(contact);

      dalServices.commitTransaction();
      return contactToReturn;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public ContactDTO refusedContact(int contactId, String refusalReason, int userId, int version) {
    try {
      dalServices.startTransaction();

      Contact contact = (Contact) myContactDAO.getOneContactById(contactId);

      if (contact.getUserId() != userId) {
        throw new BizExceptionNotFound("The contact does not belong to the user");
      }

      if (!contact.refuseContact(refusalReason, version)) {
        throw new BizException("The contact cannot be refused");
      }

      ContactDTO contactToReturn = myContactDAO.updateContact(contact);

      dalServices.commitTransaction();
      return contactToReturn;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public ContactDTO acceptContact(int contactId, int userId, int version, int supervisorId,
      String signatureDate, String internshipProject) {
    try {
      dalServices.startTransaction();

      Contact contact = (Contact) myContactDAO.getOneContactById(contactId);

      if (contact.getUserId() != userId) {
        throw new BizExceptionNotFound("The contact does not belong to the user");
      }

      if (!contact.acceptContact(version)) {
        throw new BizException("The contact cannot be accepted");
      }

      ContactDTO contactToReturn = myContactDAO.updateContact(contact);

      myContactDAO.cancelAllContact(contact);

      SupervisorDTO supervisor = mySupervisorUCC.getOneById(supervisorId);

      myStageUCC.createOne(contactToReturn, signatureDate, internshipProject,
          supervisor.getSupervisorId());

      dalServices.commitTransaction();
      return contactToReturn;


    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

    @Override
    public ContactDTO getOneContactById(int contactId) {
      try {
        dalServices.startTransaction();
        ContactDTO contact = myContactDAO.getOneContactById(contactId);
        dalServices.commitTransaction();
        return contact;
      } catch (Exception e) {
        dalServices.rollbackTransaction();
        throw e;
      }
    }

    /**
   * set all internships of a contact to refusé if an entreprise is blacklisted.
   *
   * @param idEntreprise the id of the entreprise.
   * @return true if the internships are canceled.
   */
  @Override
  public boolean cancelInternshipsBasedOnEntreprise(int idEntreprise) {

    try {
      dalServices.startTransaction();
      boolean result = myContactDAO.cancelInternshipsBasedOnEntrepriseId(idEntreprise);
      dalServices.commitTransaction();
      return result;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}

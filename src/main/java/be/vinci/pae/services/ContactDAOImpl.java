package be.vinci.pae.services;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.FatalError;
import be.vinci.pae.exception.OptimisticLockException;
import be.vinci.pae.exception.StageNotFoundException;
import be.vinci.pae.utils.LoggerUtil;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents an implementation of the {@link ContactDAO} interface.
 */
public class ContactDAOImpl implements ContactDAO {

  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALBackServices dalBackServices;
  @Inject
  private EntrepriseDAO entrepriseDAO;

  @Override
  public ContactDTO createOne(UserDTO user, EntrepriseDTO entreprise, SchoolYearDTO schoolYear) {
    try (PreparedStatement preparedStatement = dalBackServices.getPreparedStatement(
        "INSERT INTO pae.contacts "
            + "(state, id_contact, _user, entreprise, school_year, "
            + "reason_for_refusal, meeting_type, _version)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?, 0)"
    )) {
      int contactId = nextItemId();
      preparedStatement.setString(1, "initie");
      preparedStatement.setInt(2, contactId);
      preparedStatement.setInt(3, user.getId());
      preparedStatement.setInt(4, entreprise.getId());
      preparedStatement.setInt(5, schoolYear.getId());
      preparedStatement.setString(6, null);
      preparedStatement.setString(7, null);
      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        LoggerUtil.logInfo("create one contact with id " + contactId);
        return getOneContactByStageId(contactId);
      }
    } catch (Exception e) {
      LoggerUtil.logError("Error processing result set", e);
      throw new FatalError("Error processing result set", e);
    }
    return null;
  }

  @Override
  public List<ContactDTO> getAllContactsByUserId(int userId) {
    PreparedStatement getAllContacts = dalBackServices.getPreparedStatement(
        "SELECT * FROM pae.contacts WHERE _user = ?");
    List<ContactDTO> contacts = new ArrayList<>();
    try {
      getAllContacts.setInt(1, userId);
      try (ResultSet rs = getAllContacts.executeQuery()) {
        while (rs.next()) {
          ContactDTO contact;
          contact = getContactMethodFromDB(rs);
          contacts.add(contact);
          LoggerUtil.logInfo("get all contact for the user with id " + userId);
        }
      }
    } catch (Exception e) {
      LoggerUtil.logError("Error processing result set", e);
      throw new FatalError("Error processing result set", e);
    }
    return contacts;
  }

  @Override
  public ContactDTO getOneContactByStageId(int stageId) {
    PreparedStatement getOneContact = dalBackServices.getPreparedStatement(
        "SELECT * FROM pae.contacts WHERE id_contact = ?");
    try {
      getOneContact.setInt(1, stageId);
      try (ResultSet rs = getOneContact.executeQuery()) {
        if (rs.next()) {
          LoggerUtil.logInfo("get 1 contact by stage id" + stageId);
          return getContactMethodFromDB(rs);
        }
      }
    } catch (Exception e) {
      LoggerUtil.logError("Stage not found with id : " + stageId, e);
      throw new StageNotFoundException("Stage not found with id :" + stageId, e);
    }
    return null;
  }

  private ContactDTO getContactMethodFromDB(ResultSet rs) {
    ContactDTO contact = myDomainFactory.getContact();
    try {
      contact.setId(rs.getInt("id_contact"));
      contact.setState(rs.getString("state"));
      contact.setUserId(rs.getInt("_user"));
      contact.setEntrepriseId(rs.getInt("entreprise"));
      contact.setSchoolYearId(rs.getInt("school_year"));
      contact.setReasonForRefusal(rs.getString("reason_for_refusal"));
      contact.setMeetingType(rs.getString("meeting_type"));
      contact.setEntreprise(entrepriseDAO.getOne(rs.getInt("entreprise")));
      contact.setVersion(rs.getInt("_version"));
    } catch (Exception e) {
      LoggerUtil.logError("Error processing result set", e);
      throw new FatalError("Error processing result set", e);
    }
    return contact;
  }

  private int nextItemId() {
    String sql = "SELECT MAX(id_contact) FROM pae.contacts";
    try (PreparedStatement stmt = dalBackServices.getPreparedStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
      if (rs.next()) {

        return rs.getInt(1) + 1;
      }
    } catch (Exception e) {
      LoggerUtil.logError("Error processing result set", e);
      throw new FatalError("Error processing result set", e);
    }
    return 1;
  }

  @Override
  public ContactDTO updateContact(ContactDTO contactDTO) {
    StringBuilder sql = new StringBuilder("UPDATE pae.contacts SET ");
    List<Object> parameters = new ArrayList<>();

    if (!Objects.equals(contactDTO.getState(), "")) {
      sql.append("state = ?, ");
      parameters.add(contactDTO.getState());
    }

    if (!Objects.equals(contactDTO.getReasonForRefusal(), "") && contactDTO.getState()
        .equals("refuse")) {
      sql.append("reason_for_refusal = ?, ");
      parameters.add(contactDTO.getReasonForRefusal());
    }

    if (!Objects.equals(contactDTO.getMeetingType(), "") && contactDTO.getState()
        .equals("rencontre")) {
      sql.append("meeting_type = ?, ");
      parameters.add(contactDTO.getMeetingType());
    }

    // Remove the last comma and space
    sql.delete(sql.length() - 2, sql.length());

    sql.append(", _version = _version + 1 WHERE id_contact = ? AND _version = ?;");

    parameters.add(contactDTO.getId());
    parameters.add(contactDTO.getVersion());

    try (PreparedStatement stmt = dalBackServices.getPreparedStatement(sql.toString())) {
      for (int i = 0; i < parameters.size(); i++) {
        stmt.setObject(i + 1, parameters.get(i));
      }
      if (stmt.executeUpdate() == 0) {
        LoggerUtil.logError("Contact was updated by another transaction",
            new OptimisticLockException(""));
        throw new OptimisticLockException("Contact was updated by another transaction");
      }

      LoggerUtil.logInfo("Contact nr" + contactDTO.getId() + " updated!");

    } catch (Exception e) {
      LoggerUtil.logError("Error processing result set", e);
      throw new FatalError("Error processing result set", e);
    }
    return getOneContactByStageId(contactDTO.getId());
  }

  @Override
  public ContactDTO getOneContactById(int idContact) {
    PreparedStatement getOneContact = dalBackServices.getPreparedStatement(
        "SELECT * FROM pae.contacts WHERE id_contact = ?");
    try {
      getOneContact.setInt(1, idContact);
      try (ResultSet rs = getOneContact.executeQuery()) {
        if (rs.next()) {
          LoggerUtil.logInfo("get one contact by id" + idContact);
          return getContactMethodFromDB(rs);
        }
      }
    } catch (Exception e) {
      LoggerUtil.logError("Error processing result set", e);
      throw new FatalError("Error processing result set", e);
    }
    return null;
  }

  @Override
  public List<ContactDTO> getAllContactsByEnterpriseId(int enterpriseId) {
    PreparedStatement getAllContacts = dalBackServices.getPreparedStatement(
        "SELECT * FROM pae.contacts WHERE entreprise = ?");
    List<ContactDTO> contacts = new ArrayList<>();
    try {
      getAllContacts.setInt(1, enterpriseId);
      try (ResultSet rs = getAllContacts.executeQuery()) {
        while (rs.next()) {
          ContactDTO contact;
          contact = getContactMethodFromDB(rs);
          contacts.add(contact);
          LoggerUtil.logInfo("get all contact for the enterprise with id " + enterpriseId);
        }
      }
    } catch (Exception e) {
      LoggerUtil.logError("Error processing result set", e);
      throw new FatalError("Error processing result set", e);
    }
    return contacts;
  }


}

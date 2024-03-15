package be.vinci.pae.services;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.FatalError;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an implementation of the {@link ContactDAO} interface.
 */
public class ContactDAOImpl implements ContactDAO {

  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALServices dalServices;
  @Inject
  private EntrepriseDAO entrepriseDAO;

  @Override
  public ContactDTO createOne(UserDTO user, EntrepriseDTO entreprise, SchoolYearDTO schoolYear) {
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "INSERT INTO pae.contacts "
            + "(state, id_contact, \"user\", entreprise, school_year, reason_for_refusal, meeting_type)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?)"
    )) {
      int contactId = nextItemId();
      preparedStatement.setString(1, "initié");
      preparedStatement.setInt(2, contactId);
      preparedStatement.setInt(3, user.getId());
      preparedStatement.setInt(4, entreprise.getId());
      preparedStatement.setInt(5, schoolYear.getId());
      preparedStatement.setString(6, null);
      preparedStatement.setString(7, null);
      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        return getOneContactByStageId(contactId);
      }
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
    return null;
  }

  @Override
  public List<ContactDTO> getAllContactsByUserId(int userId) {
    PreparedStatement getAllContacts = dalServices.getPreparedStatement(
        "SELECT * FROM pae.contacts WHERE \"user\" = ?");
    List<ContactDTO> contacts = new ArrayList<>();
    try {
      getAllContacts.setInt(1, userId);
      try (ResultSet rs = getAllContacts.executeQuery()) {
        while (rs.next()) {
          ContactDTO contact;
          contact = getContactMethodFromDB(rs);
          contacts.add(contact);
        }
      }
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
    return contacts;
  }

  @Override
  public ContactDTO getOneContactByStageId(int stageId) {
    PreparedStatement getOneContact = dalServices.getPreparedStatement(
        "SELECT * FROM pae.contacts WHERE id_contact = ?");
    try {
      getOneContact.setInt(1, stageId);
      try (ResultSet rs = getOneContact.executeQuery()) {
        if (rs.next()) {
          return getContactMethodFromDB(rs);
        }
      }
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
    return null;
  }

  private ContactDTO getContactMethodFromDB(ResultSet rs) {
    ContactDTO contact = myDomainFactory.getContact();
    try {
      contact.setId(rs.getInt("id_contact"));
      contact.setState(rs.getString("state"));
      contact.setUserId(rs.getInt("\"user\""));
      contact.setEntrepriseId(rs.getInt("entreprise"));
      contact.setSchoolYearId(rs.getInt("school_year"));
      contact.setReasonForRefusal(rs.getString("reason_for_refusal"));
      contact.setMeetingType(rs.getString("meeting_type"));
      contact.setEntreprise(entrepriseDAO.getOne(rs.getInt("entreprise")));
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
    return contact;
  }

  @Override
  public int nextItemId() {
    String sql = "SELECT MAX(id_contact) FROM pae.contacts";
    try (PreparedStatement stmt = dalServices.getPreparedStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
      if (rs.next()) {
        return rs.getInt(1) + 1;
      }
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
    return 1;
  }

  public ContactDTO meetContact(int idContact, String meetingType) {
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "UPDATE pae.contacts SET state = 'rencontré', meeting_type = ? WHERE id_contact = ?"
    )) {
      preparedStatement.setString(1, meetingType);
      preparedStatement.setInt(2, idContact);
      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        try (PreparedStatement selectStatement = dalServices.getPreparedStatement(
            "SELECT * FROM pae.contacts WHERE id_contact = ?"
        )) {
          selectStatement.setInt(1, idContact);
          try (ResultSet rs = selectStatement.executeQuery()) {
            if (rs.next()) {
              return getContactMethodFromDB(rs);
            }
          }
        }
      }
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
    return null;
  }

  @Override
  public ContactDTO stopFollowContact(int contactId) {
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "UPDATE pae.contacts SET state = 'suivis stoppé' WHERE id_contact = ?"
    )) {
      preparedStatement.setInt(1, contactId);
      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        try (PreparedStatement selectStatement = dalServices.getPreparedStatement(
            "SELECT * FROM pae.contacts WHERE id_contact = ?"
        )) {
          selectStatement.setInt(1, contactId);
          try (ResultSet rs = selectStatement.executeQuery()) {
            if (rs.next()) {
              return getContactMethodFromDB(rs);
            }
          }
        }
      }
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
    return null;
  }

  @Override
  public ContactDTO refusedContact(int contactId, String refusalReason) {
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "UPDATE pae.contacts SET state = 'refusé', reason_for_refusal = ? WHERE id_contact = ?"
    )) {
      preparedStatement.setString(1, refusalReason);
      preparedStatement.setInt(2, contactId);
      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        try (PreparedStatement selectStatement = dalServices.getPreparedStatement(
            "SELECT * FROM pae.contacts WHERE id_contact = ?"
        )) {
          selectStatement.setInt(1, contactId);
          try (ResultSet rs = selectStatement.executeQuery()) {
            if (rs.next()) {
              return getContactMethodFromDB(rs);
            }
          }
        }
      }
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
    return null;
  }

  @Override
  public ContactDTO getOneContactById(int idContact) {
    PreparedStatement getOneContact = dalServices.getPreparedStatement(
        "SELECT * FROM pae.contacts WHERE id_contact = ?");
    try {
      getOneContact.setInt(1, idContact);
      try (ResultSet rs = getOneContact.executeQuery()) {
        if (rs.next()) {
          return getContactMethodFromDB(rs);
        }
      }
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
    return null;
  }
}

package be.vinci.pae.services;

import static be.vinci.pae.services.utils.utils.paramStatment;

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
  private DALServices dalServices;
  @Inject
  private EntrepriseDAO entrepriseDAO;


  @Override
  public ContactDTO createOne(UserDTO user, EntrepriseDTO entreprise, SchoolYearDTO schoolYear) {
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(
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
        "SELECT * FROM pae.contacts WHERE _user = ?");
    List<ContactDTO> contacts = new ArrayList<>();
    try {
      getAllContacts.setInt(1, userId);
      try (ResultSet rs = getAllContacts.executeQuery()) {
        while (rs.next()) {
          ContactDTO contact;
          contact = getContactMethodFromDB(rs);
          contacts.add(contact);
          LoggerUtil.logInfo("get all contact");
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
    getVersionFromDB(contactDTO);
    parameters.add(contactDTO.getVersion());

    try (PreparedStatement stmt = dalServices.getPreparedStatement(sql.toString())) {
      paramStatment(parameters, stmt);

      if (stmt.executeUpdate() == 0) {
        throw new OptimisticLockException("Contact was updated by another transaction");
      }

      System.out.println("Contact updated");
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
    return getOneContactByStageId(contactDTO.getId());
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

  private void getVersionFromDB(ContactDTO contact) {
    try (PreparedStatement versionStmt = dalServices.getPreparedStatement(
        "SELECT _version FROM pae.contacts WHERE id_contact = ?")) {
      versionStmt.setInt(1, contact.getId());
      try (ResultSet rs = versionStmt.executeQuery()) {
        if (rs.next()) {
          // Update the version of the UserDTO object in memory
          contact.setVersion(rs.getInt("_version"));
          System.out.println(contact.getVersion());
        }
      }
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
  }

}

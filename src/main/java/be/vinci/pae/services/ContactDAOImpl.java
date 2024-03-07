package be.vinci.pae.services;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.UserDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class represents an implementation of the {@link ContactDAO} interface.
 */
public class ContactDAOImpl implements ContactDAO {

  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALServices dalServices;

  @Override
  public ContactDTO createOne(UserDTO user, EntrepriseDTO entreprise, SchoolYearDTO schoolYear) {
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "INSERT INTO pae.contacts "
            +
            "(state, id_contact, user, entreprise, school_year, reason_for_refusal, meeting_type)"
            +
            "VALUES (?, ?, ?, ?, ?, ?, ?)"
    )) {
      preparedStatement.setString(1, "initié");
      preparedStatement.setInt(2, nextItemId());
      preparedStatement.setInt(3, user.getId());
      preparedStatement.setInt(4, entreprise.getId());
      preparedStatement.setInt(5, schoolYear.getId());
      preparedStatement.setString(6, null);
      preparedStatement.setString(7, null);
      try (ResultSet rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          return getContactMethodFromDB(rs);
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  private ContactDTO getContactMethodFromDB(ResultSet rs) {
    ContactDTO contact = myDomainFactory.getContact();
    try {
      contact.setState(rs.getString("state"));
      contact.setId(rs.getInt("id_contact"));
      contact.setUserId(rs.getInt("user"));
      contact.setEntrepriseId(rs.getInt("entreprise"));
      contact.setSchoolYearId(rs.getInt("school_year"));
      contact.setReasonForRefusal(rs.getString("reason_for_refusal"));
      contact.setMeetingType(rs.getString("meeting_type"));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return contact;
  }

  @Override
  public int nextItemId() {
    String sql = "SELECT MAX(id) FROM pae.contacts";
    try (PreparedStatement stmt = dalServices.getPreparedStatement(sql);
        ResultSet rs = stmt.executeQuery(sql)) {
      if (rs.next()) {
        return rs.getInt(1) + 1;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return 1;
  }

  public ContactDTO meetContact(int id_contact, String meetingType) {
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "UPDATE pae.contact SET state = 'rencontré', meeting_type = ? WHERE id_contact = ?"
    )) {
      preparedStatement.setString(1, meetingType);
      preparedStatement.setInt(2, id_contact);
      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        try (PreparedStatement selectStatement = dalServices.getPreparedStatement(
            "SELECT * FROM pae.contact WHERE id_contact = ?"
        )) {
          selectStatement.setInt(1, id_contact);
          try (ResultSet rs = selectStatement.executeQuery()) {
            if (rs.next()) {
              return getContactMethodFromDB(rs);
            }
          }
        }
      }
    } catch (Exception e) {
      System.out.println("Erreur lors de la mise à jour du contact : " + e.getMessage());
    }
    return null;
  }
}

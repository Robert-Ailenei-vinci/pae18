package be.vinci.pae.services;

import be.vinci.pae.business.domain.*;
import jakarta.inject.Inject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ContactDAOImpl implements ContactDAO{
    @Inject
    private DomainFactory myDomainFactory;
    @Inject
    private DALServices dalServices;

    @Override
    public ContactDTO createOne(int userId, int entrepriseId, int schoolYearId) {
        try(PreparedStatement preparedStatement = dalServices.getPreparedStatement(
                "INSERT INTO pae.contacts (state, id_contact, user, entreprise, school_year, reason_for_refusal, meeting_type)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)"
        )) {
            preparedStatement.setString(1, "initié");
            preparedStatement.setInt(2, nextItemId());
            preparedStatement.setInt(3, userId);
            preparedStatement.setInt(4, entrepriseId);
            preparedStatement.setInt(5, schoolYearId);
            preparedStatement.setString(6, null);
            preparedStatement.setString(7, null);
            try(ResultSet rs = preparedStatement.executeQuery()) {
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
}

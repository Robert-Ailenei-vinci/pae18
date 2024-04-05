package be.vinci.pae.services;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SupervisorDTO;
import be.vinci.pae.exception.FatalError;
import be.vinci.pae.exception.SupervisorNotFoundException;
import be.vinci.pae.utils.LoggerUtil;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class represents an implementation of the {@link SupervisorDAO} interface.
 */
public class SupervisorDAOImpl implements SupervisorDAO {

  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALBackServices dalBackServices;
  @Inject
  private EntrepriseDAO entrepriseDAO;

  @Override
  public SupervisorDTO getOneById(int id) {
    try (PreparedStatement preparedStatement = dalBackServices.getPreparedStatement(
            "SELECT * FROM pae.internship_supervisor WHERE id_supervisor = ?")) {
      preparedStatement.setInt(1, id);
      try (ResultSet rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          LoggerUtil.logInfo("Supervisor getone with id " + id);
          return getSupervisorMethodFromDB(rs);
        }
      }
    } catch (Exception e) {
      LoggerUtil.logError("No Supervisor found with id : ", e);
      throw new SupervisorNotFoundException("Supervisor not found with this id ", e);
    }
    return null;
  }

  @Override
  public SupervisorDTO createOne(String last_name, String first_name, int id_entreprise, String email, String numero) {
    try (PreparedStatement preparedStatement = dalBackServices.getPreparedStatement(
            "INSERT INTO pae.internship_supervisor "
                    + "(id_supervisor, last_name, first_name, entreprise, phone_number, "
                    + "email)"
                    + "VALUES (?, ?, ?, ?, ?, ?)"
    )) {
      int supervisorId = nextItemId();   //
      preparedStatement.setInt(1, supervisorId);
      preparedStatement.setString(2, last_name);
      preparedStatement.setString(3, first_name);
      preparedStatement.setInt(4, id_entreprise);
      preparedStatement.setString(5, email);
      preparedStatement.setString(6, numero);
      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        LoggerUtil.logInfo("supervisor createOne with id " + supervisorId);
        return getOneById(supervisorId);
      }
    } catch (Exception e) {
      LoggerUtil.logError("Error processing result set", e);
      throw new FatalError("Error processing result set", e);
    }
    return null;
  }

  private SupervisorDTO getSupervisorMethodFromDB(ResultSet rs) {
    SupervisorDTO supervisor = myDomainFactory.getSupervisor();
    try {
      supervisor.setSupervisorId(rs.getInt("id_supervisor"));
      supervisor.setLastName(rs.getString("last_name"));
      supervisor.setFirstName(rs.getString("first_name"));
      supervisor.setEntrepriseId(rs.getInt("entreprise"));
      supervisor.setPhoneNumber(rs.getString("phone_number"));
      supervisor.setEmail(rs.getString("email"));
      supervisor.setEntreprise(entrepriseDAO.getOne(rs.getInt("entreprise")));
    } catch (Exception e) {
      LoggerUtil.logError("Error while getting supervisor from database", e);
      throw new FatalError("Error while getting supervisor from database", e);
    }
    return supervisor;
  }

  private int nextItemId() {
    String sql = "SELECT MAX(id_supervisor) FROM pae.internship_supervisor";
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
}

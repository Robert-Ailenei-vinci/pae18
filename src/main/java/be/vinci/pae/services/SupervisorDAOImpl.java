package be.vinci.pae.services;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SupervisorDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.EntrepriseNotFoundException;
import be.vinci.pae.exception.FatalError;
import be.vinci.pae.exception.SupervisorNotFoundException;
import be.vinci.pae.utils.LoggerUtil;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

  private Connection connection;

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
      throw new SupervisorNotFoundException("Supervisor not found with this id " + id, e);
    }
    return null;
  }

  @Override
  public List<SupervisorDTO> getAll(int entrepriseId) {

    try (PreparedStatement getAll = dalBackServices.getPreparedStatement(
        "SELECT * FROM pae.internship_supervisor WHERE entreprise = ? ")) {
      getAll.setInt(1, entrepriseId);

      List<SupervisorDTO> supervisorDTOS = new ArrayList<>();
      try (ResultSet rs = getAll.executeQuery()) {
        while (rs.next()) {
          SupervisorDTO supervisorDTO;
          supervisorDTO = getSupervisorMethodFromDB(rs);
          supervisorDTOS.add(supervisorDTO);

        }
        LoggerUtil.logInfo("supervisor getAll in DAO");

        return supervisorDTOS;
      } catch (Exception e) {
        throw new FatalError("Error processing result set", e);
      }

    } catch (Exception e) {
      throw new EntrepriseNotFoundException("Entreprise not found with this id " + entrepriseId, e);
    }
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
      throw new FatalError("Error while getting supervisor from database", e);
    }
    return supervisor;
  }

  public SupervisorDTO createOne(UserDTO user, EntrepriseDTO entreprise) throws SQLException {
    try (PreparedStatement preparedStatement = dalBackServices.getPreparedStatement(
            "INSERT INTO internship_supervisor "
                    + "(last_name, first_name, entreprise, phone_number, email)"
                    + " VALUES (?, ?, ?, ?, ?) RETURNING id_supervisor"
    )) {
      int supervisorId = nextItemId();
      preparedStatement.setString(1, "initie");
      preparedStatement.setInt(2, supervisorId);
      preparedStatement.setInt(3, user.getId());
      preparedStatement.setInt(4, entreprise.getId());
      preparedStatement.setString(5, null);
      preparedStatement.setString(6, null);

      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        LoggerUtil.logInfo("create one supervisor with id " + supervisorId);
        return getOneById(supervisorId);
      }
    } catch (SQLException e) {
      throw new FatalError("Error processing result set", e);
    }
    return null;
  }

  private int nextItemId() throws SQLException {
    String sql = "SELECT MAX(id_supervisor) FROM internship_supervisor";
    try (PreparedStatement stmt = connection.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
      if (rs.next()) {
        int maxId = rs.getInt(1);
        return maxId + 1;
      }
    } catch (SQLException e) {
      throw new FatalError("Error processing result set", e);
    }
    return 1;
  }
}

package be.vinci.pae.services;

import be.vinci.pae.business.domain.DomainFactory;
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
      throw new SupervisorNotFoundException("Supervisor not found with this id " + id, e);
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
      throw new FatalError("Error while getting supervisor from database", e);
    }
    return supervisor;
  }
}

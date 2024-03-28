package be.vinci.pae.services;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.StageDTO;
import be.vinci.pae.exception.FatalError;
import be.vinci.pae.exception.StageNotFoundException;
import be.vinci.pae.utils.LoggerUtil;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class represents an implementation of the {@link StageDAO} interface.
 */
public class StageDAOImpl implements StageDAO {

  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALBackServices dalBackServices;
  @Inject
  private ContactDAO contactDAO;
  @Inject
  private UserDAO userDAO;
  @Inject
  private SupervisorDAO supervisorDAO;
  @Inject
  private SchoolYearDAO schoolYearDAO;

  @Override
  public StageDTO getOneStageByUserId(int userId) {
    try (PreparedStatement preparedStatement = dalBackServices.getPreparedStatement(
        "SELECT * FROM pae.stages WHERE _user = ?")) {
      preparedStatement.setInt(1, userId);
      try (ResultSet rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          LoggerUtil.logInfo("stage getone with id " + userId);
          return getStageMethodFromDB(rs);
        }
      }
    } catch (Exception e) {
      LoggerUtil.logError("No Stage found for userId : " + userId, e);
      throw new StageNotFoundException("Pas de stage avec l'id de user suivant : " + userId, e);
    }
    return null;
  }

  private StageDTO getStageMethodFromDB(ResultSet rs) {
    StageDTO stage = myDomainFactory.getStage();
    try {
      stage.setContactId(rs.getInt("contact"));
      stage.setSignatureDate(rs.getString("signature_date"));
      stage.setInternshipProject(rs.getString("internship_project"));
      stage.setSupervisorId(rs.getInt("supervisor"));
      stage.setUserId(rs.getInt("_user"));
      stage.setSchoolYearId(rs.getInt("school_year"));
      stage.setContact(contactDAO.getOneContactByStageId(rs.getInt("contact")));
      stage.setUser(userDAO.getOne(rs.getInt("_user")));
      stage.setSupervisor(supervisorDAO.getOneById(rs.getInt("supervisor")));
      stage.setSchoolYear(schoolYearDAO.getOne(rs.getInt("school_year")));
    } catch (Exception e) {
      LoggerUtil.logError("Error fetching stage", e);
      throw new FatalError("Erreur lors de la récupération du stage");
    }
    return stage;
  }

}

package be.vinci.pae.services;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.StageDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StageDAOImpl implements StageDAO{
  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALServices dalServices;
  @Override
  public StageDTO getOneStageByUserId(int userId) {
    try (PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.stages WHERE _user = ?")) {
      preparedStatement.setInt(1, userId);
      try (ResultSet rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          return getStageMethodFromDB(rs);
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  private StageDTO getStageMethodFromDB(ResultSet rs) {
    StageDTO stage = myDomainFactory.getStage();
    try {
      stage.setContact(rs.getInt("contact"));
      stage.setSignatureDate(rs.getString("signature_date"));
      stage.setInternshipProject(rs.getString("internship_project"));
      stage.setSupervisor(rs.getInt("supervisor"));
      stage.setUser(rs.getInt("_user"));
      stage.setSchoolYear(rs.getInt("school_year"));

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return stage;
  }

}

package be.vinci.pae.services;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.exception.FatalError;
import be.vinci.pae.exception.SchoolYearNotFoundException;
import be.vinci.pae.utils.LoggerUtil;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class represents an implementation of the {@link SchoolYearDAO} interface.
 */
public class SchoolYearDAOImpl implements SchoolYearDAO {

  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALBackServices dalBackServices;

  @Override
  public SchoolYearDTO getOne(int id) {
    try (PreparedStatement preparedStatement = dalBackServices.getPreparedStatement(
        "SELECT * FROM pae.school_years WHERE id_year = ?")) {
      preparedStatement.setInt(1, id);
      try (ResultSet rs = preparedStatement.executeQuery()) {

        if (rs.next()) {
          LoggerUtil.logInfo("schoolyear getone + id : " + id);
          return getSchoolYearMethodFromDB(rs);
        }
      }
    } catch (Exception e) {
      throw new SchoolYearNotFoundException("No Schoolyear for input id : " + id + e.getMessage());
    }
    return null;
  }

  private SchoolYearDTO getSchoolYearMethodFromDB(ResultSet rs) {
    SchoolYearDTO schoolYear = myDomainFactory.getSchoolYear();
    try {
      schoolYear.setId(rs.getInt("id_year"));
      schoolYear.setYearFormat(rs.getString("years_format"));
    } catch (Exception e) {
      throw new FatalError("Error in getSchoolYearMethodFromDB" + e.getMessage());
    }
    return schoolYear;
  }
}

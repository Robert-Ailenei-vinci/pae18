package be.vinci.pae.services;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.exception.FatalError;
import be.vinci.pae.exception.SchoolYearNotFoundException;
import be.vinci.pae.utils.LoggerUtil;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

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

  @Override
  public SchoolYearDTO getCurrentSchoolYear() {
    // Construct the school year format using the buildYear() method
    String schoolYearFormat = buildYear();

    try (PreparedStatement preparedStatement = dalBackServices.getPreparedStatement(
        "SELECT * FROM pae.school_years WHERE years_format = ?")) {
      preparedStatement.setString(1, schoolYearFormat);
      try (ResultSet rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          LoggerUtil.logInfo("Current school year found: " + schoolYearFormat);
          return getSchoolYearMethodFromDB(rs);
        }
      }
    } catch (Exception e) {
      throw new FatalError("Error while fetching current school year: " + e.getMessage());
    }

    // Handle the case where the current school year is not found

    throw new SchoolYearNotFoundException("Current school year not found: "
      + schoolYearFormat);
  }


  @Override
  public String buildYear() {
    int year;
    if (LocalDate.now().getMonth().compareTo(Month.SEPTEMBER) < 0) {
      year = LocalDate.now().getYear() - 1;
    } else {
      year = LocalDate.now().getYear();
    }
    LoggerUtil.logInfo("build year method");
    return year + "-" + (year + 1);
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

  @Override
  public List<SchoolYearDTO> getAllSchoolYears() {
    PreparedStatement preparedStatement = dalBackServices.getPreparedStatement(
        "SELECT * FROM pae.school_years");
    List<SchoolYearDTO> schoolYears = new ArrayList<>();
    try (ResultSet rs = preparedStatement.executeQuery()) {
      while (rs.next()) {
        SchoolYearDTO schoolYear = getSchoolYearMethodFromDB(rs);
        schoolYears.add(schoolYear);
      }
    } catch (Exception e) {
      throw new FatalError("Error processing result set", e);
    }
    LoggerUtil.logInfo("schoolyear getAll");
    return schoolYears;
  }
}

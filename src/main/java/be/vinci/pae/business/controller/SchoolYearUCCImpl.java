package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.SchoolYearDAO;
import jakarta.inject.Inject;
import java.util.List;

/**
 * This class is an implementation of the {@link SchoolYearUCC} interface.
 */
public class SchoolYearUCCImpl implements SchoolYearUCC {

  @Inject
  private SchoolYearDAO mySchoolYearDAO;
  @Inject
  private DALServices dalServices;

  @Override
  public SchoolYearDTO getOne(int schoolYearId) {
    try {
      // Start a new transaction
      dalServices.startTransaction();

      // Retrieve the SchoolYearDTO from the DAO
      SchoolYearDTO schoolYearDTO = mySchoolYearDAO.getOne(schoolYearId);

      // Commit the transaction
      dalServices.commitTransaction();

      return schoolYearDTO;
    } catch (Exception e) {
      // Rollback the transaction in case of an error
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public SchoolYearDTO getCurrentSchoolYear() {
    try {
      // Start a new transaction
      dalServices.startTransaction();

      // Retrieve the current school year from the DAO
      SchoolYearDTO schoolYearDTO = mySchoolYearDAO.getCurrentSchoolYear();

      // Commit the transaction
      dalServices.commitTransaction();

      return schoolYearDTO;
    } catch (Exception e) {
      // Rollback the transaction in case of an error
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public List<SchoolYearDTO> getAllSchoolYears() {
    try {
      // Start a new transaction
      dalServices.startTransaction();

      // Retrieve all school years from the DAO
      List<SchoolYearDTO> schoolYears = mySchoolYearDAO.getAllSchoolYears();

      // Commit the transaction
      dalServices.commitTransaction();

      return schoolYears;
    } catch (Exception e) {
      // Rollback the transaction in case of an error
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}

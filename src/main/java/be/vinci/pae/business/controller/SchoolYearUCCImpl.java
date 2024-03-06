package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.services.SchoolYearDAO;
import jakarta.inject.Inject;

/**
 * This class is an implementation of the {@link SchoolYearUCC} interface.
 */
public class SchoolYearUCCImpl implements SchoolYearUCC {

  @Inject
  private SchoolYearDAO mySchoolYearDAO;

  @Override
  public SchoolYearDTO getOne(int schoolYearId) {
    return mySchoolYearDAO.getOne(schoolYearId);
  }
}

package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.Stage;
import be.vinci.pae.business.domain.StageDTO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.StageDAO;
import jakarta.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is an implementation of the {@link StageUCC} interface.
 */
public class StageUCCImpl implements StageUCC {

  @Inject
  private StageDAO stageDAO;
  @Inject
  private DALServices dalServices;

  @Inject
  private DomainFactory myDomainFactory;


  private static boolean checkDateFormat(String dateString) {
    String pattern = "(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}"; // Validating days: 01-31, months: 01-12
    Pattern regex = Pattern.compile(pattern);
    Matcher matcher = regex.matcher(dateString);
    return matcher.matches();
  }

  @Override
  public StageDTO getOneStageByUserId(int userId) {
    try {
      dalServices.startTransaction();
      StageDTO stageDTO = stageDAO.getOneStageByUserId(userId);
      dalServices.commitTransaction();
      return stageDTO;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public StageDTO modifyStage(int userId, String subject, int contactId, int version) {
    try {
      dalServices.startTransaction();

      Stage stage = (Stage) myDomainFactory.getStage();

      stage.setUserId(userId);
      stage.setContactId(contactId);
      stage.setInternshipProject(subject);
      stage.set_version(version);
      StageDTO updatedStage = stageDAO.modifyStage(stage);
      dalServices.commitTransaction();
      return updatedStage;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }



  @Override
  public StageDTO createOne(ContactDTO contact, String signatureDate, String internshipProject,
      int supervisorId) {
    try {
      dalServices.startTransaction();

      if (!checkDateFormat(signatureDate)) {
        throw new BizException("The date format is not valid");
      }
      StageDTO stage = stageDAO.createOne(contact.getId(), signatureDate, internshipProject,
          supervisorId, contact.getUserId(), contact.getSchoolYearId());
      dalServices.commitTransaction();
      return stage;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}

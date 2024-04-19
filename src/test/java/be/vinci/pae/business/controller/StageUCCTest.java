package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.StageDTO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.StageDAO;
import be.vinci.pae.utils.TestApplicationBinder;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class StageUCCTest {

  private StageUCC stageUCC;
  private StageDTO stage;
  private StageDTO stageResult;
  private DomainFactory factory;
  private StageDAO stageDAO;
  private DALServices dalServices;

  @BeforeEach
  void setUp() {
    // Arrange
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestApplicationBinder());
    stageUCC = locator.getService(StageUCC.class);
    factory = locator.getService(DomainFactory.class);
    stageDAO = locator.getService(StageDAO.class);
    dalServices = locator.getService(DALServices.class);
    stage = factory.getStage();
    stageResult = factory.getStage();
  }

  @AfterEach
  public void tearDown() {
    // Clean up resources, reset state, etc.
    Mockito.reset(stageDAO, dalServices);
  }

  @DisplayName("Test getOneStageByUserId")
  @Test
  void getOneStageByUserId() {
    // Arrange
    int userId = 123;
    when(stageDAO.getOneStageByUserId(userId)).thenReturn(stage);

    // Act
    StageDTO result = stageUCC.getOneStageByUserId(userId);

    // Assert
    assertEquals(stage, result);
  }

  @DisplayName("Test getOneStageByUserId with exception")
  @Test
  void getOneStageByUserIdWithException() {
    // Arrange
    int userId = 123;
    when(stageDAO.getOneStageByUserId(userId)).thenThrow(RuntimeException.class);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> stageUCC.getOneStageByUserId(userId));
  }

  @DisplayName("Test modifyStage with exception")
  @Test
  void modifyStageWithException() {
    // Arrange
    int userId = 123;
    stage.setUserId(userId);
    String subject = "subject";
    stage.setInternshipProject(subject);
    int contactId = 456;
    stage.setContactId(contactId);
    int version = 789;
    stage.setVersion(version);

    when(stageDAO.modifyStage(stage)).thenThrow(RuntimeException.class);

    // Act & Assert
    assertThrows(RuntimeException.class,
        () -> stageUCC.modifyStage(userId, subject, contactId, version));
  }

  @DisplayName("Test modifyStage")
  @Test
  void modifyStage() {
    // Arrange
    int userId = 123;
    stage.setUserId(userId);
    stageResult.setUserId(userId);
    String subject = "new subject";
    stage.setInternshipProject(subject);
    stageResult.setInternshipProject(subject);
    int contactId = 456;
    stage.setContactId(contactId);
    stageResult.setContactId(userId);
    int version = 789;
    stage.setVersion(version);
    stageResult.setVersion(version);

    when(stageDAO.modifyStage(stage)).thenReturn(stageResult);

    // Act
    StageDTO result = stageUCC.modifyStage(userId, subject, contactId, version);

    // Assert
    assertEquals(stageResult.getContactId(), result.getContactId());
    assertEquals(stageResult.getInternshipProject(), result.getInternshipProject());
    assertEquals(stageResult.getUserId(), result.getUserId());
  }

  @DisplayName("Test createOne")
  @Test
  void createOne() {
    // Arrange
    int userId = 123;
    ContactDTO contactDTO = mock(ContactDTO.class);
    contactDTO.setId(789);
    contactDTO.setUserId(userId);
    contactDTO.setSchoolYearId(456);
    stage.setUserId(userId);
    stageResult.setUserId(userId);
    String signatureDate = "2024-04-12";
    stage.setSignatureDate(signatureDate);
    stageResult.setSignatureDate(signatureDate);
    String subject = "subject";
    stage.setInternshipProject(subject);
    stageResult.setInternshipProject(subject);
    int supervisorId = 789;
    stage.setSupervisorId(supervisorId);
    stageResult.setSupervisorId(supervisorId);

    when(stageDAO.createOne(contactDTO.getId(), signatureDate, subject, supervisorId,
        contactDTO.getUserId(), contactDTO.getSchoolYearId())).thenReturn(stageResult);

    // Act
    StageDTO result = stageUCC.createOne(contactDTO, signatureDate, subject, supervisorId);

    // Assert
    assertEquals(stageResult.getUserId(), result.getUserId());
    assertEquals(stageResult.getSignatureDate(), result.getSignatureDate());
  }

  @DisplayName("Test createOne wrong date format")
  @Test
  void createOneWrongFormat() {
    // Arrange
    int userId = 123;
    ContactDTO contactDTO = mock(ContactDTO.class);
    contactDTO.setId(789);
    contactDTO.setUserId(userId);
    contactDTO.setSchoolYearId(456);
    stage.setUserId(userId);
    stageResult.setUserId(userId);
    String signatureDate = "2024-66-12";
    stage.setSignatureDate(signatureDate);
    stageResult.setSignatureDate(signatureDate);
    String subject = "subject";
    stage.setInternshipProject(subject);
    stageResult.setInternshipProject(subject);
    int supervisorId = 789;
    stage.setSupervisorId(supervisorId);
    stageResult.setSupervisorId(supervisorId);

    // Act & Assert
    assertThrows(BizException.class,
        () -> stageUCC.createOne(contactDTO, signatureDate, subject, supervisorId));
  }
}
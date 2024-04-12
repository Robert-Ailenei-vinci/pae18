package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.StageDTO;
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

}

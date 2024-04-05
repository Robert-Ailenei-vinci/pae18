package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.StageDTO;
import be.vinci.pae.exception.FatalError;
import be.vinci.pae.services.StageDAO;
import be.vinci.pae.utils.TestApplicationBinder;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StageUCCTest {

  private StageUCC stageUCC;
  private StageDTO stage;
  private DomainFactory factory;
  private StageDAO stageDAO;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestApplicationBinder());
    this.stageUCC = locator.getService(StageUCC.class);
    this.factory = locator.getService(DomainFactory.class);
    this.stageDAO = locator.getService(StageDAO.class);
    //utiliser des factory
    this.stage = factory.getStage();
  }

  @DisplayName("Test getOneStageByUserId")
  @Test
  void getOneStageByUserId() {
    int userId = 123;

    when(stageDAO.getOneStageByUserId(userId)).thenReturn(stage);
    assertEquals(stageUCC.getOneStageByUserId(userId), stage);
  }

  @DisplayName("Test getOneStageByUserId with transaction error")
  @Test
  void getOneStageByUserIdWithException() {
    int userId = 123;

    when(stageUCC.getOneStageByUserId(userId)).thenThrow(FatalError.class);
    assertThrows(FatalError.class, () -> stageUCC.getOneStageByUserId(userId));
  }
}
package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.StageDTO;
import be.vinci.pae.services.StageDAO;
import be.vinci.pae.utils.TestApplicationBinder;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
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

  @Test
  void getOneStageByUserId() {
    int userId = 123;

    when(stageDAO.getOneStageByUserId(userId)).thenReturn(stage);
    assertEquals(stageUCC.getOneStageByUserId(userId), stage);
  }
}
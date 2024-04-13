package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.Supervisor;
import be.vinci.pae.business.domain.SupervisorDTO;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.SupervisorDAO;
import be.vinci.pae.utils.TestApplicationBinder;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SupervisorUCCImplTest {

  private SupervisorUCC supervisorUCC;
  private Supervisor supervisor;
  private Supervisor initialSupervisor;
  private DomainFactory factory;
  private SupervisorDAO supervisorDAO;
  private DALServices dalServices;

  @BeforeEach
  void setUp() {
    // Arrange
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestApplicationBinder());
    supervisorUCC = locator.getService(SupervisorUCC.class);
    factory = locator.getService(DomainFactory.class);
    supervisorDAO = locator.getService(SupervisorDAO.class);
    dalServices = locator.getService(DALServices.class);
    supervisor = (Supervisor) factory.getSupervisor();
    initialSupervisor = (Supervisor) factory.getSupervisor();
  }

  @AfterEach
  void tearDown() {
    // Clean up resources, reset state, etc.
    Mockito.reset(supervisorDAO, dalServices);
  }

  @DisplayName("Test getOneById")
  @Test
  void getOneById() {
    // Arrange
    supervisor.setSupervisorId(1);
    when(supervisorDAO.getOneById(supervisor.getSupervisorId())).thenReturn(supervisor);

    // Act
    SupervisorDTO result = supervisorUCC.getOneById(supervisor.getSupervisorId());

    // Assert
    assertEquals(supervisor.getSupervisorId(), result.getSupervisorId());
  }

  @DisplayName("Test getOneById with exception")
  @Test
  void getOneByIdWithException() {
    // Arrange
    supervisor.setSupervisorId(1);
    when(supervisorDAO.getOneById(supervisor.getSupervisorId())).thenThrow(RuntimeException.class);

    // Act & Assert
    assertThrows(RuntimeException.class,
        () -> supervisorUCC.getOneById(supervisor.getSupervisorId()));
  }

  @DisplayName("Test getAll")
  @Test
  void getAll() {
    // Arrange
    List<SupervisorDTO> supervisorList = new ArrayList<>();
    supervisorList.add(supervisor);
    supervisor.setEntrepriseId(1);
    when(supervisorDAO.getAll(supervisor.getEntrepriseId())).thenReturn(supervisorList);

    // Act
    List<SupervisorDTO> result = supervisorUCC.getAll(supervisor.getEntrepriseId());

    // Assert
    assertEquals(supervisorList.get(0).getEntrepriseId(), result.get(0).getEntrepriseId());
  }

  @DisplayName("Test getAll with exception")
  @Test
  void getAllWithException() {
    // Arrange
    supervisor.setEntrepriseId(1);
    when(supervisorDAO.getAll(supervisor.getEntrepriseId())).thenThrow(RuntimeException.class);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> supervisorUCC.getAll(supervisor.getEntrepriseId()));
  }
}
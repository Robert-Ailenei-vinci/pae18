package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.SchoolYear;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.SchoolYearDAO;
import be.vinci.pae.utils.TestApplicationBinder;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SchoolYearUCCImplTest {

  private SchoolYearUCC schoolYearUCC;
  private SchoolYear schoolYear;
  private DomainFactory factory;
  private SchoolYearDAO schoolYearDAO;
  private DALServices dalServices;

  @BeforeEach
  void setUp() {
    // 1. Arrange
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestApplicationBinder());
    schoolYearUCC = locator.getService(SchoolYearUCC.class);
    schoolYearDAO = locator.getService(SchoolYearDAO.class);
    factory = locator.getService(DomainFactory.class);
    schoolYear = (SchoolYear) factory.getSchoolYear();
    dalServices = locator.getService(DALServices.class);
  }

  @AfterEach
  public void tearDown() {
    // Clean up resources, reset state, etc.
    Mockito.reset(dalServices);
  }

  @DisplayName("Test getOne")
  @Test
  void getOne() {
    // 2. Act
    schoolYear.setId(1);
    schoolYear.setYearFormat("2021-2022");

    when(schoolYearDAO.getOne(1)).thenReturn(schoolYear);
    SchoolYearDTO result = schoolYearUCC.getOne(1);

    // 3. Assert
    assertNotNull(result);
    assertEquals(1, result.getId());
    assertEquals("2021-2022", result.getYearFormat());
  }

}
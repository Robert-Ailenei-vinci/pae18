package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.SchoolYear;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.SchoolYearDAO;
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

class SchoolYearUCCImplTest {

  private SchoolYearUCC schoolYearUCC;
  private SchoolYear schoolYear;
  private DomainFactory factory;
  private SchoolYearDAO schoolYearDAO;
  private DALServices dalServices;

  @BeforeEach
  void setUp() {
    // Arrange
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
    Mockito.reset(dalServices, schoolYearDAO);
  }

  @DisplayName("Test getOne")
  @Test
  void getOne() {
    // Arrange
    schoolYear.setId(1);
    schoolYear.setYearFormat("2021-2022");

    when(schoolYearDAO.getOne(1)).thenReturn(schoolYear);

    // Act
    SchoolYearDTO result = schoolYearUCC.getOne(1);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.getId());
    assertEquals("2021-2022", result.getYearFormat());
  }

  @DisplayName("Test getOne with exception")
  @Test
  void getOneWithException() {
    // Arrange
    when(schoolYearDAO.getOne(1)).thenThrow(RuntimeException.class);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> schoolYearUCC.getOne(1));
  }

  @DisplayName("Test getCurrentSchoolYear")
  @Test
  void getCurrentSchoolYear() {
    // Arrange
    schoolYear.setId(1);
    schoolYear.setYearFormat("2023-2024");

    when(schoolYearDAO.getCurrentSchoolYear()).thenReturn(schoolYear);

    // Act
    SchoolYearDTO result = schoolYearUCC.getCurrentSchoolYear();

    // Assert
    assertNotNull(result);
    assertEquals(1, result.getId());
    assertEquals("2023-2024", result.getYearFormat());
  }

  @DisplayName("Test getOne with exception")
  @Test
  void getCurrentSchoolYearWithException() {
    // Arrange
    when(schoolYearDAO.getCurrentSchoolYear()).thenThrow(RuntimeException.class);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> schoolYearUCC.getCurrentSchoolYear());
  }

  @DisplayName("Test getAllSchoolYears")
  @Test
  void getAllSchoolYears() {
    // Arrange
    schoolYear.setId(1);
    schoolYear.setYearFormat("2023-2024");
    List<SchoolYearDTO> schoolYearList = new ArrayList<>();
    schoolYearList.add(schoolYear);

    when(schoolYearDAO.getAllSchoolYears()).thenReturn(schoolYearList);

    // Act
    List<SchoolYearDTO> result = schoolYearUCC.getAllSchoolYears();

    // Assert
    assertNotNull(result);
    assertEquals(1, result.get(0).getId());
    assertEquals("2023-2024", result.get(0).getYearFormat());
  }

  @DisplayName("Test getOne with exception")
  @Test
  void getAllSchoolYearsWithException() {
    // Arrange
    when(schoolYearDAO.getAllSchoolYears()).thenThrow(RuntimeException.class);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> schoolYearUCC.getAllSchoolYears());
  }
}
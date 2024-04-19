package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.SchoolYear;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.utils.TestApplicationBinder;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SchoolYearUCCImplTest {

  private SchoolYearUCC schoolYearUCC;
  private SchoolYear schoolYear;
  private DomainFactory factory;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestApplicationBinder());
    this.schoolYearUCC = locator.getService(SchoolYearUCC.class);
    this.factory = locator.getService(DomainFactory.class);
    this.schoolYear = (SchoolYear) factory.getSchoolYear();

  }

  @Test
  void getOne() {
    schoolYear.setId(1);
    schoolYear.setYearFormat("2021-2022");

    when(schoolYearUCC.getOne(1)).thenReturn(schoolYear);
    SchoolYearDTO result = schoolYearUCC.getOne(1);

    assertNotNull(result);
    assertEquals(1, result.getId());
    assertEquals("2021-2022", result.getYearFormat());
  }

  @Test
  void getOneWithException() {

    when(schoolYearUCC.getOne(1));
    assertThrows(RuntimeException.class, () -> schoolYearUCC.getOne(1));
  }

  @Test
  void getCurrentSchoolYear() {
    schoolYear.setId(1);
    schoolYear.setYearFormat("2021-2022");

    when(schoolYearUCC.getCurrentSchoolYear()).thenReturn(schoolYear);
    SchoolYearDTO result = schoolYearUCC.getCurrentSchoolYear();

    assertNotNull(result);
    assertEquals(1, result.getId());
    assertEquals("2021-2022", result.getYearFormat());
  }

  @Test
  void getCurrentSchoolYearWithException() {

    when(schoolYearUCC.getCurrentSchoolYear());
    assertThrows(RuntimeException.class, () -> schoolYearUCC.getCurrentSchoolYear());
  }

  @Test
  void getAllSchoolYears() {
    schoolYear.setId(1);
    schoolYear.setYearFormat("2021-2022");

    when(schoolYearUCC.getAllSchoolYears()).thenReturn(List.of(schoolYear));
    List<SchoolYearDTO> result = schoolYearUCC.getAllSchoolYears();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(1, result.get(0).getId());
    assertEquals("2021-2022", result.get(0).getYearFormat());
  }

  @Test
  void getAllSchoolYearsWithException() {

    when(schoolYearUCC.getAllSchoolYears());
    assertThrows(RuntimeException.class, () -> schoolYearUCC.getAllSchoolYears());
  }
}
package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.Entreprise;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.EntrepriseDAO;
import be.vinci.pae.utils.TestApplicationBinder;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EntrepriseUCCImplTest {

  private Entreprise entreprise;
  private Entreprise entreprise1;
  private Entreprise entreprise2;

  private EntrepriseDAO entrepriseDAO;
  private User user;
  private EntrepriseUCC entrepriseUcc;
  private DomainFactory factory;
  private EntrepriseDTO expectedEntreprise;

  private DALServices dalServices;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestApplicationBinder());
    this.entrepriseUcc = locator.getService(EntrepriseUCC.class);
    this.factory = locator.getService(DomainFactory.class);
    this.dalServices = mock(DALServices.class);
    this.entreprise = (Entreprise) factory.getEntreprise();
    this.entreprise1 = (Entreprise) factory.getEntreprise();
    this.entreprise2 = (Entreprise) factory.getEntreprise();
    this.expectedEntreprise = factory.getEntreprise();
    this.user = (User) factory.getUser();
    this.entrepriseDAO = locator.getService(EntrepriseDAO.class);
    doNothing().when(dalServices).startTransaction();
    doNothing().when(dalServices).commitTransaction();
    doNothing().when(dalServices).rollbackTransaction();
  }

  @Test
  public void testGetOne() {
    entreprise.setId(1);
    entreprise.setTradeName("zaza");

    when(entrepriseUcc.getOne(1)).thenReturn(entreprise);
    expectedEntreprise = entrepriseUcc.getOne(1);

    assertEquals(entreprise.getId(), expectedEntreprise.getId());
  }

  @Test
  public void testGetOneWithException() {
    when(entrepriseUcc.getOne(1));
    assertThrows(RuntimeException.class, () -> {
      entrepriseUcc.getOne(1);
    });
  }


  @Test
  void createOne() {
    // 1. Arrange
    user.setRole("etudiant");
    expectedEntreprise = entreprise1;
    when(entrepriseUcc.createOne(user, "tradeName", "designation", "address", "phoneNum", "email"))
        .thenReturn(expectedEntreprise);
    when(
        entrepriseDAO.createOne("tradeName", "designation", "address", "phoneNum", "email"))
        .thenReturn(entreprise1);

    // 2. Act
    EntrepriseDTO actualEntreprise = entrepriseUcc.createOne(user, "tradeName", "designation",
        "address", "phoneNum", "email");

    // 3. Assert
    assertNotNull(actualEntreprise);
    assertEquals(expectedEntreprise, actualEntreprise);
  }

  @Test
  void createOneWithException() {
    // 1. Arrange
    user.setRole("professeur");

    // 2. Act and Asserts
    assertThrows(BizException.class, () -> {
      entrepriseUcc.createOne(user, "tradeName", "designation", "address", "phoneNum", "email");
    });
  }

  @Test
  void getAll() {
    // 1. Arrange
    List<EntrepriseDTO> expectedEntreprises = new ArrayList<>();
    expectedEntreprises.add(entreprise1);
    expectedEntreprises.add(entreprise2);

    when(entrepriseUcc.getAll()).thenReturn(expectedEntreprises);

    // 2. Act
    List<EntrepriseDTO> actualEntreprises = entrepriseUcc.getAll();

    // 3. Assert
    assertNotNull(actualEntreprises);
    assertEquals(expectedEntreprises.size(), actualEntreprises.size());
    for (int i = 0; i < expectedEntreprises.size(); i++) {
      assertEquals(expectedEntreprises.get(i), actualEntreprises.get(i));
    }
  }

  @Test
  public void testGetAllWithException() {
    when(entrepriseUcc.getAll());
    assertThrows(RuntimeException.class, () -> {
      entrepriseUcc.getAll();
    });
  }

  @Test
  public void testGetAllForSchoolYear() {
    // 1. Arrange
    List<EntrepriseDTO> expectedEntreprises = new ArrayList<>();
    expectedEntreprises.add(entreprise1);
    expectedEntreprises.add(entreprise2);

    when(entrepriseUcc.getAllForSchoolYear(1)).thenReturn(expectedEntreprises);

    // 2. Act
    List<EntrepriseDTO> actualEntreprises = entrepriseUcc.getAllForSchoolYear(1);

    // 3. Assert
    assertNotNull(actualEntreprises);
    assertEquals(expectedEntreprises.size(), actualEntreprises.size());
    for (int i = 0; i < expectedEntreprises.size(); i++) {
      assertEquals(expectedEntreprises.get(i), actualEntreprises.get(i));
    }
  }

  @Test
  public void testGetAllForSchoolYearWithException() {
    when(entrepriseUcc.getAllForSchoolYear(1));
    assertThrows(RuntimeException.class, () -> {
      entrepriseUcc.getAllForSchoolYear(1);
    });
  }

  @Test
  public void testGetAllContactsByEntrepriseId() {
    // 1. Arrange
    List<ContactDTO> expectedContacts = new ArrayList<>();
    ContactDTO contact1 = factory.getContact();
    ContactDTO contact2 = factory.getContact();
    expectedContacts.add(contact1);
    expectedContacts.add(contact2);

    when(entrepriseUcc.getAllContactsByEntrepriseId(1)).thenReturn(expectedContacts);

    // 2. Act
    List<ContactDTO> actualContacts = entrepriseUcc.getAllContactsByEntrepriseId(1);

    // 3. Assert
    assertNotNull(actualContacts);
    assertEquals(expectedContacts.size(), actualContacts.size());
    for (int i = 0; i < expectedContacts.size(); i++) {
      assertEquals(expectedContacts.get(i), actualContacts.get(i));
    }
  }

  @Test
  public void testGetAllContactsByEntrepriseIdWithException() {
    when(entrepriseUcc.getAllContactsByEntrepriseId(1));
    assertThrows(RuntimeException.class, () -> {
      entrepriseUcc.getAllContactsByEntrepriseId(1);
    });
  }

  @Test
  public void getStagesCountForSchoolYear() {
    // 1. Arrange
    int expectedCount = 2;
    when(entrepriseUcc.getStagesCountForSchoolYear(1)).thenReturn(expectedCount);

    // 2. Act
    int actualCount = entrepriseUcc.getStagesCountForSchoolYear(1);

    // 3. Assert
    assertEquals(expectedCount, actualCount);
  }

  @Test
  public void getStagesCountForSchoolYearWithException() {
    when(entrepriseUcc.getStagesCountForSchoolYear(1));
    assertThrows(RuntimeException.class, () -> {
      entrepriseUcc.getStagesCountForSchoolYear(1);
    });
  }
}
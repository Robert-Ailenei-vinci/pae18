package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.Contact;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.Entreprise;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.services.ContactDAO;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.EntrepriseDAO;
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

class EntrepriseUCCImplTest {

  private Entreprise entreprise;
  private Entreprise resultEntreprise;
  private Entreprise entreprise2;

  private EntrepriseDAO entrepriseDAO;
  private User user;
  private EntrepriseUCC entrepriseUcc;
  private DomainFactory factory;
  private EntrepriseDTO actualEntreprise;
  private ContactDTO contact;
  private ContactUCC contactUCC;
  private ContactDAO contactDAO;

  private DALServices dalServices;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestApplicationBinder());
    this.entrepriseUcc = locator.getService(EntrepriseUCC.class);
    this.contactDAO = locator.getService(ContactDAO.class);
    this.contactUCC = locator.getService(ContactUCC.class);
    this.factory = locator.getService(DomainFactory.class);
    this.dalServices = locator.getService(DALServices.class);
    this.entreprise = (Entreprise) factory.getEntreprise();
    this.resultEntreprise = (Entreprise) factory.getEntreprise();
    this.entreprise2 = (Entreprise) factory.getEntreprise();
    this.actualEntreprise = factory.getEntreprise();
    this.user = (User) factory.getUser();
    this.contact = (Contact) factory.getContact();
    this.entrepriseDAO = locator.getService(EntrepriseDAO.class);
  }

  @AfterEach
  public void tearDown() {
    // Clean up resources, reset state, etc.
    Mockito.reset(entrepriseDAO, dalServices, contactDAO);
  }

  @DisplayName("Test getOne")
  @Test
  public void testGetOne() {
    // Arrange
    entreprise.setId(1);
    entreprise.setTradeName("zaza");

    when(entrepriseDAO.getOne(1)).thenReturn(entreprise);

    // Act
    actualEntreprise = entrepriseUcc.getOne(1);

    // Assert
    assertEquals(entreprise.getId(), actualEntreprise.getId());
  }

  @DisplayName("Test getOne with exception")
  @Test
  public void testGetOneWithException() {
    // Arrange
    entreprise.setId(1);
    entreprise.setTradeName("zaza");

    when(entrepriseDAO.getOne(1)).thenThrow(RuntimeException.class);

    // Act & Act
    assertThrows(RuntimeException.class, () -> entrepriseUcc.getOne(1));
  }

  @DisplayName("Test createOne")
  @Test
  void createOne() {
    // 1. Arrange
    user.setRole("etudiant");
    String tradeName = "tradeName";
    entreprise.setTradeName(tradeName);
    resultEntreprise.setTradeName(tradeName);
    String designation = "designation";
    entreprise.setDesignation(designation);
    resultEntreprise.setDesignation(designation);
    String address = "address";
    entreprise.setAddress(address);
    resultEntreprise.setAddress(address);
    String phoneNum = "phoneNum";
    entreprise.setPhoneNumber(phoneNum);
    resultEntreprise.setPhoneNumber(phoneNum);
    String email = "email";
    entreprise.setEmail(email);
    resultEntreprise.setEmail(email);

    when(
        entrepriseDAO.createOne(tradeName, designation, address, phoneNum, email))
        .thenReturn(entreprise);

    // 2. Act
    EntrepriseDTO actualEntreprise = entrepriseUcc.createOne(user, tradeName, designation,
        address, phoneNum, email);

    // 3. Assert
    assertNotNull(actualEntreprise);
    assertEquals(this.entreprise.getDesignation(), actualEntreprise.getDesignation());
  }

  @DisplayName("Test createOne with wrong user role")
  @Test
  void createOneWithException() {
    // Arrange
    user.setRole("professeur");

    // Act and Asserts
    assertThrows(BizException.class, () -> {
      entrepriseUcc.createOne(user, "tradeName", "designation", "address", "phoneNum", "email");
    });
  }

  @DisplayName("Test getAll")
  @Test
  void getAll() {
    // 1. Arrange
    List<EntrepriseDTO> expectedEntreprises = new ArrayList<>();
    expectedEntreprises.add(resultEntreprise);
    expectedEntreprises.add(entreprise2);

    when(entrepriseDAO.getAll()).thenReturn(expectedEntreprises);

    // 2. Act
    List<EntrepriseDTO> actualEntreprises = entrepriseUcc.getAll();

    // 3. Assert
    assertNotNull(actualEntreprises);
    assertEquals(expectedEntreprises.size(), actualEntreprises.size());
    for (int i = 0; i < expectedEntreprises.size(); i++) {
      assertEquals(expectedEntreprises.get(i), actualEntreprises.get(i));
    }
  }

  @DisplayName("Test getOne with exception")
  @Test
  void getOneWithException() {
    // Arrange
    when(entrepriseDAO.getAll()).thenThrow(RuntimeException.class);

    // Act and Asserts
    assertThrows(RuntimeException.class, () -> entrepriseUcc.getAll());
  }

  @DisplayName("Test getAll with transaction exception")
  @Test
  public void testGetAllWithException() {
    when(entrepriseUcc.getAll());
    assertThrows(RuntimeException.class, () -> {
      entrepriseUcc.getAll();
    });
  }

  @DisplayName("Test Blacklist")
  @Test
  void blacklistSuccess() {
    // Arrange
    int entrepriseId = 1;
    Entreprise entreprise = (Entreprise) factory.getEntreprise();
    entreprise.setId(entrepriseId);
    when(entrepriseDAO.getOne(entrepriseId)).thenReturn(entreprise);
    when(entrepriseDAO.blacklist(entreprise, entreprise.getVersion())).thenReturn(entreprise);
    String reason = "Test reason";

    // Act
    EntrepriseDTO blacklistedEntreprise = entrepriseUcc.blacklist(entrepriseId, reason,
        entreprise.getVersion());

    // Assert
    assertNotNull(blacklistedEntreprise);
    assertTrue(blacklistedEntreprise.isBlacklisted());
    assertEquals(reason, blacklistedEntreprise.getBlacklistReason());
  }

  @DisplayName("Test with transaction exception")
  @Test
  void blacklistWithException() {
    // Arrange
    int entrepriseId = 1;
    String reason = "Test reason";
    when(entrepriseDAO.getOne(entrepriseId)).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> {
      entrepriseUcc.blacklist(entrepriseId, reason, entreprise.getVersion());
    });
  }

  @DisplayName("Test Unblacklist")
  @Test
  void unblacklistSuccess() {
    // Arrange
    int entrepriseId = 1;
    Entreprise entreprise = (Entreprise) factory.getEntreprise();
    entreprise.setId(entrepriseId);
    when(entrepriseDAO.getOne(entrepriseId)).thenReturn(entreprise);
    when(entrepriseDAO.unblacklist(entreprise, entreprise.getVersion())).thenReturn(entreprise);

    // Act
    EntrepriseDTO unblacklistedEntreprise = entrepriseUcc.unblacklist(entrepriseId,
        entreprise.getVersion());

    // Assert
    assertNotNull(unblacklistedEntreprise);
    assertFalse(unblacklistedEntreprise.isBlacklisted());
    assertNull(unblacklistedEntreprise.getBlacklistReason());
  }

  @DisplayName("Test Unblacklist with transaction exception")
  @Test
  void unblacklistWithException() {
    // Arrange
    int entrepriseId = 1;
    when(entrepriseDAO.getOne(entrepriseId)).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> {
      entrepriseUcc.unblacklist(entrepriseId, entreprise.getVersion());
    });
  }

  @DisplayName("Test getStagesCountForSchoolYear")
  @Test
  public void getStagesCountForSchoolYear() {
    // Arrange
    when(entrepriseDAO.getNbStagesForCurrentYear(1)).thenReturn(5);

    // Act
    int result = entrepriseUcc.getStagesCountForSchoolYear(1);

    // Assert
    assertEquals(5, result);
  }

  @Test
  public void testGetAllForSchoolYear() {
    // 1. Arrange
    List<EntrepriseDTO> expectedEntreprises = new ArrayList<>();
    EntrepriseDTO entreprise1 = factory.getEntreprise();
    EntrepriseDTO entreprise2 = factory.getEntreprise();
    expectedEntreprises.add(entreprise1);
    expectedEntreprises.add(entreprise2);

    when(entrepriseUcc.getAllForSchoolYear(1, "orderBy")).thenReturn(expectedEntreprises);

    // 2. Act
    List<EntrepriseDTO> actualEntreprises = entrepriseUcc.getAllForSchoolYear(1, "orderBy");

    // 3. Assert
    assertNotNull(actualEntreprises);
    assertEquals(expectedEntreprises.size(), actualEntreprises.size());
    for (int i = 0; i < expectedEntreprises.size(); i++) {
      assertEquals(expectedEntreprises.get(i), actualEntreprises.get(i));
    }
  }

  @Test
  public void testGetAllForSchoolYearWithException() {
    when(entrepriseUcc.getAllForSchoolYear(1, "orderBy"));
    assertThrows(RuntimeException.class, () -> {
      entrepriseUcc.getAllForSchoolYear(1, "orderBy");
    });
  }

  @DisplayName("Test getStagesCountForSchoolYear with exception")
  @Test
  public void getStagesCountForSchoolYearWithException() {
    // Arrange
    when(entrepriseDAO.getNbStagesForCurrentYear(1)).thenThrow(RuntimeException.class);

    // Act & Act
    assertThrows(RuntimeException.class, () -> entrepriseUcc.getStagesCountForSchoolYear(1));
  }

  @DisplayName("Test getAllContactsByEntrepriseId")
  @Test
  public void getAllContactsByEntrepriseId() {
    // Arrange
    List<ContactDTO> contactList = new ArrayList<>();
    contactList.add(contact);
    when(contactDAO.getAllContactsByEntrepriseId(1)).thenReturn(contactList);

    // Act
    List<ContactDTO> result = entrepriseUcc.getAllContactsByEntrepriseId(1);

    // Assert
    assertEquals(contact.getId(), result.get(0).getId());
  }

  @DisplayName("Test getAllContactsByEntrepriseId with exception")
  @Test
  public void getAllContactsByEntrepriseIdWithException() {
    // Arrange
    when(contactDAO.getAllContactsByEntrepriseId(1)).thenThrow(RuntimeException.class);

    // Act & Act
    assertThrows(RuntimeException.class, () -> entrepriseUcc.getAllContactsByEntrepriseId(1));
  }
}
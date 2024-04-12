package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.Entreprise;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.FatalError;
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

  private DALServices dalServices;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestApplicationBinder());
    this.entrepriseUcc = locator.getService(EntrepriseUCC.class);
    this.factory = locator.getService(DomainFactory.class);
    this.dalServices = locator.getService(DALServices.class);
    this.entreprise = (Entreprise) factory.getEntreprise();
    this.resultEntreprise = (Entreprise) factory.getEntreprise();
    this.entreprise2 = (Entreprise) factory.getEntreprise();
    this.actualEntreprise = factory.getEntreprise();
    this.user = (User) factory.getUser();
    this.entrepriseDAO = locator.getService(EntrepriseDAO.class);
  }

  @AfterEach
  public void tearDown() {
    // Clean up resources, reset state, etc.
    Mockito.reset(entrepriseDAO, dalServices);
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
}
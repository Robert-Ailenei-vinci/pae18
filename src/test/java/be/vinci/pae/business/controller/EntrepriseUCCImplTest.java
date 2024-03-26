package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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

  private EntrepriseDAO entrepriseDataService;
  private User user;
  private EntrepriseUCC entrepriseUcc;
  private DomainFactory factory;
  private EntrepriseDTO expectedEntreprise;


  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestApplicationBinder());
    this.entrepriseUcc = locator.getService(EntrepriseUCC.class);
    this.factory = locator.getService(DomainFactory.class);
    this.myEntrepriseDAO = locator.getService(EntrepriseDAO.class);
    this.dalServices = locator.getService(DALServices.class);
    this.entreprise = (Entreprise) factory.getEntreprise();
    this.entreprise1 = (Entreprise) factory.getEntreprise();
    this.entreprise2 = (Entreprise) factory.getEntreprise();
    this.expectedEntreprise = factory.getEntreprise();
    this.user = (User) factory.getUser();
    this.entrepriseDataService = locator.getService(EntrepriseDAO.class);
  }

  @Test
  public void testGetOne() {
    entreprise.setId(1);
    entreprise.setAddress("lolz");
    entreprise.setDesignation("LMP");
    entreprise.setEmail("zaza11");
    entreprise.setPhoneNumber("0484");
    entreprise.setTradeName("zaza");

    when(entrepriseUcc.getOne(1)).thenReturn(entreprise);
    expectedEntreprise = entrepriseUcc.getOne(1);

    assertNotNull(expectedEntreprise);
    assertEquals(1, expectedEntreprise.getId());
    assertEquals("lolz", expectedEntreprise.getAddress());
    assertEquals("LMP", expectedEntreprise.getDesignation());
    assertEquals("zaza11", expectedEntreprise.getEmail());
    assertEquals("0484", expectedEntreprise.getPhoneNumber());
    assertEquals("zaza", expectedEntreprise.getTradeName());

  }

  @Test
  void createOne() {
    // 1. Arrange
    user.setRole("etudiant");
    expectedEntreprise = entreprise1;
    when(entrepriseUcc.createOne(user, "tradeName", "designation", "address", "phoneNum", "email"))
        .thenReturn(expectedEntreprise);
    when(
        entrepriseDataService.createOne("tradeName", "designation", "address", "phoneNum", "email"))
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

    // 2. Act and Assert
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
}
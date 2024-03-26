package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.Entreprise;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
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
import org.mockito.Mockito;

class EntrepriseUCCImplTest {

  private Entreprise entreprise;
  private Entreprise entreprise1;
  private Entreprise entreprise2;


  private EntrepriseUCC entrepriseUcc;
  private DomainFactory factory;
  private EntrepriseDTO expectedEntreprise;
  private User user;
  private EntrepriseDAO myEntrepriseDAO;
  private DALServices dalServices;


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
  void createOne_ValidUser_CreatesEntreprise() {
    // Arrange
    when(user.checkIsStudent()).thenReturn(true);

    String tradeName = "TradeName";
    String designation = "Designation";
    String address = "Address";
    String phoneNum = "PhoneNum";
    String email = "Email";

    Entreprise expectedEntreprise = (Entreprise) factory.getEntreprise();
    when(myEntrepriseDAO.createOne(tradeName, designation, address, phoneNum, email)).thenReturn(expectedEntreprise);

    // Act
    EntrepriseDTO actualEntreprise = entrepriseUcc.createOne(user, tradeName, designation, address, phoneNum, email);

    // Assert
    assertNotNull(actualEntreprise);
    assertEquals(expectedEntreprise, actualEntreprise);

    // Verify that commitTransaction was called
    verify(dalServices).commitTransaction();
  }

  @Test
  void createOne_InvalidUser_ThrowsException() {
    // Arrange
    when(user.checkIsStudent()).thenReturn(false);

    String tradeName = "TradeName";
    String designation = "Designation";
    String address = "Address";
    String phoneNum = "PhoneNum";
    String email = "Email";

    // Act & Assert
    BizException exception = assertThrows(BizException.class, () -> entrepriseUcc.createOne(user, tradeName, designation, address, phoneNum, email));
    assertEquals("This user is not a student.", exception.getMessage());

    // Verify that rollbackTransaction was called
    verify(dalServices).rollbackTransaction();
  }
}
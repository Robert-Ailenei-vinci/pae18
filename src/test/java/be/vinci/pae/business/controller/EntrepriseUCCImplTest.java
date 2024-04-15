package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
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

    // Arrange
    // Arrange
    Entreprise entreprise = (Entreprise) factory.getEntreprise();
    entreprise.setId(1);
    when(entrepriseDAO.getOne(anyInt())).thenReturn(entreprise);
  }

  @Test
  void getOneSuccess() {
    // Arrange
    int entrepriseId = 1;
    Entreprise entreprise = (Entreprise) factory.getEntreprise();
    entreprise.setId(entrepriseId);

    when(entrepriseDAO.getOne(entrepriseId)).thenReturn(entreprise);
    // Act
    EntrepriseDTO retrievedEntreprise = entrepriseUcc.getOne(entrepriseId);

    // Assert
    assertNotNull(retrievedEntreprise);
    assertEquals(entrepriseId, retrievedEntreprise.getId());
  }

  @Test
  void getOneWithException() {
    // Arrange
    int entrepriseId = 1;
    when(entrepriseDAO.getOne(entrepriseId)).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> {
      entrepriseUcc.getOne(entrepriseId);
    });
  }

  @Test
  public void testGetOneWithException() {
    when(entrepriseUcc.getOne(1));
    assertThrows(RuntimeException.class, () -> {
      entrepriseUcc.getOne(1);
    });
  }


//  @Test
//  void createOne() {
//    // 1. Arrange
//    user.setRole("etudiant");
//    expectedEntreprise = entreprise1;
//    when(entrepriseUcc.createOne(user, "tradeName", "designation", "address", "phoneNum", "email"))
//        .thenReturn(expectedEntreprise);
//    when(
//      entrepriseDAO.createOne("tradeName", "designation", "address", "phoneNum", "email"))
//        .thenReturn(entreprise1);
//
//    // 2. Act
//    EntrepriseDTO actualEntreprise = entrepriseUcc.createOne(user, "tradeName", "designation",
//        "address", "phoneNum", "email");
//
//    // 3. Assert
//    assertNotNull(actualEntreprise);
//    assertEquals(expectedEntreprise, actualEntreprise);
//  }

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

  @Test
  public void testGetAllWithException() {
    when(entrepriseUcc.getAll());
    assertThrows(RuntimeException.class, () -> {
      entrepriseUcc.getAll();
    });
  }

  @Test
  void blacklistSuccess() {
    // Arrange
    int entrepriseId = 1;
    String reason = "Test reason";
    Entreprise entreprise = (Entreprise) factory.getEntreprise();
    entreprise.setId(entrepriseId);
    when(entrepriseDAO.getOne(entrepriseId)).thenReturn(entreprise);
    when(entrepriseDAO.blacklist(entreprise, entreprise.getVersion())).thenReturn(entreprise);

    // Act
    EntrepriseDTO blacklistedEntreprise = entrepriseUcc.blacklist(entrepriseId, reason,
        entreprise.getVersion());

    // Assert
    assertNotNull(blacklistedEntreprise);
    assertTrue(blacklistedEntreprise.isBlacklisted());
    assertEquals(reason, blacklistedEntreprise.getBlacklistReason());
  }

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

}
package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.Entreprise;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.utils.TestApplicationBinder;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EntrepriseUCCImplTest {

  private Entreprise entreprise;
  private EntrepriseUCC entrepriseUcc;
  private DomainFactory factory;
  private Entreprise expectedEntreprise;


  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestApplicationBinder());
    this.entrepriseUcc = locator.getService(EntrepriseUCC.class);
    this.factory = locator.getService(DomainFactory.class);
    this.entreprise = (Entreprise) factory.getEntreprise();
    this.expectedEntreprise = (Entreprise) factory.getEntreprise();
  }

  @Test
  public void testGetOne() {
    // Arrange
    int entrepriseId = 1;
    expectedEntreprise.setId(entrepriseId);
    expectedEntreprise.setDesignation("Test Entreprise");

    // Mock the getOne method of EntrepriseDAO to return the expectedEntreprise
    when(entrepriseUcc.getOne(entrepriseId)).thenReturn(expectedEntreprise);

    // Act
    EntrepriseDTO result = entrepriseUcc.getOne(entrepriseId);

    // Assert
    assertNotNull(result);
    assertEquals(expectedEntreprise.getId(), result.getId());
    assertEquals(expectedEntreprise.getDesignation(), result.getDesignation());
  }

  @Test
  void getAll() {

  }
}
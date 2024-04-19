package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.Contact;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.SupervisorDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.BizExceptionNotFound;
import be.vinci.pae.services.ContactDAO;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.SupervisorDAO;
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

class ContactUCCTest {

  private ContactUCC contactUCC;
  private Contact contact;
  private Contact contactResult;
  private DomainFactory factory;
  private ContactDAO contactDAO;
  private DALServices dalServices;
  private SupervisorUCC supervisorUCC;
  private SupervisorDAO supervisorDAO;

  @BeforeEach
  void setUp() {
    // Arrange
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestApplicationBinder());
    this.contactUCC = locator.getService(ContactUCC.class);
    this.supervisorUCC = locator.getService(SupervisorUCC.class);
    this.supervisorDAO = locator.getService(SupervisorDAO.class);
    this.factory = locator.getService(DomainFactory.class);
    this.contactDAO = locator.getService(ContactDAO.class);
    this.dalServices = locator.getService(DALServices.class);
    this.contact = (Contact) factory.getContact();
    this.contactResult = (Contact) factory.getContact();

    contact.setUserId(123);
    contact.setId(1);
    contact.setState("initie");
    contactResult.setUserId(123);
    contactResult.setId(1);
  }

  @AfterEach
  public void tearDown() {
    // Clean up resources, reset state, etc.
    Mockito.reset(contactDAO, dalServices);
  }

  @DisplayName("Test createOne")
  @Test
  void createOne() {
    // Arrange
    UserDTO userDTO = factory.getUser();
    userDTO.setId(123);
    userDTO.setRole("etudiant");

    contact.setUserId(userDTO.getId());
    contact.setEntrepriseId(456);

    List<ContactDTO> contactDTOList = new ArrayList<>();
    contactDTOList.add(contact);

    when(contactDAO.getAllContactsByUserId(userDTO.getId())).thenReturn(contactDTOList);

    EntrepriseDTO entrepriseDTO = mock(EntrepriseDTO.class);
    entrepriseDTO.setId(123456);
    SchoolYearDTO schoolYearDTO = mock(SchoolYearDTO.class);
    schoolYearDTO.setId(123456789);
    when(contactDAO.createOne(userDTO, entrepriseDTO, schoolYearDTO)).thenReturn(contact);

    // Act
    ContactDTO resultContact = contactUCC.createOne(userDTO, entrepriseDTO, schoolYearDTO);

    // Assert
    assertEquals(contact.getId(), resultContact.getId());
  }

  @DisplayName("Test createOne with already existing contact with same entreprise & same year")
  @Test
  void createOneWrongUniqueCondition() {
    // Arrange
    UserDTO userDTO = factory.getUser();
    userDTO.setId(123);
    userDTO.setRole("etudiant");

    List<ContactDTO> contactDTOList = new ArrayList<>();
    contactDTOList.add(contactResult);
    contactDTOList.add(contact);
    when(contactDAO.getAllContactsByUserId(contact.getUserId())).thenReturn(contactDTOList);

    EntrepriseDTO entrepriseDTO = mock(EntrepriseDTO.class);
    SchoolYearDTO schoolYearDTO = mock(SchoolYearDTO.class);
    when(contactDAO.createOne(userDTO, entrepriseDTO, schoolYearDTO)).thenReturn(contact);

    // Act and Assert
    assertThrows(BizException.class,
        () -> contactUCC.createOne(userDTO, entrepriseDTO, schoolYearDTO));
  }

  @DisplayName("Test createOneWrongUserRole")
  @Test
  void createOneWrongUserRole() {
    // Arrange
    UserDTO userDTO = factory.getUser();
    userDTO.setId(123);
    userDTO.setRole("professeur");

    EntrepriseDTO entrepriseDTO = mock(EntrepriseDTO.class);
    SchoolYearDTO schoolYearDTO = mock(SchoolYearDTO.class);
    when(contactDAO.createOne(userDTO, entrepriseDTO, schoolYearDTO)).thenReturn(contact);

    // Act and Assert
    assertThrows(BizException.class,
        () -> contactUCC.createOne(userDTO, entrepriseDTO, schoolYearDTO));
  }

  @DisplayName("Test CreateOne with transaction error")
  @Test
  void createOneWithTransactionError() {
    // Arrange
    UserDTO userDTO = factory.getUser();
    userDTO.setId(123);
    userDTO.setRole("professeur");

    EntrepriseDTO entrepriseDTO = mock(EntrepriseDTO.class);
    SchoolYearDTO schoolYearDTO = mock(SchoolYearDTO.class);
    when(contactDAO.createOne(userDTO, entrepriseDTO, schoolYearDTO)).thenThrow(
        RuntimeException.class);

    assertThrows(RuntimeException.class,
        () -> contactDAO.createOne(userDTO, entrepriseDTO, schoolYearDTO));
  }

  @DisplayName("Test getAllContactsByUserId")
  @Test
  void getAllContactsByUserId() {
    // Arrange
    List<ContactDTO> contactList = new ArrayList<>();
    ContactDTO mockedContact = mock(ContactDTO.class);
    contactList.add(mockedContact);
    when(contactDAO.getAllContactsByUserId(contact.getUserId())).thenReturn(contactList);

    // Act and Assert
    assertEquals(contactList, contactUCC.getAllContactsByUserId(contact.getUserId()));
  }

  @DisplayName("Test getAllContactsByUserId with transaction error")
  @Test
  void getAllContactsByUserIdWithTransactionError() {
    // Arrange
    when(contactDAO.getAllContactsByUserId(contact.getUserId())).thenThrow(RuntimeException.class);

    // Act and Assert
    assertThrows(RuntimeException.class,
        () -> contactUCC.getAllContactsByUserId(contact.getUserId()));
  }

  @DisplayName("Test meetContact")
  @Test
  void meetContact() {
    // Arrange
    String meetingType = "new type of meeting";
    contact.setMeetingType("type of meeting");
    contactResult.setMeetingType(meetingType);

    when(contactDAO.getOneContactById(contact.getId())).thenReturn(contact);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    // Act and Assert
    assertEquals(contactResult.getMeetingType(),
        contactUCC.meetContact(contact.getId(), meetingType, contact.getUserId(),
            contact.getVersion()).getMeetingType());
  }

  @DisplayName("Test meetContact given wrong state")
  @Test
  void meetContactWrongState() {
    // Arrange
    String meetingType = "new type of meeting";
    contact.setMeetingType("type of meeting");
    contact.setState("annule");
    contactResult.setMeetingType(meetingType);

    when(contactDAO.getOneContactById(contact.getId())).thenReturn(contact);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    // Act and Assert
    assertThrows(BizException.class,
        () -> contactUCC.meetContact(contact.getId(), meetingType, contact.getUserId(),
            contact.getVersion()));
  }


  @DisplayName("Test meetContact given wrong user")
  @Test
  void meetContactWrongUser() {
    // Arrange
    String meetingType = "new type of meeting";
    contactResult.setMeetingType(meetingType);
    contact.setUserId(456);

    when(contactDAO.getOneContactById(contact.getId())).thenReturn(contactResult);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    // Act and Assert
    assertThrows(BizExceptionNotFound.class,
        () -> contactUCC.meetContact(contact.getId(), meetingType, contact.getUserId(),
            contact.getVersion()));
  }

  @DisplayName("Test stopFollowContact")
  @Test
  void stopFollowContact() {
    // Arrange
    contactResult.setState("suivis stoppe");

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contact);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    // Act and Assert
    assertEquals(contactResult.getState(),
        contactUCC.stopFollowContact(contact.getUserId(), contact.getUserId(),
            contact.getVersion()).getState());
  }

  @DisplayName("Test stopFollowContact given wrong user")
  @Test
  void stopFollowContactWrongUser() {
    // Arrange
    contactResult.setState("suivis stoppe");
    contact.setUserId(456);

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contactResult);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    // Act and Assert
    assertThrows(BizExceptionNotFound.class,
        () -> contactUCC.stopFollowContact(contact.getUserId(), contact.getUserId(),
            contact.getVersion()));
  }


  @DisplayName("Test stopFollowContact given wrong state")
  @Test
  void stopFollowContactWrongCheckMeet() {
    // Arrange
    contactResult.setState("suivis stoppe");
    contact.setState("rencontre");

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contactResult);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    // Act and Assert
    assertThrows(BizException.class,
        () -> contactUCC.stopFollowContact(contact.getUserId(), contact.getUserId(),
            contact.getVersion()));
  }

  @DisplayName("Test refusedContact")
  @Test
  void refusedContact() {
    // Arrange
    String refusalReason = "raison de refus";
    contact.setState("rencontre");
    contactResult.setState("refuse");
    contactResult.setReasonForRefusal(refusalReason);

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contact);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    // Act and Assert
    assertEquals(contactResult.getState(),
        contactUCC.refusedContact(contact.getUserId(), refusalReason, contact.getUserId(),
            contact.getVersion()).getState());
  }

  @DisplayName("Test refusedContact given wrong state")
  @Test
  void refusedContactWrongState() {
    // Arrange
    String refusalReason = "raison de refus";
    contact.setState("refuser");
    contactResult.setState("refuse");
    contactResult.setReasonForRefusal(refusalReason);

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contact);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    // Act and Assert
    assertThrows(BizException.class,
        () -> contactUCC.refusedContact(contact.getUserId(), refusalReason, contact.getUserId(),
            contact.getVersion()));
  }

  @DisplayName("Test refusedContact given wrong user")
  @Test
  void refusedContactWrongUser() {
    // Arrange
    contact.setState("rencontre");
    contact.setUserId(456);
    contactResult.setState("refuse");
    String refusalReason = "raison de refus";
    contactResult.setReasonForRefusal(refusalReason);

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contactResult);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    // Act and Assert
    assertThrows(BizExceptionNotFound.class,
        () -> contactUCC.refusedContact(contact.getUserId(), refusalReason, contact.getUserId(),
            contact.getVersion()));
  }

  @DisplayName("Test acceptContact")
  @Test
  void acceptContact() {
    // Arrange
    contact.setId(123);
    contact.setState("rencontre");
    contactResult.setId(123);
    contactResult.setState("accepte");
    String signDate = "2023-11-11";
    String internshipPoject = "SQL: avancée";
    int supervisorId = 2;

    SupervisorDTO supervisorDTO = Mockito.mock(SupervisorDTO.class);
    supervisorDTO.setEntreprise(contact.getEntreprise());
    supervisorDTO.setSupervisorId(supervisorId);
    supervisorDTO.setFirstName("First");
    supervisorDTO.setLastName("Last");

    when(contactDAO.getOneContactById(contact.getId())).thenReturn(contact);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);
    when(supervisorDAO.getOneById(supervisorId)).thenReturn(supervisorDTO);
    // Act and Assert
    ContactDTO result = contactUCC.acceptContact(contact.getUserId(), contact.getUserId(),
        contact.getVersion(), supervisorId, signDate, internshipPoject);
    assertEquals(contactResult.getId(), result.getId());
    assertEquals(contactResult.getState(), result.getState());
  }

  @DisplayName("Test acceptContact with wrong given state")
  @Test
  void acceptContactWithWrongState() {
    // Arrange
    contact.setId(123);
    contact.setState("accepte");
    String signDate = "2023-11-11";
    String internshipPoject = "SQL: avancée";
    int supervisorId = 2;

    SupervisorDTO supervisorDTO = Mockito.mock(SupervisorDTO.class);
    supervisorDTO.setEntreprise(contact.getEntreprise());
    supervisorDTO.setSupervisorId(supervisorId);
    supervisorDTO.setFirstName("First");
    supervisorDTO.setLastName("Last");

    when(contactDAO.getOneContactById(contact.getId())).thenReturn(contact);
    when(supervisorDAO.getOneById(supervisorId)).thenReturn(supervisorDTO);

    // Act and Assert
    assertThrows(BizException.class,
        () -> contactUCC.acceptContact(contact.getUserId(), contact.getUserId(),
            contact.getVersion(), supervisorId, signDate, internshipPoject));
  }

  @DisplayName("Test acceptContact with wrong given user")
  @Test
  void acceptContactWithWrongUser() {
    // Arrange
    contact.setId(123);
    contact.setState("accepte");
    String signDate = "2023-11-11";
    String internshipPoject = "SQL: avancée";
    int supervisorId = 2;

    SupervisorDTO supervisorDTO = Mockito.mock(SupervisorDTO.class);
    supervisorDTO.setEntreprise(contact.getEntreprise());
    supervisorDTO.setSupervisorId(supervisorId);
    supervisorDTO.setFirstName("First");
    supervisorDTO.setLastName("Last");

    when(contactDAO.getOneContactById(contact.getId())).thenReturn(contact);
    when(supervisorDAO.getOneById(supervisorId)).thenReturn(supervisorDTO);

    // Act and Assert
    assertThrows(BizExceptionNotFound.class,
        () -> contactUCC.acceptContact(contact.getUserId(), 789,
            contact.getVersion(), supervisorId, signDate, internshipPoject));
  }

  @DisplayName("Test cancelInternshipsBasedOnEntreprise")
  @Test
  void cancelInternshipsBasedOnEntreprise() {
    // Arrange
    contact.setId(1);

    when(contactDAO.cancelInternshipsBasedOnEntrepriseId(1)).thenReturn(true);

    // Act & Assert
    assertTrue(contactUCC.cancelInternshipsBasedOnEntreprise(1));
  }

  @DisplayName("Test cancelInternshipsBasedOnEntreprise with transaction exception")
  @Test
  void cancelInternshipsBasedOnEntrepriseWithException() {
    // Arrange
    contact.setId(1);

    when(contactDAO.cancelInternshipsBasedOnEntrepriseId(1)).thenThrow(RuntimeException.class);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> contactUCC.cancelInternshipsBasedOnEntreprise(1));
  }
}
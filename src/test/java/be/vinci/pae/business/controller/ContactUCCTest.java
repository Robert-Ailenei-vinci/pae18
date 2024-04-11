package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.Contact;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.BizExceptionNotFound;
import be.vinci.pae.services.ContactDAO;
import be.vinci.pae.utils.TestApplicationBinder;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContactUCCTest {

  private ContactUCC contactUCC;
  private Contact contact;
  private Contact contactResult;
  private DomainFactory factory;
  private ContactDAO contactDAO;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestApplicationBinder());
    this.contactUCC = locator.getService(ContactUCC.class);
    this.factory = locator.getService(DomainFactory.class);
    this.contactDAO = locator.getService(ContactDAO.class);
    this.contact = (Contact) factory.getContact();
    this.contactResult = (Contact) factory.getContact();

    contact.setUserId(123);
    contact.setId(1);
    contact.setState("initie");
    contactResult.setUserId(123);
    contactResult.setId(1);
  }

  @DisplayName("Test createOne")
  @Test
  void createOne() {
    UserDTO userDTO = factory.getUser();
    userDTO.setId(123);
    userDTO.setRole("etudiant");
    List<ContactDTO> contactDTOList = new ArrayList<>();
    contactResult.setEntrepriseId(123);
    contactResult.setSchoolYearId(123);
    contactDTOList.add(contactResult);
    when(contactDAO.getAllContactsByUserId(contact.getUserId())).thenReturn(contactDTOList);
    EntrepriseDTO entrepriseDTO = mock(EntrepriseDTO.class);
    SchoolYearDTO schoolYearDTO = mock(SchoolYearDTO.class);
    when(contactDAO.createOne(userDTO, entrepriseDTO, schoolYearDTO)).thenReturn(contact);

    assertEquals(contactUCC.createOne(userDTO, entrepriseDTO, schoolYearDTO), contact);
  }

  @DisplayName("Test createOne with already existing contact with same entreprise & same year")
  @Test
  void createOneWrongUniqueCondition() {
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

    assertThrows(BizException.class,
        () -> contactUCC.createOne(userDTO, entrepriseDTO, schoolYearDTO));
  }

  @DisplayName("Test createOne given wrong role")
  @Test
  void createOneWrongUserRole() {
    UserDTO userDTO = factory.getUser();
    userDTO.setId(123);
    userDTO.setRole("professeur");
    EntrepriseDTO entrepriseDTO = mock(EntrepriseDTO.class);
    SchoolYearDTO schoolYearDTO = mock(SchoolYearDTO.class);
    when(contactDAO.createOne(userDTO, entrepriseDTO, schoolYearDTO)).thenReturn(contact);

    assertThrows(BizException.class,
        () -> contactUCC.createOne(userDTO, entrepriseDTO, schoolYearDTO));
  }

  @DisplayName("Test getAllContactsByUserId")
  @Test
  void getAllContactsByUserId() {
    List<ContactDTO> contactList = new ArrayList<>();
    ContactDTO mockedContact = mock(ContactDTO.class);
    contactList.add(mockedContact);
    when(contactDAO.getAllContactsByUserId(contact.getUserId())).thenReturn(contactList);

    assertEquals(contactUCC.getAllContactsByUserId(contact.getUserId()), contactList);
  }

  @DisplayName("Test getAllContactsByUserId with transaction exception")
  @Test
  void getAllContactsByUserIdWithException() {
    when(contactDAO.getAllContactsByUserId(contact.getUserId()));

    assertThrows(RuntimeException.class,
        () -> contactUCC.getAllContactsByUserId(contact.getUserId()));
  }

  @DisplayName("Test meetContact")
  @Test
  void meetContact() {
    String meetingType = "new type of meeting";
    contact.setMeetingType("type of meeting");
    contactResult.setMeetingType(meetingType);

    when(contactDAO.getOneContactById(contact.getId())).thenReturn(contact);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    assertEquals(contactUCC.meetContact(contact.getId(), meetingType, contact.getUserId(),
            contact.getVersion()),
        contactResult);
  }

  @DisplayName("Test meetContact given wrong state")
  @Test
  void meetContactWrongState() {
    String meetingType = "new type of meeting";
    contact.setMeetingType("type of meeting");
    contact.setState("annule");
    contactResult.setMeetingType(meetingType);

    when(contactDAO.getOneContactById(contact.getId())).thenReturn(contact);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    assertThrows(BizException.class,
        () -> contactUCC.meetContact(contact.getId(), meetingType, contact.getUserId(),
            contact.getVersion()));
  }

  @DisplayName("Test meetContact given wrong user")
  @Test
  void meetContactWrongUser() {
    String meetingType = "new type of meeting";
    contactResult.setMeetingType(meetingType);
    contact.setUserId(456);

    when(contactDAO.getOneContactById(contact.getId())).thenReturn(contactResult);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    // Assert that a BizException is thrown when trying to meet the contact
    assertThrows(BizExceptionNotFound.class,
        () -> contactUCC.meetContact(contact.getId(), meetingType, contact.getUserId(),
            contact.getVersion()));
  }

  @DisplayName("Test stopFollowContact")
  @Test
  void stopFollowContact() {
    contactResult.setState("suivis stoppe");

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contact);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    assertEquals(contactUCC.stopFollowContact(contact.getUserId(), contact.getUserId(),
            contact.getVersion()),
        contactResult);
  }

  @DisplayName("Test stopFollowContact given wrong user")
  @Test
  void stopFollowContactWrongUser() {
    contactResult.setState("suivis stoppe");
    contact.setUserId(456);

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contactResult);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    assertThrows(BizExceptionNotFound.class,
        () -> contactUCC.stopFollowContact(contact.getUserId(), contact.getUserId(),
            contact.getVersion()));
  }

  @DisplayName("Test stopFollowContact given wrong state")
  @Test
  void stopFollowContactWrongCheckMeet() {
    contactResult.setState("suivis stoppe");
    contact.setState("rencontre");

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contactResult);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    assertThrows(BizException.class,
        () -> contactUCC.stopFollowContact(contact.getUserId(), contact.getUserId(),
            contact.getVersion()));
  }

  @DisplayName("Test refusedContact")
  @Test
  void refusedContact() {
    String refusalReason = "raison de refus";
    contact.setState("rencontre");
    contactResult.setState("refuse");
    contactResult.setReasonForRefusal(refusalReason);

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contact);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    assertEquals(contactUCC.refusedContact(contact.getUserId(), refusalReason, contact.getUserId(),
            contact.getVersion()),
        contactResult);
  }

  @DisplayName("Test refusedContact given wrong state")
  @Test
  void refusedContactWrongState() {
    String refusalReason = "raison de refus";
    contact.setState("refuser");
    contactResult.setState("refuse");
    contactResult.setReasonForRefusal(refusalReason);

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contact);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    assertThrows(BizException.class,
        () -> contactUCC.refusedContact(contact.getUserId(), refusalReason, contact.getUserId(),
            contact.getVersion()));
  }

  @DisplayName("Test refusedContact given wrong user")
  @Test
  void refusedContactWrongUser() {
    contact.setState("rencontre");
    contact.setUserId(456);
    contactResult.setState("refuse");
    String refusalReason = "raison de refus";
    contactResult.setReasonForRefusal(refusalReason);

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contactResult);
    when(contactDAO.updateContact(contact)).thenReturn(contactResult);

    assertThrows(BizExceptionNotFound.class,
        () -> contactUCC.refusedContact(contact.getUserId(), refusalReason, contact.getUserId(),
            contact.getVersion()));
  }

}
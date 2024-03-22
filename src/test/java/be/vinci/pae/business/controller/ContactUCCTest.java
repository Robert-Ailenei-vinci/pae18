package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.Contact;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.BizExceptionNotFound;
import be.vinci.pae.services.ContactDAO;
import be.vinci.pae.utils.TestApplicationBinder;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
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

  @Test
  void createOne() {
    /*UserDTO userDTO = mock(UserDTO.class);
    EntrepriseDTO entrepriseDTO = mock(EntrepriseDTO.class);
    SchoolYearDTO schoolYearDTO = mock(SchoolYear.class);
    when(contactDAO.createOne(userDTO, entrepriseDTO, schoolYearDTO)).thenReturn(contact);
    when((User) userDTO).thenReturn(mock(User.class));

    assertEquals(contactUCC.createOne(userDTO, entrepriseDTO, schoolYearDTO), contact);*/
  }

  @Test
  void getAllContactsByUserId() {
    List<ContactDTO> contactList = new ArrayList<>();
    ContactDTO mockedContact = mock(ContactDTO.class);
    contactList.add(mockedContact);
    when(contactDAO.getAllContactsByUserId(contact.getUserId())).thenReturn(contactList);

    assertEquals(contactUCC.getAllContactsByUserId(contact.getUserId()), contactList);
  }

  @Test
  void meetContact() {
    String meetingType = "new type of meeting";
    contact.setMeetingType("type of meeting");
    contactResult.setMeetingType(meetingType);

    when(contactDAO.getOneContactById(contact.getId())).thenReturn(contact);
    when(contactDAO.meetContact(contact.getId(), meetingType)).thenReturn(contactResult);

    assertEquals(contactUCC.meetContact(contact.getId(), meetingType, contact.getUserId()),
        contactResult);
  }

  @Test
  void meetContactNullFromDAO() {
    String meetingType = "new type of meeting";
    contact.setMeetingType("type of meeting");
    contactResult.setMeetingType(meetingType);

    when(contactDAO.getOneContactById(contact.getId())).thenReturn(contact);
    when(contactDAO.meetContact(contact.getId(), meetingType)).thenReturn(null);

    assertNull(contactUCC.meetContact(contact.getId(), meetingType, contact.getUserId()));
  }

  @Test
  void meetContactWrongState() {
    String meetingType = "new type of meeting";
    contact.setMeetingType("type of meeting");
    contact.setState("annule");
    contactResult.setMeetingType(meetingType);

    when(contactDAO.getOneContactById(contact.getId())).thenReturn(contact);
    when(contactDAO.meetContact(contact.getId(), meetingType)).thenReturn(contactResult);

    assertThrows(BizException.class,
        () -> contactUCC.meetContact(contact.getId(), meetingType, contact.getUserId()));
  }

  @Test
  void meetContactWrongUser() {
    String meetingType = "new type of meeting";
    contactResult.setMeetingType(meetingType);
    contact.setUserId(456);

    when(contactDAO.getOneContactById(contact.getId())).thenReturn(contactResult);
    when(contactDAO.meetContact(contact.getId(), meetingType)).thenReturn(contactResult);

    // Assert that a BizException is thrown when trying to meet the contact
    assertThrows(BizExceptionNotFound.class,
        () -> contactUCC.meetContact(contact.getId(), meetingType, contact.getUserId()));
  }

  @Test
  void stopFollowContact() {
    contactResult.setState("suivis stoppe");

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contact);
    when(contactDAO.stopFollowContact(contact.getUserId())).thenReturn(contactResult);

    assertEquals(contactUCC.stopFollowContact(contact.getUserId(), contact.getUserId()),
        contactResult);
  }

  @Test
  void stopFollowContactNullFromDAO() {
    contactResult.setState("suivis stoppe");

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contact);
    when(contactDAO.stopFollowContact(contact.getUserId())).thenReturn(null);

    assertNull(contactUCC.stopFollowContact(contact.getUserId(), contact.getUserId()));
  }

  @Test
  void stopFollowContactWrongUser() {
    contactResult.setState("suivis stoppe");
    contact.setUserId(456);

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contactResult);
    when(contactDAO.stopFollowContact(contact.getUserId())).thenReturn(contactResult);

    assertThrows(BizExceptionNotFound.class,
        () -> contactUCC.stopFollowContact(contact.getUserId(), contact.getUserId()));
  }

  @Test
  void stopFollowContactWrongCheckMeet() {
    contactResult.setState("suivis stoppe");
    contact.setState("rencontre");

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contactResult);
    when(contactDAO.stopFollowContact(contact.getUserId())).thenReturn(contactResult);

    assertThrows(BizException.class,
        () -> contactUCC.stopFollowContact(contact.getUserId(), contact.getUserId()));
  }

  @Test
  void refusedContact() {
    String refusalReason = "raison de refus";
    contact.setState("rencontre");
    contactResult.setState("refuse");
    contactResult.setReasonForRefusal(refusalReason);

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contact);
    when(contactDAO.refusedContact(contact.getUserId(), refusalReason)).thenReturn(contactResult);

    assertEquals(contactUCC.refusedContact(contact.getUserId(), refusalReason, contact.getUserId()),
        contactResult);
  }

  @Test
  void refusedContactWrongState() {
    String refusalReason = "raison de refus";
    contact.setState("refuser");
    contactResult.setState("refuse");
    contactResult.setReasonForRefusal(refusalReason);

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contact);
    when(contactDAO.refusedContact(contact.getUserId(), refusalReason)).thenReturn(contactResult);

    assertThrows(BizException.class,
        () -> contactUCC.refusedContact(contact.getUserId(), refusalReason, contact.getUserId()));
  }

  @Test
  void refusedContactWrongUser() {
    String refusalReason = "raison de refus";
    contact.setState("rencontre");
    contact.setUserId(456);
    contactResult.setState("refuse");
    contactResult.setReasonForRefusal(refusalReason);

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contactResult);
    when(contactDAO.refusedContact(contact.getUserId(), refusalReason)).thenReturn(contactResult);

    assertThrows(BizExceptionNotFound.class,
        () -> contactUCC.refusedContact(contact.getUserId(), refusalReason, contact.getUserId()));
  }

  @Test
  void refusedContactNullFromDAO() {
    String refusalReason = "raison de refus";
    contact.setState("rencontre");
    contactResult.setState("refuse");
    contactResult.setReasonForRefusal(refusalReason);

    when(contactDAO.getOneContactById(contact.getUserId())).thenReturn(contact);
    when(contactDAO.refusedContact(contact.getUserId(), refusalReason)).thenReturn(null);

    assertNull(contactUCC.refusedContact(contact.getUserId(), refusalReason, contact.getUserId()));
  }
}
package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.Contact;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SchoolYear;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
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
    UserDTO userDTO = mock(UserDTO.class);
    EntrepriseDTO entrepriseDTO = mock(EntrepriseDTO.class);
    SchoolYearDTO schoolYearDTO = mock(SchoolYear.class);
    when(contactDAO.createOne(userDTO, entrepriseDTO, schoolYearDTO)).thenReturn(contact);
    when((User) userDTO).thenReturn(mock(User.class));

    assertEquals(contactUCC.createOne(userDTO, entrepriseDTO, schoolYearDTO), contact);
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
    System.out.println(contact);
    System.out.println(contactResult);

    when(contactDAO.getOneContactById(contact.getId())).thenReturn(contact);
    when(contactDAO.meetContact(contact.getId(), meetingType)).thenReturn(contactResult);

    assertEquals(contactUCC.meetContact(contact.getId(), meetingType, contact.getUserId()),
        contactResult);
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
}
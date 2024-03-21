package be.vinci.pae.business.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import be.vinci.pae.business.domain.Contact;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.services.ContactDAO;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.utils.TestApplicationBinder;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactUCCImplTest {

  private ContactUCC contactUCC;
  private Contact contact;
  private DomainFactory factory;
  private ContactDAO contactDAO;
  private DALServices dalServices;

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestApplicationBinder());
    this.contactUCC = locator.getService(ContactUCC.class);
    this.factory = locator.getService(DomainFactory.class);
    this.contactDAO = mock(ContactDAO.class);
    this.dalServices = mock(DALServices.class);
    //utiliser des factory
    this.contact = (Contact) factory.getContact();
  }


  @Test
  void createOne() {
    /*
    // Prepare test data
    ContactDTO expectedContact = mock(ContactDTO.class);
    when(contactUCC.createOne(any(UserDTO.class), any(EntrepriseDTO.class),
        any(SchoolYearDTO.class)))
        .thenReturn(expectedContact);

    // Perform the method call
    ContactDTO createdContact = contactUCC.createOne(mockUser, mockEnterprise, mockSchoolYear);
    assertEquals(expectedContact, createdContact); */
  }

  @Test
  void getAllContactsByUserId() {
    // Setup
    int userId = 123;
    List<ContactDTO> contacts = new ArrayList<>();
    // Fill contacts list with mock ContactDTO objects

    // Stubbing the behavior of contactDAO.getAllContactsByUserId
    when(contactDAO.getAllContactsByUserId(userId)).thenReturn(contacts);

    // Execution
    List<ContactDTO> result = contactUCC.getAllContactsByUserId(userId);

    // Verification
    assertEquals(contacts, result);
  }

  @Test
  void meetContact() {
    // Prepare test data
    int contactId = 1;
    int userId = 123;
    String meetingType = "Initial Meeting";
    ContactDTO expectedContact = mock(ContactDTO.class);
    when(contactDAO.meetContact(contactId, meetingType)).thenReturn(expectedContact);

    // Perform the method call
    ContactDTO contacted = contactUCC.meetContact(contactId, meetingType, userId);
    assertEquals(expectedContact, contacted);
  }


  @Test
  void stopFollowContact() {
    // Prepare test data
    int contactId = 1;
    int userId = 123;
    ContactDTO expectedContact = mock(ContactDTO.class);
    when(contactDAO.stopFollowContact(contactId)).thenReturn(expectedContact);

    // Perform the method call
    ContactDTO stoppedContact = contactUCC.stopFollowContact(contactId, userId);
    assertEquals(expectedContact, stoppedContact);
  }

  @Test
  void refusedContact() {
    // Prepare test data
    int contactId = 1;
    int userId = 123;
    String refusalReason = "Not interested";
    ContactDTO expectedContact = mock(ContactDTO.class);
    when(contactDAO.refusedContact(contactId, refusalReason)).thenReturn(
        expectedContact);

    // Perform the method call
    ContactDTO refused = contactUCC.refusedContact(contactId, refusalReason, userId);
    assertEquals(expectedContact, refused);
  }
}
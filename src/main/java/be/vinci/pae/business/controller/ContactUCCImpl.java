package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.Contact;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.services.ContactDAO;
import jakarta.inject.Inject;

public class ContactUCCImpl implements ContactUCC{
    @Inject
    private ContactDAO myContactDAO;
    @Override
    public ContactDTO newContact(int userID, int entrepriseId, int schoolYearId) {
        return (Contact) myContactDAO.createOne(userID, entrepriseId, schoolYearId);
    }
}

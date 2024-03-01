package be.vinci.pae.services;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SchoolYearDTO;
import be.vinci.pae.business.domain.UserDTO;

public interface ContactDAO {
    ContactDTO createOne(int userId, int entrepriseId, int schoolYearId);

    int nextItemId();
}

package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.business.domain.SupervisorDTO;

public interface SupervisorUCC {

    SupervisorDTO createOne(int id, String last_name, String first_name, EntrepriseDTO entreprise, String email, String numero);
}

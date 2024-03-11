package be.vinci.pae.services;

import be.vinci.pae.business.domain.SupervisorDTO;

public interface SupervisorDAO {

  SupervisorDTO getOneById(int id);
}

package be.vinci.pae.services;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.StageDTO;

public interface StageDAO {

  StageDTO getOneStageByUserId(int userId);
}

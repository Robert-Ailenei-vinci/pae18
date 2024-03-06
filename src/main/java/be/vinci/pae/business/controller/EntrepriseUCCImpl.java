package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.EntrepriseDTO;
import be.vinci.pae.services.EntrepriseDAO;
import jakarta.inject.Inject;

/**
 * This class is an implementation of the {@link EntrepriseUCC} interface.
 */
public class EntrepriseUCCImpl implements EntrepriseUCC {

  @Inject
  private EntrepriseDAO myEntrepriseDAO;

  @Override
  public EntrepriseDTO getOne(int entrepriseId) {
    return myEntrepriseDAO.getOne(entrepriseId);

  }
}

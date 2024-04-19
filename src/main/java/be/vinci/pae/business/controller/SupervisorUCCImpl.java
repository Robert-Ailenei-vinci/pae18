package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.*;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.SupervisorDAO;
import jakarta.inject.Inject;
import java.util.List;

/**
 * This class represents an implementation of the {@link SupervisorUCC} interface.
 */
public class SupervisorUCCImpl implements SupervisorUCC {

  @Inject
  private SupervisorDAO mySupervisorDAO;
  @Inject
  private DALServices dalServices;

  @Override
  public SupervisorDTO getOneById(int supervisorId) {
    try {
      // Start a new transaction
      dalServices.startTransaction();

      // Retrieve the EntrepriseDTO from the DAO
      SupervisorDTO supervisorDTO = mySupervisorDAO.getOneById(supervisorId);

      // Commit the transaction
      dalServices.commitTransaction();

      return supervisorDTO;
    } catch (Exception e) {
      // Rollback the transaction in case of an error
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  @Override
  public List<SupervisorDTO> getAll(int entrepriseId) {
    try {
      // Start a new transaction
      dalServices.startTransaction();

      // Retrieve the list of EntrepriseDTO from the DAO
      List<SupervisorDTO> supervisorDTOs = mySupervisorDAO.getAll(entrepriseId);

      // Commit the transaction
      dalServices.commitTransaction();

      return supervisorDTOs;
    } catch (Exception e) {
      // Rollback the transaction in case of an error
      dalServices.rollbackTransaction();
      throw e;
    }
  }

  public SupervisorDTO createOne(UserDTO user, String lastName, String firstName, int entrepriseId,
                                 String phoneNumber, String email) {
    try {
      dalServices.startTransaction();

      if (!((User) user).checkIsStudent()) {
        throw new BizException("This user is not a student.");
      }

      SupervisorDTO supervisorDTO = new SupervisorImpl();
      supervisorDTO.setLastName(lastName);
      supervisorDTO.setFirstName(firstName);
      supervisorDTO.setEntrepriseId(entrepriseId);
      supervisorDTO.setPhoneNumber(phoneNumber);
      supervisorDTO.setEmail(email);
      EntrepriseDTO entrepriseDTO = supervisorDTO.getEntreprise();
      Supervisor supervisor = (Supervisor) mySupervisorDAO.createOne(user,entrepriseDTO);

      dalServices.commitTransaction();

      return supervisor;
    } catch (Exception e) {
      dalServices.rollbackTransaction();
      throw e;
    }
  }
}

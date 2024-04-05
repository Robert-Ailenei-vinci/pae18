package be.vinci.pae.business.controller;

import be.vinci.pae.business.domain.*;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.SupervisorDAO;
import be.vinci.pae.utils.LoggerUtil;
import jakarta.inject.Inject;

public class SupervisorUCCImpl implements SupervisorUCC {

    @Inject
    private DALServices dalServices;

    @Inject
    private SupervisorDAO mySupervisor;

    @Override
    public SupervisorDTO createOne(String last_name, String first_name, int id_entreprise, String email, String numero) {
        try {
            dalServices.startTransaction();

            Supervisor supervisor = (Supervisor) mySupervisor.createOne(last_name, first_name, id_entreprise,
                    email, numero);

            dalServices.commitTransaction();

            return supervisor;
        } catch (Exception e) {
            LoggerUtil.logError("BizError", e);
            dalServices.rollbackTransaction();
            throw e;
        }
    }
}

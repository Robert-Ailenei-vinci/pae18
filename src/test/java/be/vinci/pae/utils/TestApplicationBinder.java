package be.vinci.pae.utils;


import be.vinci.pae.business.controller.ContactUCC;
import be.vinci.pae.business.controller.ContactUCCImpl;
import be.vinci.pae.business.controller.EntrepriseUCC;
import be.vinci.pae.business.controller.EntrepriseUCCImpl;
import be.vinci.pae.business.controller.SchoolYearUCC;
import be.vinci.pae.business.controller.SchoolYearUCCImpl;
import be.vinci.pae.business.controller.StageUCC;
import be.vinci.pae.business.controller.StageUCCImpl;
import be.vinci.pae.business.controller.UserUCC;
import be.vinci.pae.business.controller.UserUCCImpl;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.DomainFactoryImpl;
import be.vinci.pae.services.ContactDAO;
import be.vinci.pae.services.ContactDAOImpl;
import be.vinci.pae.services.DALServices;
import be.vinci.pae.services.DALServicesImpl;
import be.vinci.pae.services.EntrepriseDAO;
import be.vinci.pae.services.EntrepriseDAOImpl;
import be.vinci.pae.services.SchoolYearDAO;
import be.vinci.pae.services.SchoolYearDAOImpl;
import be.vinci.pae.services.StageDAO;
import be.vinci.pae.services.StageDAOImpl;
import be.vinci.pae.services.SupervisorDAO;
import be.vinci.pae.services.SupervisorDAOImpl;
import be.vinci.pae.services.UserDAO;
import be.vinci.pae.services.UserDAOImpl;
import jakarta.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * A utility class that provides bindings for dependencies used in testing scenarios. This class
 * extends {@code AbstractBinder}, allowing for custom bindings to be configured.
 */
public class TestApplicationBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bind(DomainFactoryImpl.class).to(DomainFactory.class).in(Singleton.class);
    bind(UserDAOImpl.class).to(UserDAO.class).in(Singleton.class);
    bind(DALServicesImpl.class).to(DALServices.class).in(Singleton.class);
    bind(UserUCCImpl.class).to(UserUCC.class).in(Singleton.class);
    bind(ContactUCCImpl.class).to(ContactUCC.class).in(Singleton.class);
    bind(ContactDAOImpl.class).to(ContactDAO.class).in(Singleton.class);
    bind(EntrepriseUCCImpl.class).to(EntrepriseUCC.class).in(Singleton.class);
    bind(EntrepriseDAOImpl.class).to(EntrepriseDAO.class).in(Singleton.class);
    bind(StageUCCImpl.class).to(StageUCC.class).in(Singleton.class);
    bind(StageDAOImpl.class).to(StageDAO.class).in(Singleton.class);
    bind(SupervisorDAOImpl.class).to(SupervisorDAO.class).in(Singleton.class);
    bind(SchoolYearUCCImpl.class).to(SchoolYearUCC.class).in(Singleton.class);
    bind(SchoolYearDAOImpl.class).to(SchoolYearDAO.class).in(Singleton.class);
  }
}

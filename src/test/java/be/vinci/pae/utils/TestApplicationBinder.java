package be.vinci.pae.utils;


import be.vinci.pae.business.controller.StageUCC;
import be.vinci.pae.business.controller.StageUCCImpl;
import be.vinci.pae.business.controller.UserUCC;
import be.vinci.pae.business.controller.UserUCCImpl;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.DomainFactoryImpl;
import be.vinci.pae.services.StageDAO;
import be.vinci.pae.services.StageDAOImpl;
import be.vinci.pae.services.UserDAO;
import be.vinci.pae.services.UserDAOImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.mockito.Mockito;

/**
 * A utility class that provides bindings for dependencies used in testing scenarios. This class
 * extends {@code AbstractBinder}, allowing for custom bindings to be configured.
 */
public class TestApplicationBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bind(UserUCCImpl.class).to(UserUCC.class);
    bind(Mockito.mock(UserDAOImpl.class)).to(UserDAO.class);
    bind(DomainFactoryImpl.class).to(DomainFactory.class);
    bind(StageUCCImpl.class).to(StageUCC.class);
    bind(Mockito.mock(StageDAOImpl.class)).to(StageDAO.class);
  }
}

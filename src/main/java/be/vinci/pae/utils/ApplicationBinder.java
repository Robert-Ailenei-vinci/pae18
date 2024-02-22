package be.vinci.pae.utils;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.DomainFactoryImpl;
import be.vinci.pae.services.UserDataService;
import be.vinci.pae.services.UserDataServiceImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * This class binds implementations to their corresponding interfaces using HK2 for
 * dependency injection.
 */
@Provider
public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(DomainFactoryImpl.class).to(DomainFactory.class).in(Singleton.class);
        bind(UserDataServiceImpl.class).to(UserDataService.class).in(Singleton.class);
    }
}

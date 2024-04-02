package be.vinci.pae.api.filters;

import be.vinci.pae.api.filters.Authorize.AuthorizeStudent;
import be.vinci.pae.api.filters.Authorize.AuthorizeSupervisor;
import be.vinci.pae.api.filters.Authorize.AuthorizeTeacher;
import jakarta.ws.rs.container.DynamicFeature;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.FeatureContext;

public class AuthorizationConfiguration implements DynamicFeature {

  @Override
  public void configure(ResourceInfo resourceInfo, FeatureContext context) {
    if (resourceInfo.getResourceMethod().isAnnotationPresent(AuthorizeStudent.class)) {
      context.register(AuthorizationRequestFilter.forRoles("etudiant"));
    }
    if (resourceInfo.getResourceMethod().isAnnotationPresent(AuthorizeTeacher.class)) {
      context.register(AuthorizationRequestFilter.forRoles("professeur"));
    }
    if (resourceInfo.getResourceMethod().isAnnotationPresent(AuthorizeSupervisor.class)) {
      context.register(AuthorizationRequestFilter.forRoles("administratif"));
    }
  }
}

package be.vinci.pae.api.filters;

import be.vinci.pae.api.filters.Authorize.AuthorizeStudent;
import be.vinci.pae.api.filters.Authorize.AuthorizeSupervisor;
import be.vinci.pae.api.filters.Authorize.AuthorizeTeacher;
import jakarta.ws.rs.container.DynamicFeature;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.FeatureContext;

/**
 * A dynamic feature for configuring authorization filters based on resource method annotations.
 * This class implements the {@link jakarta.ws.rs.container.DynamicFeature} interface.
 */
public class AuthorizationConfiguration implements DynamicFeature {

  /**
   * Configures authorization filters based on annotations present on resource methods.
   *
   * @param resourceInfo The resource information, including method annotations.
   * @param context      The feature context used for registering filters.
   */
  @Override
  public void configure(ResourceInfo resourceInfo, FeatureContext context) {
    // Check for @AuthorizeStudent annotation
    if (resourceInfo.getResourceMethod().isAnnotationPresent(AuthorizeStudent.class)) {
      // Register AuthorizationRequestFilter for student role
      context.register(AuthorizationRequestFilter.forRoles("etudiant"));
    }

    // Check for @AuthorizeTeacher annotation
    if (resourceInfo.getResourceMethod().isAnnotationPresent(AuthorizeTeacher.class)) {
      // Register AuthorizationRequestFilter for teacher role
      context.register(AuthorizationRequestFilter.forRoles("professeur"));
    }

    // Check for @AuthorizeSupervisor annotation
    if (resourceInfo.getResourceMethod().isAnnotationPresent(AuthorizeSupervisor.class)) {
      // Register AuthorizationRequestFilter for supervisor role
      context.register(AuthorizationRequestFilter.forRoles("administratif"));
    }
  }
}


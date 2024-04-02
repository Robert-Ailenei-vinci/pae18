package be.vinci.pae.api.filters;

import jakarta.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used as a name binding for authorization in JAX-RS resources and methods. It
 * serves as a container for role-specific annotations.
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {

  /**
   * An inner annotation to specify authorization for student role. This annotation can be applied
   * to JAX-RS resources and methods.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD, ElementType.TYPE})
  @interface AuthorizeStudent {

  }

  /**
   * An inner annotation to specify authorization for teacher role. This annotation can be applied
   * to JAX-RS resources and methods.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD, ElementType.TYPE})
  @interface AuthorizeTeacher {

  }

  /**
   * An inner annotation to specify authorization for supervisor role. This annotation can be
   * applied to JAX-RS resources and methods.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD, ElementType.TYPE})
  @interface AuthorizeSupervisor {

  }
}


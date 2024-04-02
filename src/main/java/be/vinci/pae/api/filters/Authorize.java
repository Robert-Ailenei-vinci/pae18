package be.vinci.pae.api.filters;

import jakarta.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used as a name binding for authorization in JAX-RS resources and methods.
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD, ElementType.TYPE})
  public @interface AuthorizeStudent {

  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD, ElementType.TYPE})
  public @interface AuthorizeTeacher {

  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD, ElementType.TYPE})
  public @interface AuthorizeSupervisor {

  }
}

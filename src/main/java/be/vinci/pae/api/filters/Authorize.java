package be.vinci.pae.api.filters;

import jakarta.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used as a name binding for authorization in JAX-RS resources and methods. It
 * serves as a container for role-specific annotations.
 *
 * <p>Example usage:
 * <pre>{@code
 * \@Authorize(roles = {"etudiant", "professeur"})
 * public class MyResource {
 *     \@GET
 *     public Response getSomeData() {
 *         // Method implementation
 *     }
 * }
 * }</pre>
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Authorize {

  /**
   * Specifies the roles allowed to access the resource or method. Default is an empty array,
   * indicating no specific roles required.
   *
   * @return An array of roles allowed to access the resource or method.
   */
  String[] roles() default {};
}

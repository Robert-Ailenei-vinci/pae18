package be.vinci.pae.api.filters;

import jakarta.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is used as a name binding for authorization in JAX-RS resources and methods.
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {

}

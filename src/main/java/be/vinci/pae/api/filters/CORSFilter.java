package be.vinci.pae.api.filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * An implementation of {@code ContainerResponseFilter} that enables Cross-Origin Resource Sharing
 * (CORS) by adding appropriate headers to the HTTP response.
 *
 * <p>This filter allows requests from any origin, and specifies the allowed HTTP methods and
 * headers
 * for CORS. It is typically used to enable cross-origin requests in web applications.</p>
 */
@Provider
public class CORSFilter implements ContainerResponseFilter {

  @Override
  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext)
      throws IOException {
    MultivaluedMap<String, Object> headers = responseContext.getHeaders();

    headers.add("Access-Control-Allow-Origin",
        "*"); // Permettre l'accès à partir de n'importe quelle origine
    headers.add("Access-Control-Allow-Methods",
        "GET, POST, DELETE, PUT"); // Méthodes HTTP autorisées
    headers.add("Access-Control-Allow-Headers", "Content-Type"); // En-têtes autorisés
  }
}

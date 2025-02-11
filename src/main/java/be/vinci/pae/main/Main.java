package be.vinci.pae.main;

import be.vinci.pae.utils.ApplicationBinder;
import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.WebExceptionMapper;
import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * This class represents the main entry point for the application.
 */
public class Main {

  /**
   * Base URI the Grizzly HTTP server will listen on.
   */
  public static final String BASE_URI;

  static {
    Config.load("dev.properties");
    BASE_URI = Config.getProperty("BaseUri");
  }

  /**
   * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
   *
   * @return Grizzly HTTP server.
   */
  public static HttpServer startServer() {

    // create a resource config that scans for JAX-RS resources and providers
    // in vinci.be package
    final ResourceConfig rc = new ResourceConfig().packages("be.vinci.pae.api")
        .register(ApplicationBinder.class)
        .register(WebExceptionMapper.class);

    // create and start a new instance of grizzly http server
    // exposing the Jersey application at BASE_URI
    return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
  }

  /**
   * The main method of the application.
   *
   * @param args Command line arguments.
   * @throws IOException If an I/O error occurs.
   */
  public static void main(String[] args) throws IOException {
    final HttpServer server = startServer();
    System.out.println(String.format("Jersey app started with WADL available at "
        + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
    System.in.read();
    server.stop();
  }
}

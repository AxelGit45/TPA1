package arg.com.utn.donatrack;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {

  public static final String BASE_URI = "http://0.0.0.0:8080/";

  public static void main(String[] args) throws IOException {
    final ResourceConfig config = new ResourceConfig();


    config.packages("arg.com.utn.donatrack.apis"); //Jersey busca en este paquete todos los @Path
    config.register(JacksonFeature.class); // Configuracion de jackson que convierte entre objetos java y json

    final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);

    System.out.println("Servidor DonaTrack levantado en " + BASE_URI);
    System.out.println("Entra a: http://localhost:8080/health");
    System.out.println("ENTER para pararlo");
    System.in.read();

    server.shutdownNow();
  }
}
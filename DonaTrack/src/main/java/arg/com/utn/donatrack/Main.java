package arg.com.utn.donatrack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.ws.rs.ext.ContextResolver;
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

    // Jersey va a escanear este paquete buscando clases con @Path
    config.packages("arg.com.utn.donatrack.resources", "arg.com.utn.donatrack.apis");
    config.register(JacksonFeature.class);

    config.register((ContextResolver<ObjectMapper>) type -> {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      return mapper;
    });

    final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);

    System.out.println("Servidor DonaTrack levantado en " + BASE_URI);
    System.out.println("Proba: http://localhost:8080/health");
    System.out.println("Presiona ENTER para detenerlo...");
    System.in.read();

    server.shutdownNow();
  }
}
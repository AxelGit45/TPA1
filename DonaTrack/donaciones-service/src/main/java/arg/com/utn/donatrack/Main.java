package arg.com.utn.donatrack;

import arg.com.utn.donatrack.grpc.usuarios.ServidorUsuariosGrpc;
import arg.com.utn.donatrack.tareas.AsignadorDeEntidadesParaDonaciones;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.grpc.Server;
import jakarta.ws.rs.ext.ContextResolver;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.net.URI;
import arg.com.utn.donatrack.grpc.usuarios.UsuariosGrpcService;

public class Main {

  //public static final String BASE_URI = "http://0.0.0.0:8080/";

  public static void main(String[] args) throws Exception {
    /*final ResourceConfig config = new ResourceConfig();

    // Jersey va a escanear este paquete buscando clases con @Path
    config.packages("arg.com.utn.donatrack.apis");
    config.register(JacksonFeature.class);

    config.register((ContextResolver<ObjectMapper>) type -> {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      return mapper;
    });

    final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);*/

    Server server = ServidorUsuariosGrpc.iniciar();
    server.awaitTermination();

    /*System.out.println("[DONACIONES-SERVICE] Levantado en " + BASE_URI);
    System.out.println("Proba: http://localhost:8080/health");
    System.out.println("Presiona ENTER para detenerlo...");
    try { Thread.currentThread().join(); } catch (InterruptedException ignored) {}

    server.shutdownNow();*/
  }
}

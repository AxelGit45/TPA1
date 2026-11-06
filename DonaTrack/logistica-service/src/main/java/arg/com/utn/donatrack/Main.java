package arg.com.utn.donatrack;

import arg.com.utn.donatrack.services.LogisticaGrpcServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class Main {
  public static void main(String[] args) throws Exception {

    int puerto = 50051;
    Server server = ServerBuilder.forPort(puerto)
        .addService(new LogisticaGrpcServiceImpl())
        .build();

    server.start();
    System.out.println("=================================================");
    System.out.println(" Servidor gRPC de Logística activo en puerto " + puerto);
    System.out.println("=================================================");

    server.awaitTermination();
  }
}

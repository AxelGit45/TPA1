package arg.com.utn.donatrack;

import arg.com.utn.donatrack.services.LogisticaGrpcServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class Main {


  public static void main(String[] args) throws Exception {

    //Configurar el puerto y registrar el servicio gRPC
    int puerto = 50051;
    Server server = ServerBuilder.forPort(puerto)
        .addService(new LogisticaGrpcServiceImpl())
        .build();

    //Iniciar la escucha de red
    server.start();
    System.out.println("=================================================");
    System.out.println(" Servidor gRPC de Logística activo en puerto " + puerto);
    System.out.println("=================================================");

    //Evitar que la aplicación de Java se cierre inmediatamente
    server.awaitTermination();
  }

}

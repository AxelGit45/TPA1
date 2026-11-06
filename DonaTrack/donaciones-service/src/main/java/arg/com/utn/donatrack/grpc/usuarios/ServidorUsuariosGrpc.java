package arg.com.utn.donatrack.grpc.usuarios;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ServidorUsuariosGrpc {

  public static final int PUERTO = 9091;

  public static Server iniciar() throws IOException {
    Server server = ServerBuilder.forPort(PUERTO)
        .addService(new UsuariosGrpcService())
        .build()
        .start();
    System.out.println("[DONACIONES-SERVICE] gRPC de usuarios escuchando en el puerto " + PUERTO);
    return server;
  }
}
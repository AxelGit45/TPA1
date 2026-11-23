package arg.com.utn.donatrack;

import arg.com.utn.donatrack.services.LogisticaGrpcServiceImpl;
import io.grpc.Attributes;
import io.grpc.Grpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerTransportFilter;
import java.net.SocketAddress;

public class Main {
  public static void main(String[] args) throws Exception {

    int puerto = 50051;
    Server server = ServerBuilder.forPort(puerto)
        .addService(new LogisticaGrpcServiceImpl())
        /*.build();*/
        .addTransportFilter(new ServerTransportFilter() {
          @Override
          public Attributes transportReady(Attributes transportAttrs) {
            SocketAddress clientIp = transportAttrs.get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR);
            System.out.println("[CONEXIÓN] Cliente conectado desde: " + clientIp);
            return super.transportReady(transportAttrs);
          }

          @Override
          public void transportTerminated(Attributes transportAttrs) {
            SocketAddress clientIp = transportAttrs.get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR);
            System.out.println("[DESCONEXIÓN] Cliente desconectado: " + clientIp);
            super.transportTerminated(transportAttrs);
          }
        })
        .build();



    server.start();
    System.out.println("=================================================");
    System.out.println(" Servidor gRPC de Logística activo en puerto " + puerto);
    System.out.println("=================================================");

    server.awaitTermination();
  }
}

package arg.com.utn.donatrack.grpc.usuarios;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ProbarUsuariosGrpc {

  public static void main(String[] args) {
    ManagedChannel canal = ManagedChannelBuilder
        .forAddress("localhost", ServidorUsuariosGrpc.PUERTO)
        .usePlaintext()
        .build();
    UsuariosServiceGrpc.UsuariosServiceBlockingStub stub = UsuariosServiceGrpc.newBlockingStub(canal);

    String email = "prueba" + System.currentTimeMillis() + "@mail.com";

    RegistrarDonanteResponse registro = stub.registrarDonante(RegistrarDonanteRequest.newBuilder()
        .setNombre("Prueba").setApellido("Grpc").setEmail(email).setContrasena("1234").build());
    System.out.println("Registro -> ok=" + registro.getOk()
        + " usuarioId=" + registro.getUsuarioId() + " error=" + registro.getError());

    AutenticarResponse bien = stub.autenticar(AutenticarRequest.newBuilder()
        .setEmail(email).setContrasena("1234").build());
    System.out.println("Login correcto -> ok=" + bien.getOk() + " rol=" + bien.getRol());

    AutenticarResponse mal = stub.autenticar(AutenticarRequest.newBuilder()
        .setEmail(email).setContrasena("incorrecta").build());
    System.out.println("Login con clave mala -> ok=" + mal.getOk());

    canal.shutdownNow();
  }
}
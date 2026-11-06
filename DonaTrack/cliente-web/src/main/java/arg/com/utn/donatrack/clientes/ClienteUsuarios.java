package arg.com.utn.donatrack.clientes;

import arg.com.utn.donatrack.grpc.usuarios.AutenticarRequest;
import arg.com.utn.donatrack.grpc.usuarios.AutenticarResponse;
import arg.com.utn.donatrack.grpc.usuarios.RegistrarDonanteRequest;
import arg.com.utn.donatrack.grpc.usuarios.RegistrarDonanteResponse;
import arg.com.utn.donatrack.grpc.usuarios.UsuariosServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ClienteUsuarios {

  private final UsuariosServiceGrpc.UsuariosServiceBlockingStub stub;

  public ClienteUsuarios(String host, int puerto) {
    ManagedChannel canal = ManagedChannelBuilder.forAddress(host, puerto)
        .usePlaintext()
        .build();
    this.stub = UsuariosServiceGrpc.newBlockingStub(canal);
  }

  public RegistrarDonanteResponse registrarDonante(String nombre, String apellido, int edad,
                                                   int numeroDeDocumento, String genero, String direccion,
                                                   String email, String telefono, String whatsapp,
                                                   String medioPredeterminado, String contrasena) {
    return stub.registrarDonante(RegistrarDonanteRequest.newBuilder()
        .setNombre(nombre)
        .setApellido(apellido)
        .setEdad(edad)
        .setNumeroDeDocumento(numeroDeDocumento)
        .setGenero(genero)
        .setDireccion(direccion)
        .setEmail(email)
        .setTelefono(telefono)
        .setWhatsapp(whatsapp)
        .setMedioPredeterminado(medioPredeterminado)
        .setContrasena(contrasena)
        .build());
  }

  public AutenticarResponse autenticar(String email, String contrasena) {
    return stub.autenticar(AutenticarRequest.newBuilder()
        .setEmail(email)
        .setContrasena(contrasena)
        .build());
  }
}
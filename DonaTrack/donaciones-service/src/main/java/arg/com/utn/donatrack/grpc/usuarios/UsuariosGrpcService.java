package arg.com.utn.donatrack.grpc.usuarios;

import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.usuarios.ServicioDeUsuarios;
import arg.com.utn.donatrack.usuarios.Usuario;
import io.grpc.stub.StreamObserver;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class UsuariosGrpcService extends UsuariosServiceGrpc.UsuariosServiceImplBase {

  private final ServicioDeUsuarios servicio = new ServicioDeUsuarios();

  @Override
  public void registrarDonante(RegistrarDonanteRequest request,
                               StreamObserver<RegistrarDonanteResponse> responseObserver) {
    EntityManager em = Persistencia.crearEntityManager();
    EntityTransaction tx = em.getTransaction();
    RegistrarDonanteResponse respuesta;
    try {
      tx.begin();
      Usuario usuario = servicio.registrarDonante(
          em,
          request.getNombre(),
          request.getApellido(),
          request.getEdad(),
          request.getNumeroDeDocumento(),
          request.getGenero(),
          request.getDireccion(),
          request.getEmail(),
          request.getTelefono(),
          request.getWhatsapp(),
          request.getMedioPredeterminado(),
          request.getContrasena());
      tx.commit();
      respuesta = RegistrarDonanteResponse.newBuilder()
          .setOk(true)
          .setUsuarioId(usuario.getId())
          .build();
    } catch (IllegalStateException e) {
      // Por ejemplo: ya existe una cuenta con ese email
      if (tx.isActive()) tx.rollback();
      respuesta = RegistrarDonanteResponse.newBuilder()
          .setOk(false)
          .setError(e.getMessage())
          .build();
    } catch (RuntimeException e) {
      if (tx.isActive()) tx.rollback();
      e.printStackTrace();
      respuesta = RegistrarDonanteResponse.newBuilder()
          .setOk(false)
          .setError("No se pudo registrar el usuario")
          .build();
    } finally {
      em.close();
    }
    responseObserver.onNext(respuesta);
    responseObserver.onCompleted();
  }

  @Override
  public void registrarDonanteJuridica(RegistrarDonanteJuridicaRequest request,
                                       StreamObserver<RegistrarDonanteResponse> responseObserver) {
    EntityManager em = Persistencia.crearEntityManager();
    EntityTransaction tx = em.getTransaction();
    RegistrarDonanteResponse respuesta;
    try {
      tx.begin();
      Usuario usuario = servicio.registrarDonanteJuridica(
          em,
          request.getRazonSocial(),
          request.getTipoRazonSocial(),
          request.getRubro(),
          request.getEmail(),
          request.getContrasena());
      tx.commit();
      respuesta = RegistrarDonanteResponse.newBuilder()
          .setOk(true)
          .setUsuarioId(usuario.getId())
          .build();
    } catch (IllegalStateException e) {
      if (tx.isActive()) tx.rollback();
      respuesta = RegistrarDonanteResponse.newBuilder()
          .setOk(false)
          .setError(e.getMessage())
          .build();
    } catch (RuntimeException e) {
      if (tx.isActive()) tx.rollback();
      e.printStackTrace();
      respuesta = RegistrarDonanteResponse.newBuilder()
          .setOk(false)
          .setError("No se pudo registrar el usuario")
          .build();
    } finally {
      em.close();
    }
    responseObserver.onNext(respuesta);
    responseObserver.onCompleted();
  }

  @Override
  public void autenticar(AutenticarRequest request,
                         StreamObserver<AutenticarResponse> responseObserver) {
    EntityManager em = Persistencia.crearEntityManager();
    AutenticarResponse respuesta;
    try {
      Usuario usuario = servicio.autenticar(em, request.getEmail(), request.getContrasena());
      if (usuario == null) {
        respuesta = AutenticarResponse.newBuilder().setOk(false).build();
      } else {
        respuesta = AutenticarResponse.newBuilder()
            .setOk(true)
            .setUsuarioId(usuario.getId())
            .setRol(usuario.getRol().name())
            .build();
      }
    } finally {
      em.close();
    }
    responseObserver.onNext(respuesta);
    responseObserver.onCompleted();
  }
}
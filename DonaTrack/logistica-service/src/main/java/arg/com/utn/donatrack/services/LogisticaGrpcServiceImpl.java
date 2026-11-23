package arg.com.utn.donatrack.services;

import arg.com.utn.donatrack.grpc.logistica.*;
import io.grpc.stub.StreamObserver;

public class LogisticaGrpcServiceImpl extends LogisticaServiceGrpc.LogisticaServiceImplBase{


  @Override
  public void planificarRutas(PlanificarRutasRequest request, StreamObserver<PlanificacionResponse> responseObserver) {
    System.out.println("Recibidas " + request.getDonacionesCount() + " donaciones para planificar.");

    // 1. Armar una entrega de prueba usando la primera donación recibida
    DonacionPlanificableProto primeraDonacion = request.getDonaciones(0);

    EntregaRutaProto entrega = EntregaRutaProto.newBuilder()
        .setEntregaId(100L)
        .addDonacionIds(primeraDonacion.getDonacionId()) // Para campos repeated se usa add...
        .setDireccionDestino(primeraDonacion.getDireccionDestino())
        .setOrden(1)
        .build();

    // 2. Armar la ruta que contiene esa entrega
    RutaResponse ruta = RutaResponse.newBuilder()
        .setRutaId(1L)
        .setCamionId(50L)
        .setPatenteCamion("AB123CD")
        .setLinkMapa("https://google.com")
        .addEntregas(entrega)
        .build();

    // 3. Armar la respuesta final (PlanificacionResponse)
    PlanificacionResponse response = PlanificacionResponse.newBuilder()
        .addRutas(ruta)
        .build();

    // 4. Enviar la respuesta al cliente y cerrar la llamada
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
  /*@Override
  public void planificarRutas(PlanificarRutasRequest request, StreamObserver<PlanificacionResponse> responseObserver) {
    // 1. LEER LOS DATOS ENVIADOS POR EL CLIENTE (donaciones-service)
    // 'request' contiene la lista de donaciones en formato ProtoBuf
    int cantidadDonaciones = request.getDonacionesCount();
    System.out.println("Solicitud gRPC recibida para planificar " + cantidadDonaciones + " donaciones.");

    // 2. EJECUTAR TU LÓGICA DE DOMINIO LOCAL
    // Acá llamas a tus clases/servicios locales de Logística para armar los camiones y rutas

    // 3. ARMAR EL MENSAJE DE RESPUESTA USANDO EL BUILDER AUTOGENERADO
    PlanificacionResponse.Builder responseBuilder = PlanificacionResponse.newBuilder();

    // Ejemplo armando una ruta de respuesta:
    RutaResponse rutaEjemplo = RutaResponse.newBuilder()
        .setRutaId(1L)
        .setCamionId(100L)
        .setPatenteCamion("AA123CD")
        .build();

    responseBuilder.addRutas(rutaEjemplo);

    PlanificacionResponse response = responseBuilder.build();

    // 4. ENVIAR LA RESPUESTA AL CLIENTE Y CERRAR LA COMUNICACIÓN
    responseObserver.onNext(response);      // Transmite la respuesta binaria
    responseObserver.onCompleted();         // Notifica que la llamada finalizó
  }*/

}

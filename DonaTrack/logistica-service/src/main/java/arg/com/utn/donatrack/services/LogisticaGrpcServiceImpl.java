package arg.com.utn.donatrack.services;

import arg.com.utn.donatrack.grpc.logistica.*;
import io.grpc.stub.StreamObserver;

public class LogisticaGrpcServiceImpl extends LogisticaServiceGrpc.LogisticaServiceImplBase{

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

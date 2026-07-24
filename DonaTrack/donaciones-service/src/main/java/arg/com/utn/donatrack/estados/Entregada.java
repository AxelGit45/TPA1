package arg.com.utn.donatrack.estados;

import arg.com.utn.donatrack.clients.LogisticaClient;
import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.dtos.CamionDTO;
import arg.com.utn.donatrack.dtos.EntregaDTO;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.personas.Persona;
import arg.com.utn.donatrack.personas.contactos.Contacto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Entregada extends EstadoDonacion {

  private Long entregaId;
  private Long camionId;

  public Entregada(){}

  public Entregada(Long entregaId, Long camionId) {
    this.entregaId = entregaId;
    this.camionId = camionId;
  }

  public Long getEntregaId() { return entregaId; }
  public Long getCamionId() { return camionId; }

  @Override
  public void notificar() {
    LogisticaClient logisticaClient = new LogisticaClient();
    EntregaDTO entrega = logisticaClient.obtenerEntrega(entregaId);
    CamionDTO camion = logisticaClient.obtenerCamion(camionId);

    LocalDateTime ahora = LocalDateTime.now();
    String fecha = ahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    String hora = ahora.format(DateTimeFormatter.ofPattern("HH:mm"));
    String datosCamion = camion != null ? camion.getPatente() : "N/A";
    String comprobante = "COMPROBANTE DE ENTREGA - Fecha: " + fecha +
        " | Hora: " + hora + " | Camión: " + datosCamion;

    System.out.println("[NOTIFICACIÓN ENTREGA] " + comprobante + " - Los bienes ya están en su establecimiento.");
    System.out.println("[NOTIFICACIÓN DONADOR] " + comprobante + " - Su donación ha sido entregada exitosamente.");

//    for (Contacto c : entrega.getEntidadBeneficiaria().getContactos()) {
//
//      c.contactar(comprobante + " - Los bienes ya están en su establecimiento.");
//
//    }
//
//    for (Donacion donacion : entrega.getDonacionesAEntregar()) {
//
//      for (Persona donador : donacion.getDonadores()) {
//
//        for (Contacto c : donador.getContactos()) {
//
//          c.contactar(comprobante + " - Su donación ha sido entregada exitosamente.");
//
//        }
//
//      }
//
//    }
  }

  @Override
  public void asignacionDonaciones(List<EntidadBeneficiaria> entidades, Donacion donacion){
    throw new RuntimeException("No es posible realizar el matchmaking, estado incorrecto");
  }

}

package arg.com.utn.donatrack.estados;

import arg.com.utn.donatrack.clients.LogisticaClient;
import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.dtos.CamionDTO;
import arg.com.utn.donatrack.dtos.EntregaDTO;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.personas.Administrador;
import arg.com.utn.donatrack.repositorios.RepositorioAdministradores;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EntregaFallida extends EstadoDonacion{

  private Long entregaId;
  private Long camionId;
  private String justificacion;

  public EntregaFallida(){}

  public EntregaFallida(Long entregaId, Long camionId, String justificacion){
    this.entregaId = entregaId;
    this.camionId = camionId;
    this.justificacion = justificacion;
  }

  @Override
  public void asignacionDonaciones(List<EntidadBeneficiaria> entidades, Donacion donacion){
    throw new RuntimeException("No es posible realizar el matchmaking, estado incorrecto");
  }

  @Override
  public void notificar() {
    LogisticaClient logisticaClient = new LogisticaClient();
    EntregaDTO entrega = logisticaClient.obtenerEntrega(entregaId);
    CamionDTO camion = logisticaClient.obtenerCamion(camionId);

    LocalDateTime ahora = LocalDateTime.now();
    String fecha = ahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    String hora = ahora.format(DateTimeFormatter.ofPattern("HH:mm"));
    String datosCamion = camion != null ? camion.getPatente() : "N/A";

    System.out.println("[NOTIFICACIÓN ENTIDAD] La entrega ha fallado.");

    System.out.println("[NOTIFICACIÓN DONADOR] La entrega ha fallado.");

    for (Administrador administrador : RepositorioAdministradores.getAdministradores()) {
      for (arg.com.utn.donatrack.personas.contactos.Contacto contacto : administrador.getContactos()) {
        contacto.contactar("ALERTA: Ha fallado la entrega ID " + entregaId);
      }
    }
  }

}

package arg.com.utn.donatrack.estados;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.logistica.Camion;
import arg.com.utn.donatrack.logistica.Entrega;
import arg.com.utn.donatrack.personas.Administrador;
import arg.com.utn.donatrack.personas.Persona;
import arg.com.utn.donatrack.personas.contactos.Contacto;
import arg.com.utn.donatrack.repositorios.RepositorioAdministradores;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EntregaFallida extends EstadoDonacion{

  private Entrega entrega;
  private Camion camion;
  private String justificacion;

  public EntregaFallida(Entrega entrega, Camion camion, String justificacion){

    this.entrega = entrega;
    this.camion = camion;
    this.justificacion = justificacion;

  }

  @Override
  public void asignacionDonaciones(List<EntidadBeneficiaria> entidades, Donacion donacion){
    throw new RuntimeException("No es posible realizar el matchmaking, estado incorrecto");
  }

  @Override
  public void notificar() {

    LocalDateTime ahora = LocalDateTime.now();
    String fecha = ahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    String hora = ahora.format(DateTimeFormatter.ofPattern("HH:mm"));
    String datosCamion = camion.getPatente();
    String comprobante = "COMPROBANTE DE ENTREGA - Fecha: " + fecha +
        " | Hora: " + hora + " | Camión: " + datosCamion;

    for (Contacto c : entrega.getEntidadBeneficiaria().getContactos()) {

      c.contactar("La entrega ha fallado.");

    }

    for (Donacion donacion : entrega.getDonacionesAEntregar()) {

      for (Persona donador : donacion.getDonadores()) {

        for (Contacto c : donador.getContactos()) {

          c.contactar("La entrega ha fallado.");

        }

      }

    }

    for (Administrador administrador : RepositorioAdministradores.getAdministradores()) {

      for (Contacto contacto : administrador.getContactos()) {

        contacto.contactar("ALERTA: Ha fallado la entrega ID " + entrega.getId() +
            " para la entidad " + entrega.getEntidadBeneficiaria().getRazonSocial());

      }

    }

  }

}

package donacion.estadosDonacion;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.logistica.Camion;
import arg.com.utn.donatrack.logistica.Entrega;
import arg.com.utn.donatrack.personas.Persona;
import arg.com.utn.donatrack.personas.contactos.Contacto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Entregada extends EstadoDonacion {

  private Entrega entrega;
  private Camion camion;

  public Entregada(){}

  public Entregada (Entrega entrega, Camion camion) {

    this.entrega = entrega;
    this.camion = camion;

  }

  public Entrega getEntrega() {
    return entrega;
  }

  public Camion getCamion() {
    return camion;
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

      c.contactar(comprobante + " - Los bienes ya están en su establecimiento.");

    }

    for (Donacion donacion : entrega.getDonacionesAEntregar()) {

      for (Persona donador : donacion.getDonadores()) {

        for (Contacto c : donador.getContactos()) {

          c.contactar(comprobante + " - Su donación ha sido entregada exitosamente.");

        }

      }

    }

  }

  @Override
  public void asignacionDonaciones(List<EntidadBeneficiaria> entidades, Donacion donacion){
    throw new RuntimeException("No es posible realizar el matchmaking, estado incorrecto");
  }

}
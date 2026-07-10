package arg.com.utn.donatrack.estados;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.logistica.Camion;
import arg.com.utn.donatrack.logistica.Entrega;
import arg.com.utn.donatrack.personas.Persona;
import arg.com.utn.donatrack.personas.contactos.Contacto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

  public void notificarEntrega() {

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

}
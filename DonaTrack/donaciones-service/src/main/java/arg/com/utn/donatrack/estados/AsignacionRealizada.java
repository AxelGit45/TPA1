package arg.com.utn.donatrack.estados;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.personas.Persona;
import arg.com.utn.donatrack.personas.contactos.Contacto;
import arg.com.utn.donatrack.personas.contactos.Mail;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import arg.com.utn.donatrack.donaciones.Bien;
import java.time.format.DateTimeFormatter;

@Entity
@DiscriminatorValue("ASIGNACION_REALIZADA")
public class AsignacionRealizada extends EstadoDonacion{
  @ManyToOne
  @JoinColumn(name = "entidad_beneficiaria_id")
  private EntidadBeneficiaria entidadBeneficiaria;
  @Transient
  private Donacion donacion;

  public AsignacionRealizada() {}

  public AsignacionRealizada(EntidadBeneficiaria entidadBeneficiaria, Donacion donacion){

    this.entidadBeneficiaria = entidadBeneficiaria;
    this.donacion = donacion;

  }

  @Override
  public void notificar() {

    String mensajeEntidad = armarMensajeEntidad();

    for (Contacto medioDeLaEntidad : this.entidadBeneficiaria.getContactos()) {
      try {
        medioDeLaEntidad.contactar(mensajeEntidad);
      } catch (Exception e) {
        // Si falla un medio (ej. SMS de Twilio), no se corta la asignación ni los demás avisos
        System.err.println("No se pudo notificar a la entidad por " + medioDeLaEntidad.getClass().getSimpleName() + ": " + e.getMessage());
      }
    }


    String mensajeDonante = armarMensajeDonante();
    donacion.getBienes().stream()
            .map(Bien::getDonador)
            .filter(donador -> donador != null)
            .distinct()
            .forEach(donador -> donador.getContactos().stream()
                    .filter(contacto -> contacto instanceof Mail)
                    .forEach(mail -> {
                      try {
                        mail.contactar(mensajeDonante);
                      } catch (Exception e) {
                        System.err.println("No se pudo notificar al donante por mail: " + e.getMessage());
                      }
                    }));

  }

  private String armarMensajeEntidad() {
    StringBuilder bienes = new StringBuilder();
    for (Bien bien : donacion.getBienes()) {
      bienes.append("  - ")
              .append(bien.getSubcategoria() != null ? bien.getSubcategoria().getNombre() : "Sin subcategoría")
              .append(": ").append(bien.getCantidad()).append(" ").append(bien.getUnidad());
      if (bien.getDescripcion() != null && !bien.getDescripcion().isBlank()) {
        bienes.append(" (").append(bien.getDescripcion()).append(")");
      }
      bienes.append("\n");
    }

    return "¡Hola " + entidadBeneficiaria.getRazonSocial() + "!\n\n"
            + "Les asignamos una nueva donación desde el depósito de DonaTrack.\n\n"
            + "Donación #" + donacion.getId()
            + (donacion.getDescripcion() != null ? " — " + donacion.getDescripcion() : "") + "\n"
            + "Fecha de asignación: " + getFechaDeAsignacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n\n"
            + "Bienes incluidos:\n"
            + bienes
            + "\nPueden ver el detalle en http://localhost:9000/entidad/donaciones\n"
            + "Les vamos a avisar cuando la donación salga en camino.\n\n"
            + "¡Gracias por el trabajo que hacen!\n"
            + "El equipo de DonaTrack";
  }


  private String armarMensajeDonante() {
    StringBuilder bienes = new StringBuilder();
    for (Bien bien : donacion.getBienes()) {
      bienes.append("  - ")
              .append(bien.getSubcategoria() != null ? bien.getSubcategoria().getNombre() : "Sin subcategoría")
              .append(": ").append(bien.getCantidad()).append(" ").append(bien.getUnidad());
      if (bien.getDescripcion() != null && !bien.getDescripcion().isBlank()) {
        bienes.append(" (").append(bien.getDescripcion()).append(")");
      }
      bienes.append("\n");
    }

    return "¡Hola!\n\n"
            + "Queremos contarte que tu donación ya tiene destino.\n\n"
            + "Donación #" + donacion.getId()
            + (donacion.getDescripcion() != null ? " — " + donacion.getDescripcion() : "") + "\n"
            + "Fue asignada a: " + entidadBeneficiaria.getRazonSocial() + "\n"
            + "Fecha de asignación: " + getFechaDeAsignacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n\n"
            + "Bienes donados:\n"
            + bienes
            + "\nPodés seguir tus donaciones en http://localhost:9000/donante/donaciones\n\n"
            + "¡Muchas gracias por donar!\n"
            + "El equipo de DonaTrack";
  }
}
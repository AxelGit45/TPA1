package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.donaciones.Subcategoria;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RECURRENTE")
public class NecesidadRecurrente extends Necesidad{

  private Integer recibidos;
  private LocalDate fechaLimite;

  public NecesidadRecurrente() {
  }

  public NecesidadRecurrente(Long id, Subcategoria necesidad, Integer cantidadNecesitada, String descripcion,
                             Boolean satisfecha, Integer recibidos, LocalDate fechaLimite){

    super(id,necesidad, cantidadNecesitada, descripcion, satisfecha);
    this.recibidos = recibidos;
    this.fechaLimite = fechaLimite;

  }
  public Subcategoria getSubcategoria(){
    return necesidad;
  }

  public Integer getRecibidos() {
    return recibidos;
  }

  public LocalDate getFechaLimite() {
    return fechaLimite;
  }

  public void recibir(Integer recibido){

    this.recibidos += recibido;

    if(this.recibidos >= this.cantidadNecesitada){

      if(LocalDate.now().isBefore(this.fechaLimite)){

        this.satisfecha = true;

      }

    }

  }

}
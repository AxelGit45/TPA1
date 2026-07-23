package necesidad;


import categoria.Subcategoria;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class NecesidadRecurrente extends Necesidad {

  private Integer recibidos;
  private ChronoLocalDate fechaLimite;

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

  public ChronoLocalDate getFechaLimite() {
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
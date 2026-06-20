package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.donaciones.Subcategoria;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;

public class NecesidadRecurrente extends Necesidad{

  private Integer recibidos;
  private ChronoLocalDate fechaLimite;

  public NecesidadRecurrente(Subcategoria necesidad, Integer cantidadNecesitada, String descripcion,
                             Boolean satisfecha, Integer recibidos, ChronoLocalDate fechaLimite){

    super(necesidad, cantidadNecesitada, descripcion, satisfecha);
    this.recibidos = recibidos;
    this.fechaLimite = fechaLimite;

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

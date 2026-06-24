package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.donaciones.Subcategoria;
import java.util.List;

public abstract class Necesidad {

  protected Subcategoria necesidad;
  protected Integer cantidadNecesitada;
  protected String descripcion;
  protected Boolean satisfecha;

  public Necesidad(Subcategoria necesidad, Integer cantidadNecesitada, String descripcion, Boolean satisfecha){

    this.necesidad = necesidad;
    this.cantidadNecesitada = cantidadNecesitada;
    this.descripcion = descripcion;
    this.satisfecha = satisfecha;

  }



}

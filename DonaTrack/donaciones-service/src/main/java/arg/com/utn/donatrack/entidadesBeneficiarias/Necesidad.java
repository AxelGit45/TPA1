package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.donaciones.Bien;
import arg.com.utn.donatrack.donaciones.Subcategoria;

public abstract class Necesidad {

  protected Long id;
  protected Subcategoria necesidad;
  protected Integer cantidadNecesitada;
  protected String descripcion;
  protected Boolean satisfecha;

  public Necesidad(Long id, Subcategoria necesidad, Integer cantidadNecesitada, String descripcion, Boolean satisfecha){

    this.id = id;
    this.necesidad = necesidad;
    this.cantidadNecesitada = cantidadNecesitada;
    this.descripcion = descripcion;
    this.satisfecha = satisfecha;

  }

  public boolean esSatisfechaPor(Bien bien){
    return necesidad == bien.getSubcategoria();
  }

  public Long getId() {
    return id;
  }

  public Integer getCantidadNecesitada() {
    return cantidadNecesitada;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public Boolean getSatisfecha() {
    return satisfecha;
  }

}
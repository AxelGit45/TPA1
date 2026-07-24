package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.donaciones.Subcategoria;

public class NecesidadExtraordinaria extends Necesidad{

  private Integer recibidos;

  public NecesidadExtraordinaria(Long id,
                                 Subcategoria necesidad,
                                 Integer cantidadNecesitada,
                                 String descripcion,
                                 Boolean satisfecha,
                                 Integer recibidos) {

    super(id, necesidad, cantidadNecesitada, descripcion, satisfecha);
    this.recibidos = recibidos;
  }
  public Subcategoria getSubcategoria(){
    return necesidad;
  }

  public Integer getRecibidos() {
    return recibidos;
  }

  public void recibir(Integer recibido){

    this.recibidos += recibido;

    if(this.recibidos >= this.cantidadNecesitada){
      this.satisfecha = true;
    }

  }
  public Long getId() {
    return id;
  }

}
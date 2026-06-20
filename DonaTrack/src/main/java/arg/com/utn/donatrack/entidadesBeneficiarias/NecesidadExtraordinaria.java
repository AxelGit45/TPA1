package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.donaciones.Subcategoria;

public class NecesidadExtraordinaria extends Necesidad{

  private Integer recibidos;

  public NecesidadExtraordinaria(Subcategoria necesidad, Integer cantidadNecesitada, String descripcion,
                                 Boolean satisfecha, Integer recibidos){

    super(necesidad, cantidadNecesitada, descripcion, satisfecha);

    this.recibidos = recibidos;

  }

  public void recibir(Integer recibido){

    this.recibidos += recibido;

    if(this.recibidos >= this.cantidadNecesitada){
      this.satisfecha = true;
    }

  }

}

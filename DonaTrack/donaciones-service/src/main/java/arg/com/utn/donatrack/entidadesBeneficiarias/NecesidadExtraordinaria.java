package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.donaciones.Subcategoria;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("EXTRAORDINARIA")
public class NecesidadExtraordinaria extends Necesidad{

  private Integer recibidos;

  public NecesidadExtraordinaria() {
  }

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
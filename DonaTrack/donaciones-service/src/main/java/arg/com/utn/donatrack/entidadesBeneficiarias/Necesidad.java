package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.donaciones.Bien;
import arg.com.utn.donatrack.donaciones.Subcategoria;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_necesidad")
@Table(name = "necesidades")
public abstract class Necesidad {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;
  @ManyToOne
  @JoinColumn(name = "subcategoria_id")
  protected Subcategoria necesidad;
  protected Integer cantidadNecesitada;
  protected String descripcion;
  protected Boolean satisfecha;

  public Necesidad() {
  }

  public Necesidad(Long id, Subcategoria necesidad, Integer cantidadNecesitada, String descripcion, Boolean satisfecha){

    this.id = id;
    this.necesidad = necesidad;
    this.cantidadNecesitada = cantidadNecesitada;
    this.descripcion = descripcion;
    this.satisfecha = satisfecha;

  }

  /*public boolean esSatisfechaPor(Bien bien){
    return necesidad == bien.getSubcategoria();
  }*/

  public boolean esSatisfechaPor(Bien bien){
    return necesidad != null && necesidad.equals(bien.getSubcategoria());
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
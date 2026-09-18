package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.donaciones.Bien;
import arg.com.utn.donatrack.donaciones.Subcategoria;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_necesidad", discriminatorType = DiscriminatorType.STRING)
@Table(name = "necesidad")

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
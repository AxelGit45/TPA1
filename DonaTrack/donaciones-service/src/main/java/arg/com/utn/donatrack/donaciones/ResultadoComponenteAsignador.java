package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.estados.EstadoDonacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table
public class ResultadoComponenteAsignador {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne  //--> Warning
  @JoinColumn(name = "entidad_id")
  private EntidadBeneficiaria entidad;

  @Transient
  private List<EntidadBeneficiaria> entidades = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "donacion_id")
  private Donacion donacion;

  public ResultadoComponenteAsignador(){

  }

  public ResultadoComponenteAsignador(List<EntidadBeneficiaria> entidades, Donacion donacion){
    this.entidad = entidades.get(0);
    this.donacion = donacion;
    //this.entidades = entidades;
  }

  public Long getId() {
    return id;
  }

  public List<EntidadBeneficiaria> getEntidades() {
    return entidades;
  }

  public Donacion getDonacion() {
    return donacion;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setEntidades(List<EntidadBeneficiaria> entidades) {
    this.entidades = entidades;
  }

  public void setDonacion(Donacion donacion) {
    this.donacion = donacion;
  }
}

package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.estados.EstadoDonacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class ResultadoComponenteAsignador {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Destino final: queda en null hasta que la administradora confirme
  @ManyToOne  //--> Warning
  @JoinColumn(name = "entidad_id")
  private EntidadBeneficiaria entidad;

  // Ids de todas las entidades candidatas que devolvió el componente
  @ElementCollection
  @CollectionTable(name = "resultado_entidades_candidatas", joinColumns = @JoinColumn(name = "resultado_id"))
  @Column(name = "entidad_id")
  private List<Long> entidadesCandidatasIds = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "donacion_id")
  private Donacion donacion;

  public ResultadoComponenteAsignador(){

  }

  public ResultadoComponenteAsignador(List<EntidadBeneficiaria> entidades, Donacion donacion){
    this.donacion = donacion;
    this.entidadesCandidatasIds = new ArrayList<>(entidades.stream().map(EntidadBeneficiaria::getId).toList());
  }

  public Long getId() {
    return id;
  }

  public EntidadBeneficiaria getEntidad() {
    return entidad;
  }

  public List<Long> getEntidadesCandidatasIds() {
    return entidadesCandidatasIds;
  }

  public Donacion getDonacion() {
    return donacion;
  }

  public void setEntidad(EntidadBeneficiaria entidad) {
    this.entidad = entidad;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setEntidadesCandidatasIds(List<Long> entidadesCandidatasIds) {
    this.entidadesCandidatasIds = entidadesCandidatasIds;
  }

  public void setDonacion(Donacion donacion) {
    this.donacion = donacion;
  }


}
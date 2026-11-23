package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.estados.EstadoDonacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table
public class ResultadoComponenteAsignador {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToMany()
  @JoinColumn(name = "entidad_id")
  private List<EntidadBeneficiaria> entidades;

  @OneToOne
  @JoinColumn(name = "donacion_id")
  private Donacion donacion;


  public ResultadoComponenteAsignador(List<EntidadBeneficiaria> entidades, Donacion donacion){
    this.donacion = donacion;
    this.entidades = entidades;
  }
}

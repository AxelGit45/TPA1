package arg.com.utn.donatrack.estados;

import arg.com.utn.donatrack.donaciones.Algoritmo;
import arg.com.utn.donatrack.donaciones.ComponenteAsignadorDeDestinatarios;
import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDate;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
    @JsonSubTypes.Type(value = EnDeposito.class, name = "EN_DEPOSITO"),
    @JsonSubTypes.Type(value = EnTraslado.class, name = "EN_TRASLADO"),
    @JsonSubTypes.Type(value = ListaParaEntregar.class, name = "LISTA_PARA_ENTREGAR"),
    @JsonSubTypes.Type(value = Entregada.class, name = "ENTREGADA"),
    @JsonSubTypes.Type(value = EntregaFallida.class, name = "ENTREGA_FALLIDA"),
    @JsonSubTypes.Type(value = Vencida.class, name = "VENCIDA")
})
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_estado")
public abstract class EstadoDonacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDate fechaDeAsignacion;

  public EstadoDonacion() {
    this.fechaDeAsignacion = LocalDate.now();
  }

  public LocalDate getFechaDeAsignacion() {
    return fechaDeAsignacion;
  }

  public void asignacionDonaciones(List<EntidadBeneficiaria> entidades, Donacion donacion){}
  public void setAlgoritmo(Algoritmo algoritmo){}
  public void notificar() {};
  public void setComponenteExterno(ComponenteAsignadorDeDestinatarios componenteExterno){}
}
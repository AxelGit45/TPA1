package arg.com.utn.donatrack.estados;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import java.time.LocalDate;
import java.util.List;

// Discriminador "tipo" en el JSON para que Jackson sepa a qué subclase deserializar
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
    @JsonSubTypes.Type(value = EnDeposito.class, name = "EN_DEPOSITO"),
    @JsonSubTypes.Type(value = EnTraslado.class, name = "EN_TRASLADO"),
    @JsonSubTypes.Type(value = ListaParaEntregar.class, name = "LISTA_PARA_ENTREGAR"),
    @JsonSubTypes.Type(value = Entregada.class, name = "ENTREGADA"),
    @JsonSubTypes.Type(value = Vencida.class, name = "VENCIDA")
})
public abstract class EstadoDonacion {

  private LocalDate fechaDeAsignacion;

  public EstadoDonacion() {
    this.fechaDeAsignacion = LocalDate.now();
  }

  public LocalDate getFechaDeAsignacion() {
    return fechaDeAsignacion;
  }

  public void matchmaking(List<EntidadBeneficiaria> entidades, Donacion donacion){}
}
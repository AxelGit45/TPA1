package arg.com.utn.donatrack.logistica;

import arg.com.utn.donatrack.donaciones.Donacion;
//import arg.com.utn.donatrack.donaciones.Estados;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.estados.EnDeposito;
import arg.com.utn.donatrack.estados.EnTraslado;
import arg.com.utn.donatrack.estados.EntregaFallida;
import arg.com.utn.donatrack.estados.Entregada;
import arg.com.utn.donatrack.estados.EstadoDonacion;

import java.time.LocalDate;
import java.util.List;

public class Entrega {
  private Long id;
  private String direccionEntidadBeneficiaria;
  private List<Donacion> donacionesAEntregar;
  private EstadoEntrega estadoEntrega;
  private Camion camionQueEntrega;
  private LocalDate fechaDeEntregaEsperada;
  private EntidadBeneficiaria entidadBeneficiaria;

  public Entrega(Long id, String direccionEntidadBeneficiaria, LocalDate fechaDeEntregaEsperada,
                 EntidadBeneficiaria entidadBeneficiaria) { // AGREGAR: Lista de donaciones y camion que entrega.
    this.id = id;
    this.direccionEntidadBeneficiaria = direccionEntidadBeneficiaria;
    this.fechaDeEntregaEsperada = fechaDeEntregaEsperada;
    this.estadoEntrega = EstadoEntrega.PENDIENTE;
    this.entidadBeneficiaria = entidadBeneficiaria;
  }
  public Long getId() { return id; }
  public List<Donacion> getDonaciones() {
    return this.donacionesAEntregar;
  }

  public void cambiarEstado(EstadoEntrega nuevoEstado){
    this.estadoEntrega = nuevoEstado;
  }

  public void iniciarTraslado() {
    this.cambiarEstado(EstadoEntrega.ENTRASLADO);
    donacionesAEntregar.forEach(donacion -> donacion.iniciarTraslado(camionQueEntrega));
  }

  /*---------------------------------------ACTUAL--------------------------------------------*/
  /** El registro ya quedó previamente hecho porque lo realizó el componente externo. **/
  public void confirmarRecepcionDeEntrega() {
    this.cambiarEstado(EstadoEntrega.ENTREGADA);
    entidadBeneficiaria.cargarFotosDeEntrega();
    donacionesAEntregar.forEach((donacion -> donacion.cambiarEstado(new Entregada())));
  }

  public void informarNoRecepcion() {
    if (this.entregaTardia()) {
      this.cambiarEstado(EstadoEntrega.NORECIBIDA);
      donacionesAEntregar.forEach(donacion -> // AGREGAR JUSTIFICACION
          donacion.cambiarEstado(new EntregaFallida(this, camionQueEntrega, null)));
    }
  }

  public boolean entregaTardia() {
    LocalDate fechaDeHoy = LocalDate.now();
    return fechaDeEntregaEsperada.isAfter(fechaDeHoy);
  }

  public void volverAPendiente() {
    if (donacionesAEntregar.stream().anyMatch(donacion -> donacion.getEstado() instanceof EnDeposito)) {
      this.cambiarEstado(EstadoEntrega.PENDIENTE);
    }
  }
  /*-----------------------------------------------------------------------------------------*/

  public LocalDate getFechaDeEntregaEsperada() {
    return fechaDeEntregaEsperada;
  }

  public void cambiarAPendiente() { // Método ejecutado por alguien externo (administrador)
    /*if (this.lasDonacionesEstanEnDeposito()) {
      this.cambiarEstado(EstadoEntrega.PENDIENTE);
    } // Lanzar error si las donaciones no están en depósito. Se entiende
    // que el administrador es quien regresa las donaciones al depósito. */
  }

  /* public boolean lasDonacionesEstanEnDeposito(){ // ¿Esta validación va? o, como lo hace un administrador, ¿ya se da por hecho?
    return donacionesAEntregar.stream().allMatch(donacion -> donacion.getEstado() == Estados.ENDEPOSITO);
  } */
  public String getDireccionEntidadBeneficiaria() {return direccionEntidadBeneficiaria;}
  public EntidadBeneficiaria getEntidadBeneficiaria() {return entidadBeneficiaria;}
  public List<Donacion> getDonacionesAEntregar() {return donacionesAEntregar;}
  public EstadoEntrega getEstadoEntrega() {return estadoEntrega;}
  public Camion getCamionQueLaEntrego() {return camionQueEntrega;}

  public void setDireccionEntidadBeneficiaria(String direccionEntidadBeneficiaria) { this.direccionEntidadBeneficiaria = direccionEntidadBeneficiaria; }
  public void setDonacionesAEntregar(List<Donacion> donacionesAEntregar) { this.donacionesAEntregar = donacionesAEntregar; }
  public void setEstadoEntrega(EstadoEntrega estadoEntrega) { this.estadoEntrega = estadoEntrega; }
  public void setCamionQueLaEntrego(Camion camionQueLaEntrego) { this.camionQueEntrega = camionQueLaEntrego; }
  public void setFechaDeEntregaEsperada(LocalDate fechaDeEntregaEsperada) { this.fechaDeEntregaEsperada = fechaDeEntregaEsperada; }
}

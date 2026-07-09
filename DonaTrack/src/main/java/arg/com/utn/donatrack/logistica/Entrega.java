package arg.com.utn.donatrack.logistica;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.donaciones.Estados;

import java.time.LocalDate;
import java.util.List;

public class Entrega {
  private Long id;
  private String direccionEntidadBeneficiaria;
  private List<Donacion> donacionesAEntregar;
  private EstadoEntrega estadoEntrega;
  private Camion camionQueLaEntrego;
  private LocalDate fechaDeEntregaEsperada;

  public Entrega() {
  }

  public Entrega(Long id, String direccionEntidadBeneficiaria, LocalDate fechaDeEntregaEsperada) {
    this.id = id;
    this.direccionEntidadBeneficiaria = direccionEntidadBeneficiaria;
    this.fechaDeEntregaEsperada = fechaDeEntregaEsperada;
    this.estadoEntrega = EstadoEntrega.PENDIENTE;
  }

  public Long getId() { return id; }

  public List<Donacion> getDonaciones() {
    return this.donacionesAEntregar;
  }

  public void cambiarEstado(EstadoEntrega nuevoEstado){
    this.estadoEntrega = nuevoEstado;
  }

  public void registrarCamion(Camion camionEntregador) {
    if (estadoEntrega == EstadoEntrega.ENTREGADA){
      this.camionQueLaEntrego = camionEntregador;
    } // Agregar throw new RuntimeException para que avise que hay
    // un error al ejecutar este método si se lo llama si
    // la entrega no está en estado Entregada.
  }

  public LocalDate getFechaDeEntregaEsperada() {
    return fechaDeEntregaEsperada;
  }

  public void cambiarAPendiente() { // Método ejecutado por alguien externo (administrador)
    if (this.lasDonacionesEstanEnDeposito()) {
      this.cambiarEstado(EstadoEntrega.PENDIENTE);
    } // Lanzar error si las donaciones no están en depósito. Se entiende
    // que el administrador es quien regresa las donaciones al depósito.
  }

  public boolean lasDonacionesEstanEnDeposito(){ // ¿Esta validación va? o, como lo hace un administrador, ¿ya se da por hecho?
    return donacionesAEntregar.stream().allMatch(donacion -> donacion.getEstado() == Estados.ENDEPOSITO);
  }
  public String getDireccionEntidadBeneficiaria() {return direccionEntidadBeneficiaria;}
  public List<Donacion> getDonacionesAEntregar() {return donacionesAEntregar;}
  public EstadoEntrega getEstadoEntrega() {return estadoEntrega;}
  public Camion getCamionQueLaEntrego() {return camionQueLaEntrego;}

  public void setDireccionEntidadBeneficiaria(String direccionEntidadBeneficiaria) { this.direccionEntidadBeneficiaria = direccionEntidadBeneficiaria; }
  public void setDonacionesAEntregar(List<Donacion> donacionesAEntregar) { this.donacionesAEntregar = donacionesAEntregar; }
  public void setEstadoEntrega(EstadoEntrega estadoEntrega) { this.estadoEntrega = estadoEntrega; }
  public void setCamionQueLaEntrego(Camion camionQueLaEntrego) { this.camionQueLaEntrego = camionQueLaEntrego; }
  public void setFechaDeEntregaEsperada(LocalDate fechaDeEntregaEsperada) { this.fechaDeEntregaEsperada = fechaDeEntregaEsperada; }
}

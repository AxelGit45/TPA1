package Modelos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class ReadyEntrega extends EstadoDeLaDonacion{
  @Setter
  @Getter
  private Boolean camionAsignado;

  public ReadyEntrega(EntidadBeneficiaria entidadAsignada, LocalDate fechaLimiteDeEntrega) {
    this.entidadAsignada = entidadAsignada;
    this.fechaLimiteDeEntrega = fechaLimiteDeEntrega;
    this.camionAsignado  = false;
  }
  @Override
  public void cambiarEstado(Donacion donacion) {
    if(cumpleCondicion()){
      donacion.setEstado(new Traslado(this.entidadAsignada, this.fechaLimiteDeEntrega));
      donacion.agregarEstadoAHistorial(this);
    }
  }

  @Override
  public boolean cumpleCondicion() {
    return getCamionAsignado();
  }
}

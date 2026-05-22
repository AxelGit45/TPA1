package Modelos;

import Errors.ErrorParametrosConstructor;

import java.time.LocalDate;

public class Traslado extends EstadoDeLaDonacion {
  private boolean entregado;

  public Traslado(EntidadBeneficiaria entidadAsignada, LocalDate fechaLimiteDeEntrega) {
    validarConstructor(entidadAsignada, fechaLimiteDeEntrega);
    this.entidadAsignada = entidadAsignada;
    this.fechaLimiteDeEntrega = fechaLimiteDeEntrega;
    this.entregado = false;

  }

  @Override
  public void cambiarEstado(Donacion donacion) {
    if(!cumpleCondicion()){
      donacion.setEstado(new EntregaFallida(this.entidadAsignada, this.fechaLimiteDeEntrega));
      donacion.agregarEstadoAHistorial(this);
    }
    donacion.setEstado(new Entregada(this.entidadAsignada, this.fechaLimiteDeEntrega));
    donacion.agregarEstadoAHistorial(this);
  }

  public boolean cumpleFecha(){
    LocalDate hoy = LocalDate.now();
    return !hoy.isAfter(fechaLimiteDeEntrega);
  }

  @Override
  public boolean cumpleCondicion() {
    return cumpleFecha() && getEntregado();
  }

  private boolean getEntregado() {
    return entregado;
  }
  private void validarConstructor(EntidadBeneficiaria entidadBeneficiaria, LocalDate fecha){
    if(entidadBeneficiaria == null || fecha == null){
      throw  new ErrorParametrosConstructor("Parametros nulos en el contructor");
    }
  }
}

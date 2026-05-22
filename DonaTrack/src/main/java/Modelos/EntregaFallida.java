package Modelos;

import Errors.ErrorParametrosConstructor;

import java.time.LocalDate;

public class EntregaFallida extends EstadoDeLaDonacion {
  private String justificacion;

  public EntregaFallida(EntidadBeneficiaria entidadAsignada, LocalDate fechaLimiteDeEntrega) {
    validarConstructor(entidadAsignada,fechaLimiteDeEntrega);
    this.entidadAsignada = entidadAsignada;
    this.fechaLimiteDeEntrega = fechaLimiteDeEntrega;
  }

  @Override
  public void cambiarEstado(Donacion donacion) {
    if(cumpleCondicion()){
      donacion.setEstado(new Deposito());
      donacion.agregarEstadoAHistorial(this);
    }
  }

  @Override
  public boolean cumpleCondicion() {
    return justificacion.length() > 0;
  }

  public String registrarJustificacion(String justicacion){
    return this.justificacion = justicacion;
  }

  private void validarConstructor(EntidadBeneficiaria entidadAsignada, LocalDate fechaLimiteDeEntrega) {
    if(entidadAsignada ==null || fechaLimiteDeEntrega == null){
      throw new ErrorParametrosConstructor("Faltan parámetros en el constructor.");
    }
  }
}

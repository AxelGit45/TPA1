package Modelos;

import Errors.ErrorParametrosConstructor;

import java.time.LocalDate;

public class Entregada extends EstadoDeLaDonacion {

  public Entregada(EntidadBeneficiaria entidadAsignada, LocalDate fechaLimiteDeEntrega) {
    validarConstructor(entidadAsignada,fechaLimiteDeEntrega);
    this.entidadAsignada = entidadAsignada;
    this.fechaLimiteDeEntrega = fechaLimiteDeEntrega;
  }
  @Override
  public void cambiarEstado(Donacion donacion) {
    if(cumpleCondicion()){
      //// le Agregar Donacion a la entidadBeneficiaria
      //// this.entidadAsignada.agregarmeLaDonacion(donacion)
      donacion.agregarEstadoAHistorial(this);
    }
  }
  @Override
  public boolean cumpleCondicion() {
      //TODO
    // this.entidadAsignada.verificarDonacion(donacion);
    return true;
  }
  private void validarConstructor(EntidadBeneficiaria entidadAsignada, LocalDate fechaLimiteDeEntrega) {
    if(entidadAsignada ==null || fechaLimiteDeEntrega == null){
      throw new ErrorParametrosConstructor("Faltan parámetros en el constructor.");
    }
  }
}

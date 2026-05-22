package Modelos;

import Errors.ErrorParametrosConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Asignada extends EstadoDeLaDonacion {
  private List<String> ruta;

  public Asignada(EntidadBeneficiaria entidadAsignada, LocalDate fechaLimiteDeEntrega) {
    validarConstructor(entidadAsignada,fechaLimiteDeEntrega);
    this.entidadAsignada = entidadAsignada;
    this.fechaLimiteDeEntrega = fechaLimiteDeEntrega;
    this.ruta = new ArrayList<>();
  }

  @Override
  public void cambiarEstado(Donacion donacion) {
    if(cumpleCondicion()){
      donacion.setEstado(new ReadyEntrega(this.entidadAsignada, this.fechaLimiteDeEntrega));
      donacion.agregarEstadoAHistorial(this);
    }
  }

  @Override
  public boolean cumpleCondicion() {
    return this.ruta.size() > 0;
  }

  private void validarConstructor(EntidadBeneficiaria entidadBeneficiaria, LocalDate fecha){
    if(entidadBeneficiaria == null || fecha == null){
      throw new ErrorParametrosConstructor("Faltan parametros en el contructor");
    }
  }
}

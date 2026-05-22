package Modelos;

import java.time.LocalDate;

public class Deposito extends EstadoDeLaDonacion{

  @Override
  public void cambiarEstado(Donacion donacion) {

    if(cumpleCondicion()){
      Asignada asignada = new Asignada(this.entidadAsignada, this.fechaLimiteDeEntrega);
      donacion.setEstado(asignada);
      donacion.agregarEstadoAHistorial(this);
    }
  }

  @Override
  public boolean cumpleCondicion() {
    return this.entidadAsignada != null;
  }

  public void cambiarEstadoDonacion(Donacion donacion){
    donacion.setEstado(new Vencida());
  }

}

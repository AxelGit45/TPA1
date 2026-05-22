package Modelos;

public class Vencida extends EstadoDeLaDonacion {
  @Override
  public void cambiarEstado(Donacion donacion) {
    if (cumpleCondicion()) { // TODO
      donacion.agregarEstadoAHistorial(this);
    }
  }

  @Override
  public boolean cumpleCondicion() {
    return true;
  }
}

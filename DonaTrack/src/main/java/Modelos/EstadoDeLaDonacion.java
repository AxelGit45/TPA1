package Modelos;

import java.time.LocalDate;

public abstract class EstadoDeLaDonacion {
  public EntidadBeneficiaria entidadAsignada;
  public LocalDate fechaLimiteDeEntrega;

  public abstract void cambiarEstado(Donacion donacion);
  public abstract boolean cumpleCondicion() ;
}

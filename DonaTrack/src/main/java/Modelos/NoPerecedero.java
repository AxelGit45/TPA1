package Modelos;

import Enums.TipoCategoria;
import Enums.EstadoDeUso;

public class NoPerecedero extends TipoDeBien{
  private EstadoDeUso estado;

  public NoPerecedero(String descripcion, String stringFoto, int cantidad, TipoCategoria categoria, String subcategoria, EstadoDeUso estado) {
    super(descripcion, stringFoto, cantidad, categoria, subcategoria);
    this.estado = estado;
  }

}

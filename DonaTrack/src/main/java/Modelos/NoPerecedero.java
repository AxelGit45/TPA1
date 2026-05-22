package Modelos;

import Enums.TipoCategoria;
import Enums.EstadoDeUso;
import Errors.ErrorParametrosConstructor;

public class NoPerecedero extends TipoDeBien{
  private EstadoDeUso estado;

  public NoPerecedero(String descripcion, String stringFoto, int cantidad, TipoCategoria categoria, String subcategoria, EstadoDeUso estado) {
    super(descripcion, stringFoto, cantidad, categoria, subcategoria);
    validarConstructor(estado);
    this.estado = estado;
  }

  private void validarConstructor(EstadoDeUso estado){
    if (estado == null) {
      throw new ErrorParametrosConstructor("Falta el parámetro 'estado'");
    }
  }
}

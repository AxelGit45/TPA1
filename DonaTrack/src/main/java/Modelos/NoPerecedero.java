package Modelos;

import Enums.EstadoDelBien;
import Enums.TipoCategoria;
import Enums.EstadoDeUso;
import Errors.ErrorParametrosConstructor;

public class NoPerecedero implements TipoDeBien{
  private EstadoDeUso estadoDeUso;

  public NoPerecedero(EstadoDelBien estado, EstadoDeUso estadoDeUso) {
    validarConstructor(estadoDeUso);
    this.estadoDeUso = estadoDeUso;
  }

  /*
  public NoPerecedero(String descripcion, String stringFoto, int cantidad, TipoCategoria categoria, String subcategoria, EstadoDeUso estado) {
    super(descripcion, stringFoto, cantidad, categoria, subcategoria);
    validarConstructor(estado);
    this.estado = estado;
  }*/

  private void validarConstructor(EstadoDeUso estado){
    if (estado == null) {
      throw new ErrorParametrosConstructor("Falta el parámetro 'estado'");
    }
  }
}

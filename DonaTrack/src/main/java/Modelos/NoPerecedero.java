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

  private void validarConstructor(EstadoDeUso estado){
    if (estado == null) {
      throw new ErrorParametrosConstructor("Falta el parámetro 'estado'");
    }
  }
}

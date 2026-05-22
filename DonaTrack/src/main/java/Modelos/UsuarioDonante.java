package Modelos;
import Errors.ErrorParametrosConstructor;

import java.util.List;

public class UsuarioDonante {

  private int id;
  private String nombreDeUsuario;
  private List<Donacion> donaciones;

  public UsuarioDonante(int id, String nombreDeUsuario, List<Donacion> donaciones) {
    validarConstructor(id, nombreDeUsuario, donaciones);
    this.id = id;
    this.nombreDeUsuario = nombreDeUsuario;
    this.donaciones = donaciones;
  }

  private  void validarConstructor(int id , String nombreDeUsuario, List<Donacion> donaciones){
    if (id <= 0 || nombreDeUsuario == null || donaciones == null) {
      throw new ErrorParametrosConstructor("Faltan parámetros en el constructor.");
    }
  }

}

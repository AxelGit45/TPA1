package Modelos;

import Enums.EstadoDeUso;
import Enums.EstadoDelBien;
import Enums.TipoCategoria;
import Errors.ErrorParametrosConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bien {

  private String descripcion;
  private String stringFoto;
  private int cantidad;
  private TipoCategoria categoria;
  private String subcategoria;
  private TipoDeBien tipo;

  public Bien(String descripcion, String stringFoto, int cantidad, TipoCategoria categoria, String subcategoria) {
    validarConstructor(descripcion,cantidad,categoria,subcategoria);
    this.descripcion = descripcion;
    this.stringFoto = (stringFoto == null) ? "" : stringFoto;
    this.cantidad = cantidad;
    this.categoria = categoria;
    this.subcategoria = subcategoria;
  }

  public TipoDeBien getTipoDelBien(){
    return this.tipo;
  }

  public void setTipo(TipoDeBien tipo) {
    this.tipo = tipo;
  }

  public String getSubcategoria() {
    return subcategoria;
  }

  private void validarConstructor(String descripcion, int cantidad, TipoCategoria categoria, String subcategoria) {
    if(descripcion == null || cantidad <= 0 || categoria == null || subcategoria == null ){
      throw new ErrorParametrosConstructor("Faltan parámetros en el constructor.");
    }
  }

}

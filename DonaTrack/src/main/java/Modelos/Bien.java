package Modelos;

import Enums.EstadoDeUso;
import Enums.EstadoDelBien;
import Enums.TipoCategoria;

public class Bien {

  private String descripcion;
  private String stringFoto; // URL opcional
  private int cantidad;
  private TipoCategoria categoria;
  private String subcategoria;
  private TipoDeBien tipo;

  public Bien(String descripcion, String stringFoto, int cantidad, TipoCategoria categoria, String subcategoria) {
    this.descripcion = descripcion;
    this.stringFoto = stringFoto;
    this.cantidad = cantidad;
    this.categoria = categoria;
    this.subcategoria = subcategoria;
  }


  public TipoDeBien getTipoDelBien(){
    return this.tipo;
  }

  public boolean getNoPerecedero() {
    return false;
  }


  public void setSubcategoria(String subcategoria) {
    this.subcategoria = subcategoria;
  }

  public void setCategoria(TipoCategoria categoria) {
    this.categoria = categoria;
  }

  public void setTipo(TipoDeBien tipo) {
    this.tipo = tipo;
  }

  public String getSubcategoria() {
    return subcategoria;
  }


}

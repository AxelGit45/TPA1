package Modelos;

public class Bien {

  private String decripcion;
  private String stringFoto;
  private int cantidad;
  private TipoCategoria categoria;

  public String getDecripcion() {
    return decripcion;
  }

  public void setDecripcion(String decripcion) {
    this.decripcion = decripcion;
  }

  public String getStringFoto() {
    return stringFoto;
  }

  public void setStringFoto(String stringFoto) {
    this.stringFoto = stringFoto;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public TipoCategoria getCategoria() {
    return categoria;
  }

  public void setCategoria(TipoCategoria categoria) {
    this.categoria = categoria;
  }
}

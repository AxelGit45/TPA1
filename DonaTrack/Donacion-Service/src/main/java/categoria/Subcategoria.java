package categoria;

public class Subcategoria {

  private String nombre;
  private Categoria categoriaPadre;
  private Boolean exigeFechaVencimiento;
  private Boolean exigeEstadoUso;

  public Subcategoria() {
  }

  public Subcategoria(String nombre, Categoria categoriaPadre, Boolean exigeFechaVencimiento, Boolean exigeEstadoUso){

    this.nombre = nombre;
    this.categoriaPadre = categoriaPadre;
    this.exigeFechaVencimiento = exigeFechaVencimiento;
    this.exigeEstadoUso = exigeEstadoUso;

  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Categoria getCategoriaPadre() {
    return categoriaPadre;
  }

  public void setCategoriaPadre(Categoria categoriaPadre) {
    this.categoriaPadre = categoriaPadre;
  }

  public Boolean getExigeFechaVencimiento() {
    return exigeFechaVencimiento;
  }

  public void setExigeFechaVencimiento(Boolean exigeFechaVencimiento) {
    this.exigeFechaVencimiento = exigeFechaVencimiento;
  }

  public Boolean getExigeEstadoUso() {
    return exigeEstadoUso;
  }

  public void setExigeEstadoUso(Boolean exigeEstadoUso) {
    this.exigeEstadoUso = exigeEstadoUso;
  }
}
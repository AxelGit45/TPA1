package arg.com.utn.donatrack.donaciones;

public class Subcategoria {

  private String nombre;
  private Categoria categoriaPadre;
  private Boolean exigeFechaVencimiento;
  private Boolean exigeEstadoUso;

  public Subcategoria(String nombre, Categoria categoriaPadre, Boolean exigeFechaVencimiento, Boolean exigeEstadoUso){

    this.nombre = nombre;
    this.categoriaPadre = categoriaPadre;
    this.exigeFechaVencimiento = exigeFechaVencimiento;
    this.exigeEstadoUso = exigeEstadoUso;

  }

}

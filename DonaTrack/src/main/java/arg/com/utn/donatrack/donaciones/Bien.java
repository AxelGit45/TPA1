package arg.com.utn.donatrack.donaciones;

import java.time.LocalDate;
import java.util.Date;

public class Bien {

  private String descripcion;
  private String foto;
  private Subcategoria subcategoria;
  private Integer cantidad;
  private Unidad unidad;
  private Date fechaVencimiento;
  private EstadoUso estadoUso;

  public Bien(String descripcion, String foto, Subcategoria subcategoria, Integer cantidad, Unidad unidad,
              Date fechaVencimiento, EstadoUso estadoUso){

    this.descripcion = descripcion;
    this.foto = foto;
    this.subcategoria = subcategoria;
    this.cantidad = cantidad;
    this.unidad = unidad;
    this.fechaVencimiento = fechaVencimiento;
    this.estadoUso = estadoUso;

  }

}

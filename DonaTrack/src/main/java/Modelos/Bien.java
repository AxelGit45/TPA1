package Modelos;

import Enums.EstadoDeUso;
import Enums.EstadoDelBien;
import Enums.TipoCategoria;

public class Bien {

  private String descripcion;
  private String stringFoto;
  private int cantidad;
  private TipoCategoria categoria;
  private String subcategoria;
  private TipoDeBien tipo;
  private EstadoDelBien estadoDelBien; // AÑADIDO

  public Bien(String descripcion, String stringFoto, int cantidad, TipoCategoria categoria, String subcategoria) {
    this.descripcion = descripcion;
    this.stringFoto = stringFoto;
    this.cantidad = cantidad;
    this.categoria = categoria; // Alimento, Mobiliario, Vestimenta
    this.subcategoria = subcategoria; // Alimento (fideos secos, arroz, legumbres secas, aceite vegetal)
  }

  public EstadoDelBien getEstadoDelBien(){
    return this.estadoDelBien;
  }

  public TipoDeBien getTipoDelBien(){
    return this.tipo;
  }

}

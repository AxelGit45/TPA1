package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.personas.Persona;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "bien")
public class Bien {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String descripcion;
  private String foto;
  @Embedded
  private Subcategoria subcategoria;
  private Integer cantidad;
  @Enumerated(EnumType.STRING)
  private Unidad unidad;
  private Date fechaVencimiento;
  @Enumerated(EnumType.STRING)
  private EstadoUso estadoUso;
  private boolean perecedero;
  @ManyToOne
  @JoinColumn(name = "persona_id")
  private Persona donador;

  public Bien(){}

  public Bien(String descripcion, String foto, Subcategoria subcategoria, Integer cantidad, Unidad unidad,
              Date fechaVencimiento, EstadoUso estadoUso, boolean perecedero, Persona donador){

    this.descripcion = descripcion;
    this.foto = foto;
    this.subcategoria = subcategoria;
    this.cantidad = cantidad;
    this.unidad = unidad;
    this.fechaVencimiento = fechaVencimiento;
    this.estadoUso = estadoUso;
    this.perecedero= perecedero;
    this.donador = donador;

  }
  public boolean esPerecedero(){
    return perecedero;
  }

  public Subcategoria getSubcategoria(){return subcategoria;}


  public boolean isPerecedero() {
    return perecedero;
  }

  public void setPerecedero(boolean perecedero) {
    this.perecedero = perecedero;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getFoto() {
    return foto;
  }

  public void setFoto(String foto) {
    this.foto = foto;
  }

  public void setSubcategoria(Subcategoria subcategoria) {
    this.subcategoria = subcategoria;
  }

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer cantidad) {
    this.cantidad = cantidad;
  }

  public Unidad getUnidad() {
    return unidad;
  }

  public void setUnidad(Unidad unidad) {
    this.unidad = unidad;
  }

  public Date getFechaVencimiento() {
    return fechaVencimiento;
  }

  public void setFechaVencimiento(Date fechaVencimiento) {
    this.fechaVencimiento = fechaVencimiento;
  }

  public EstadoUso getEstadoUso() {
    return estadoUso;
  }

  public void setEstadoUso(EstadoUso estadoUso) {
    this.estadoUso = estadoUso;
  }

  public Persona getDonador() {
    return donador;
  }
}
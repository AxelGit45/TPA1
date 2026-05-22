package Modelos;

import Enums.EstadoDelBien;
import Enums.EstadoDonacion;
import Errors.ErrorParametrosConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Donacion {

  private String nombreDonante;
  private List<Bien> bienes;
  private int id;
  private String descripcionGeneral;
  @Setter
  private EstadoDeLaDonacion estado;
  private List<EstadoDeLaDonacion> historialDeEstados;
  private Boolean realizada;
  private EstadoDonacion estadoDonacion;

  public Donacion(String nombreDonante, List<Bien> bienes, int id,String descripcionGeneral, EstadoDonacion estadoDonacion) {
    validarConstructor(nombreDonante, bienes, id, descripcionGeneral, estadoDonacion);
    this.descripcionGeneral = descripcionGeneral;
    this.nombreDonante = nombreDonante;
    this.bienes = bienes;
    this.estado = new Deposito();
    this.historialDeEstados = new ArrayList<>();
    this.id = id;
    this.realizada = false;
    this.estadoDonacion = estadoDonacion;
  }

  public void agregarEstadoAHistorial(EstadoDeLaDonacion estadoParaAgregar) {
    historialDeEstados.add(estadoParaAgregar);
  }

  private void validarConstructor(String nombreDonante, List<Bien> bienes, int id,String descripcionGeneral, EstadoDonacion estadoDonacion) {
    if(nombreDonante == null || bienes == null || id <= 0 || descripcionGeneral == null || estadoDonacion == null){
      throw new ErrorParametrosConstructor("Faltan parámetros en el constructor.");
    }
  }
}

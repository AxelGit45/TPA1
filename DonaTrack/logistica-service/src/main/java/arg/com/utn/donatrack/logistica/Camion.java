package arg.com.utn.donatrack.logistica;

import java.util.ArrayList;
import java.util.List;

public class Camion {

    private Long id;
    private String patente;
    private Integer volumen;
    private Integer altura;
    private Integer capacidadDeCarga;
    private List<Long> bienesTransportados;
    private String idGps;
    private Double latitud;
    private Double longitud;

  public Camion() {
    this.bienesTransportados = new ArrayList<>();
  }
  public Camion(String patente, Integer volumen, Integer altura, Integer capacidadDeCarga) {
    this.patente = patente;
    this.volumen = volumen;
    this.altura = altura;
    this.capacidadDeCarga = capacidadDeCarga;
    this.bienesTransportados = new ArrayList<>();
  }

  public Long getId() { return id; }
  public String getPatente() {return patente;}
  public Integer getVolumen() {return volumen;}
  public Integer getAltura() {return altura;}
  public Integer getCapacidadDeCarga() {return capacidadDeCarga;}
  public List<Long> getBienesTransportados() {return bienesTransportados;}
  public String getIdGps() {return idGps;}
  public Double getLatitud() {return latitud;}
  public Double getLongitud() {return longitud;}

  public void setId(Long id) { this.id = id; }
  public void setPatente(String patente) { this.patente = patente; }
  public void setVolumen(Integer volumen) { this.volumen = volumen; }
  public void setAltura(Integer altura) { this.altura = altura; }
  public void setCapacidadDeCarga(Integer capacidadDeCarga) { this.capacidadDeCarga = capacidadDeCarga; }
  public void setBienesTransportados(List<Long> bienesTransportados) { this.bienesTransportados = bienesTransportados; }
  public void setIdGps(String idGps) {this.idGps = idGps;}
  public void setLatitud(Double latitud) {this.latitud = latitud;}
  public void setLongitud(Double longitud) {this.longitud = longitud;}
}

package arg.com.utn.donatrack.logistica;

import arg.com.utn.donatrack.donaciones.Bien;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import java.util.List;

public class Camion {

    private String patente;
    private Integer volumen;
    private Integer altura;
    private Integer capacidadDeCarga;
    private List<Bien> bienesTransportados;
    private String idGps;
    private Double latitud;
    private Double longitud;

  public Camion() {
  }
  public Camion(String patente, Integer volumen, Integer altura, Integer capacidadDeCarga) {
    this.patente = patente;
    this.volumen = volumen;
    this.altura = altura;
    this.capacidadDeCarga = capacidadDeCarga;
  }

  /*
  {
    camion,
    la lista de destinos
  }
  */
  public String getPatente() {return patente;}
  public Integer getVolumen() {return volumen;}
  public Integer getAltura() {return altura;}
  public Integer getCapacidadDeCarga() {return capacidadDeCarga;}
  public List<Bien> getBienesTransportados() {return bienesTransportados;}
  public String getIdGps() {return idGps;};
  public Double getLatitud() {return latitud;};
  public Double getLongitud() {return longitud;};

  public void setPatente(String patente) { this.patente = patente; }
  public void setVolumen(Integer volumen) { this.volumen = volumen; }
  public void setAltura(Integer altura) { this.altura = altura; }
  public void setCapacidadDeCarga(Integer capacidadDeCarga) { this.capacidadDeCarga = capacidadDeCarga; }
  public void setBienesTransportados(List<Bien> bienesTransportados) { this.bienesTransportados = bienesTransportados; }
  public void setIdGps(String idGps) {this.idGps = idGps;};
  public void setLatitud(Double latitud) {this.latitud = latitud;};
  public void setLongitud(Double longitud) {this.longitud = longitud;};
}

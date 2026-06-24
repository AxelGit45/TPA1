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
}

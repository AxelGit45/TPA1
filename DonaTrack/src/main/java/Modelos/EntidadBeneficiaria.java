package Modelos;

import java.util.List;

public class EntidadBeneficiaria {

  private String razonSocial;
  private String direccionCompleta;
  private int telefono;
  private List<String> correosDeRepresentantes;
  private List <Necesidad> necesidadMateriales;

  public EntidadBeneficiaria(String razonSocial, String direccionCompleta, int telefono) {
    this.razonSocial = razonSocial;
    this.direccionCompleta = direccionCompleta;
    this.telefono = telefono;
  }

}

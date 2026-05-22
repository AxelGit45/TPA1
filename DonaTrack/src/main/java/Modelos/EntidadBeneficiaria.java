package Modelos;

import Errors.ErrorParametrosConstructor;

import java.util.ArrayList;
import java.util.List;

public class EntidadBeneficiaria {

  private String razonSocial;
  private String direccionCompleta;
  private int telefono;
  private List<String> correosDeRepresentantes;
  private List<Necesidad> necesidadMateriales;

  public EntidadBeneficiaria(String razonSocial, String direccionCompleta, int telefono) {
    validarConstructor(razonSocial, direccionCompleta, telefono);
    this.razonSocial = razonSocial;
    this.direccionCompleta = direccionCompleta;
    this.telefono = telefono;
    this.correosDeRepresentantes = new ArrayList<>();
    this.necesidadMateriales = new ArrayList<>();
  }

  private void validarConstructor(String razonSocial, String direccionCompleta, int telefono) {
    if(razonSocial == null || direccionCompleta == null || telefono <= 0){
      throw new ErrorParametrosConstructor("Faltan parámetros en el constructor.");
    }
  }
}

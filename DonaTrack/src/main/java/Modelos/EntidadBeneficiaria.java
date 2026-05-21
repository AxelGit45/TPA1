package Modelos;

import java.util.List;

public class EntidadBeneficiaria {

  private String razonSocial;
  private String direccionCompleta;
  private int telefono;
  private List<PersonaFisica> correosDeRepresentantes;
  private List <NecesidadMaterial> necesidadMateriales;

  public EntidadBeneficiaria(String razonSocial, String direccionCompleta, int telefono, List<PersonaFisica> correosDeRepresentantes, List<NecesidadMaterial> necesidadMateriales) {
    this.razonSocial = razonSocial;
    this.direccionCompleta = direccionCompleta;
    this.telefono = telefono;
    this.correosDeRepresentantes = correosDeRepresentantes;
    this.necesidadMateriales = necesidadMateriales;
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public void setRazonSocial(String razonSocial) {
    this.razonSocial = razonSocial;
  }

  public String getDireccionCompleta() {
    return direccionCompleta;
  }

  public void setDireccionCompleta(String direccionCompleta) {
    this.direccionCompleta = direccionCompleta;
  }

  public int getTelefono() {
    return telefono;
  }

  public void setTelefono(int telefono) {
    this.telefono = telefono;
  }

  public List<PersonaFisica> getCorreosDeRepresentantes() {
    return correosDeRepresentantes;
  }

  public void setCorreosDeRepresentantes(List<PersonaFisica> correosDeRepresentantes) {
    this.correosDeRepresentantes = correosDeRepresentantes;
  }

  public List<NecesidadMaterial> getNecesidadMateriales() {
    return necesidadMateriales;
  }

  public void setNecesidadMateriales(List<NecesidadMaterial> necesidadMateriales) {
    this.necesidadMateriales = necesidadMateriales;
  }
}

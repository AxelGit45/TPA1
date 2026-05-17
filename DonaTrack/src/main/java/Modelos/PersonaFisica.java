package Modelos;
import java.util.List;

public class PersonaFisica {

  private String nombre ;
  private String apellido;
  int edad;
  int nroDocumento;
  private String genero;
  private String direccion;
  private String email;
  private List<MedioDeContacto> medioDeContactos;

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public int getEdad() {
    return edad;
  }

  public void setEdad(int edad) {
    this.edad = edad;
  }

  public int getNroDocumento() {
    return nroDocumento;
  }

  public void setNroDocumento(int nroDocumento) {
    this.nroDocumento = nroDocumento;
  }

  public String getGenero() {
    return genero;
  }

  public void setGenero(String genero) {
    this.genero = genero;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<MedioDeContacto> getMedioDeContactos() {
    return medioDeContactos;
  }

  public void setMedioDeContactos(List<MedioDeContacto> medioDeContactos) {
    this.medioDeContactos = medioDeContactos;
  }
}

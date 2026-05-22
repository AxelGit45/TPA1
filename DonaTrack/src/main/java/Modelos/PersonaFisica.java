package Modelos;
import Enums.MedioDeContacto;
import Errors.ErrorParametrosConstructor;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PersonaFisica {

  private String nombre ;
  private String apellido;
  private int edad;
  private int nroDocumento;
  private String genero;
  private String direccion;
  private String email;
  private  UsuarioDonante user;
  private List<MedioDeContacto> medioDeContactos;

  public PersonaFisica(String nombre, String apellido, int edad, int nroDocumento, String genero, String direccion, String email, List<MedioDeContacto> medioDeContactos) {
    validarConstructor(nombre, apellido, edad, nroDocumento, genero, direccion, email);
    this.nombre = nombre;
    this.apellido = apellido;
    this.edad = edad;
    this.nroDocumento = nroDocumento;
    this.genero = genero;
    this.direccion = direccion;
    this.email = email;
    this.medioDeContactos = (medioDeContactos != null) ? medioDeContactos : new ArrayList<MedioDeContacto>();
  }

  private void validarConstructor(String nombre, String apellido, int edad, int nroDocumento, String genero, String direccion, String email){
    if (nombre == null || apellido == null || edad <= 0 || nroDocumento == 0 || genero == null || direccion == null || email == null) {
      throw new ErrorParametrosConstructor("Faltan parámetros en el constructor.");
    }
  }
}

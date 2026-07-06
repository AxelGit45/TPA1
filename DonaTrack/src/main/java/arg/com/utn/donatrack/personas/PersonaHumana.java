package arg.com.utn.donatrack.personas;

import arg.com.utn.donatrack.personas.contactos.Contacto;
import java.util.List;

public class PersonaHumana extends Persona {

  private String nombre;
  private String apellido;
  private Integer edad;
  private Integer numeroDeDocumento;
  private String genero;
  private String direccion;
  private Contacto predeterminado;

  public PersonaHumana(){}

  public PersonaHumana(String nombre, String apellido, Integer edad, Integer numeroDeDocumento, String genero,
                       String direccion, List<Contacto> contactos, Contacto predeterminado){
    this.nombre = nombre;
    this.apellido = apellido;
    this.edad = edad;
    this.numeroDeDocumento = numeroDeDocumento;
    this.genero = genero;
    this.direccion = direccion;
    this.contactos = contactos;
    this.predeterminado = predeterminado;
  }

  public String getNombre() { return nombre; }
  public String getApellido() { return apellido; }
  public Integer getEdad() { return edad; }
  public Integer getNumeroDeDocumento() { return numeroDeDocumento; }
  public String getGenero() { return genero; }
  public String getDireccion() { return direccion; }
  public Contacto getPredeterminado() { return predeterminado; }
}
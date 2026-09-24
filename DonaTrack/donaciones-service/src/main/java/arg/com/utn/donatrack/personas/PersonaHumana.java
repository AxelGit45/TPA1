package arg.com.utn.donatrack.personas;

import arg.com.utn.donatrack.personas.contactos.Contacto;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "PersonaHumana")
public class PersonaHumana extends Persona {

  private String nombre;
  private String apellido;
  private Integer edad;
  private Integer numeroDeDocumento;
  private String genero;
  private String direccion;
  @OneToOne
  @JoinColumn(name = "contacto_predeterminado_id")
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
    if (contactos != null) {
      this.contactos = contactos;
    }
    this.predeterminado = predeterminado;
  }

  public String getNombre() { return nombre; }
  public String getApellido() { return apellido; }
  public Integer getEdad() { return edad; }
  public Integer getNumeroDeDocumento() { return numeroDeDocumento; }
  public String getGenero() { return genero; }
  public String getDireccion() { return direccion; }
  public Contacto getPredeterminado() { return predeterminado; }

  public void setNombre(String nombre) {this.nombre = nombre;}
}
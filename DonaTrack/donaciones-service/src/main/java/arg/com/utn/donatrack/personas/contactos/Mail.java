package arg.com.utn.donatrack.personas.contactos;

import arg.com.utn.donatrack.notificaciones.adapters.EmailAdapter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MAIL")
public class Mail extends Contacto {

  private String direccion;

  public Mail(){
  }

  public Mail(String direccion){

    this.direccion = direccion;

  }

  @Override
  public void contactar(String mensaje){

    //EmailAdapter.getInstancia().enviarCorreo(this.direccion, "Notificación de DonaTrack", mensaje);

  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }
}

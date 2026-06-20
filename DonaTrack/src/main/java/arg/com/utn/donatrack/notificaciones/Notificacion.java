package arg.com.utn.donatrack.notificaciones;

import arg.com.utn.donatrack.personas.Persona;
import arg.com.utn.donatrack.personas.contactos.Contacto;

public class Notificacion {

  private Persona destinatario;
  private String mensaje;
  private Contacto medio;
  private Boolean completada;

  public Notificacion(Persona destinatario, String mensaje, Contacto medio){

    this.destinatario = destinatario;
    this.mensaje = mensaje;
    this.medio = medio;
    this.completada = false;

  }

  public void contactar(){

    this.destinatario.obtenerMedio(this.medio).contactar(this.mensaje);
    this.completada = true;

  }

}

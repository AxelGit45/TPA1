package donantes.contactos;

import arg.com.utn.donatrack.notificaciones.adapters.EmailAdapter;

public class Mail implements Contacto{

  private String direccion;

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

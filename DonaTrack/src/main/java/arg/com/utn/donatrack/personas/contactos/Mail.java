package arg.com.utn.donatrack.personas.contactos;

public class Mail extends Contacto{

  private String direccion;

  public Mail(String direccion){

    this.direccion = direccion;

  }

  @Override
  public void contactar(String mensaje){

    mailAPI.contactar(direccion, mensaje);

  }

}

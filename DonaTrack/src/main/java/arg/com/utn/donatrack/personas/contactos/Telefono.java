package arg.com.utn.donatrack.personas.contactos;

import arg.com.utn.donatrack.notificaciones.adapters.TwilioAdapter;

public class Telefono extends Contacto{

  private String numero;

  public Telefono(String numero){

    this.numero = numero;

  }

  @Override
  public void contactar(String mensaje){

    TwilioAdapter.getInstancia().enviarSms(this.numero, mensaje);

  }

}

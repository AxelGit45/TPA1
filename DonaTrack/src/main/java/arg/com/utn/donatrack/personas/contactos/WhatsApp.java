package arg.com.utn.donatrack.personas.contactos;

public class WhatsApp extends Contacto{

  private Integer numero;

  public WhatsApp(Integer numero){

    this.numero = numero;

  }

  @Override
  public void contactar(String mensaje){

    whatsAppAPI.contactar(numero, mensaje);

  }

}

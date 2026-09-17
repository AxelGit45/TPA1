package arg.com.utn.donatrack.personas.contactos;

import arg.com.utn.donatrack.notificaciones.adapters.TwilioAdapter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WHATSAPP")
public class WhatsApp extends Contacto {

  private String numero;

  public WhatsApp(){
  }

  public WhatsApp(String numero){

    this.numero = numero;

  }

  @Override
  public void contactar(String mensaje){

    TwilioAdapter.getInstancia().enviarWhatsApp(this.numero, mensaje);

  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }
}

package arg.com.utn.donatrack.notificaciones.adapters;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioAdapter {

  private static TwilioAdapter instancia = null;

  private static final String ACCOUNT_SID = "AC204d5d9b26b4a42c9682c3e8c987e32d";
  private static final String AUTH_TOKEN = "6a4013dbda721c209c5d2a68ccf1bd0a";
  private static final String TWILIO_PHONE_NUMBER = "+17543430258";
  private static final String TWILIO_WHATSAPP_NUMBER = "whatsapp:+14155238886";

  private TwilioAdapter() {

    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

  }

  public static TwilioAdapter getInstancia() {

    if (instancia == null) {
      instancia = new TwilioAdapter();
    }
    return instancia;

  }

  public void enviarSms(String numeroDestinatario, String mensaje) {

    Message message = Message.creator(
        new PhoneNumber(numeroDestinatario),
        new PhoneNumber(TWILIO_PHONE_NUMBER),
        mensaje
    ).create();

  }

  public void enviarWhatsApp(String numeroDestinatario, String mensaje) {

    Message message = Message.creator(
        new PhoneNumber("whatsapp:" + numeroDestinatario),
        new PhoneNumber(TWILIO_WHATSAPP_NUMBER),
        mensaje
    ).create();

  }

}

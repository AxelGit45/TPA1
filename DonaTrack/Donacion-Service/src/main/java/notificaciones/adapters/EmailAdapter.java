package notificaciones.adapters;

public class EmailAdapter {

 /* private static EmailAdapter instancia = null;

  private static final String CORREO_REMITENTE = "donatrackk3053@gmail.com";
  private static final String PASSWORD_APLICACION = "syvmylxaqvwnverj";

  private Session session;

  private EmailAdapter() {

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    this.session = Session.getInstance(props, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(CORREO_REMITENTE, PASSWORD_APLICACION);
      }
    });
  }

  public static EmailAdapter getInstancia() {

    if (instancia == null) {
      instancia = new EmailAdapter();
    }
    return instancia;

  }

  public void enviarCorreo(String correoDestinatario, String asunto, String cuerpoMensaje) {

    try {
      Message message = new MimeMessage(this.session);
      message.setFrom(new InternetAddress(CORREO_REMITENTE));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestinatario));

      message.setSubject(asunto);
      message.setText(cuerpoMensaje);

      Transport.send(message);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }

  } */
}

package Modelos;

import lombok.Getter;
import lombok.Setter;

public class Notificacion {
  private UsuarioDonante destinatario;
  private String mensaje;
  private MedioDeNotificacion medioDeNotificacion;
  @Setter
  @Getter
  private Boolean completada;
  public Notificacion(UsuarioDonante destinatario, String mensaje,MedioDeNotificacion medioDeNotificacion) {
    this.destinatario = destinatario;
    this.mensaje = mensaje;
    this.medioDeNotificacion = medioDeNotificacion;
    this.completada = false;
  }

  public void enviarMensaje(){
    //// enviarmensaje
    medioDeNotificacion.EnviarMensaje(destinatario, mensaje);
    //// supongo que fue  realizada con exito
    setCompletada(true);
  }

}

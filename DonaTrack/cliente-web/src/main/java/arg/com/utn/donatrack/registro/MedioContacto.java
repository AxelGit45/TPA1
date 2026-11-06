package arg.com.utn.donatrack.registro;

public enum MedioContacto {
  EMAIL("Correo electrónico"),
  TELEFONO("Teléfono"),
  WHATSAPP("WhatsApp");

  private final String etiqueta;

  MedioContacto(String etiqueta) { this.etiqueta = etiqueta; }

  public String getEtiqueta() { return etiqueta; }
}
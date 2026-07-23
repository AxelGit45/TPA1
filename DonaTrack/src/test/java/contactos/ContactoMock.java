package arg.com.utn.donatrack.personas.contactos;

import java.util.ArrayList;
import java.util.List;

public class ContactoMock implements Contacto {

  private List<String> mensajesRecibidos = new ArrayList<>();

  @Override
  public void contactar(String mensaje) {
    mensajesRecibidos.add(mensaje);
  }

  public List<String> getMensajesRecibidos() {
    return mensajesRecibidos;
  }
}

package arg.com.utn.donatrack.personas.contactos;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MOCK")
public class ContactoMock extends Contacto {

  private List<String> mensajesRecibidos = new ArrayList<>();

  @Override
  public void contactar(String mensaje) {
    mensajesRecibidos.add(mensaje);
  }

  public List<String> getMensajesRecibidos() {
    return mensajesRecibidos;
  }
}

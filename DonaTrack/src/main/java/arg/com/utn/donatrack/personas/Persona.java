package arg.com.utn.donatrack.personas;

import arg.com.utn.donatrack.personas.contactos.Contacto;
import java.util.List;

public abstract class Persona {

  protected List<Contacto> contactos;

  public void ingresarDonacion(){}

  public Contacto obtenerMedio(Contacto contactoAObtener){

    return this.contactos.stream()
        .filter(contacto -> contacto.equals(contactoAObtener))
        .findFirst()
        .orElse(null);

  }

}

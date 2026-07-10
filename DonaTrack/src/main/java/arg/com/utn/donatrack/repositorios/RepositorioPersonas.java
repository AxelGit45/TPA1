package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.personas.Persona;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPersonas {

  private static List<Persona> personas;

  public RepositorioPersonas (){

    personas = new ArrayList<>();

  }

  public static List<Persona> getPersonas() {return personas;}

}

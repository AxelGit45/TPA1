package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.personas.Persona;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPersonas {

  private static List<Persona> personas = new ArrayList<>();

  public RepositorioPersonas (){

  }

  public static List<Persona> getPersonas() {return personas;}

  public static void agregar(Persona persona) {personas.add(persona);}

}

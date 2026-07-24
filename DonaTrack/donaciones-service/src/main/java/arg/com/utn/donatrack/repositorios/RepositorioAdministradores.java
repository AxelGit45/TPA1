package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.personas.Administrador;
import arg.com.utn.donatrack.personas.Persona;
import java.util.ArrayList;
import java.util.List;

public class RepositorioAdministradores {

  private static List<Administrador> administradores = new ArrayList<>();

  public RepositorioAdministradores (){


  }

  public static List<Administrador> getAdministradores() {

    return administradores;

  }

  public void agregarAdministrador(Administrador administrador) {

    administradores.add(administrador);

  }
}

package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.personas.Persona;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEntidadesBeneficiarias {

  private static List<EntidadBeneficiaria> entidadesBeneficiarias;

  public RepositorioEntidadesBeneficiarias (){

    entidadesBeneficiarias = new ArrayList<>();

  }

  public static List<EntidadBeneficiaria> getEntidadesBeneficiarias() {return entidadesBeneficiarias;}
}

package Modelos;

import java.util.ArrayList;
import java.util.List;

public class Importacion {

  private List<UsuarioDonante> usuariosSistema;
  private List<PersonaFisica>  personaFisicas;
  private List<PersonaJuridica> personaJuridicas;

  public Importacion( ) {
    this.personaFisicas = new ArrayList<>();
    this.personaJuridicas = new ArrayList<>();
  }

}

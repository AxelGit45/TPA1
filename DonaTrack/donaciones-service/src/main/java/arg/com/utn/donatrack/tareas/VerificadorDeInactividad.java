package arg.com.utn.donatrack.tareas;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.personas.Persona;
import arg.com.utn.donatrack.personas.contactos.Contacto;
import arg.com.utn.donatrack.repositorios.RepositorioEntidadesBeneficiarias;
import arg.com.utn.donatrack.repositorios.RepositorioPersonas;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class VerificadorDeInactividad {

  public VerificadorDeInactividad() {}

  public void verificar () {

    LocalDate hoy = LocalDate.now();

    for (Persona persona : RepositorioPersonas.getPersonas()) {

      if (ChronoUnit.DAYS.between(persona.getUltimaConexion(), hoy) > 20) {

        for (Contacto contacto : persona.getContactos()){

          contacto.contactar("Hola! Hace mucho no te vemos en DonaTrack");

        }

      }

    }

    for (EntidadBeneficiaria entidadBeneficiaria : RepositorioEntidadesBeneficiarias.getEntidadesBeneficiarias()) {

      if (ChronoUnit.DAYS.between(entidadBeneficiaria.getUltimaConexion(), hoy) > 20) {

        for (Contacto contacto : entidadBeneficiaria.getContactos()){

          contacto.contactar("Hola! Hace mucho no te vemos en DonaTrack");

        }

      }

    }

  }

}

package arg.com.utn.donatrack.tareas;

import arg.com.utn.donatrack.personas.Persona;
import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.personas.PersonaJuridica;
import arg.com.utn.donatrack.personas.contactos.Contacto;
import arg.com.utn.donatrack.personas.contactos.Mail;
import arg.com.utn.donatrack.personas.contactos.Telefono;
import arg.com.utn.donatrack.repositorios.RepositorioPersonas;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ImportadorCsv {

  public static void main(String[] args) {

    String rutaArchivo = "donantes_prueba.csv";
    importarDesde(rutaArchivo);

  }

  public static void importarDesde(String ruta){

    String linea = "";
    String separador = ",";

    try (BufferedReader br = new BufferedReader(new FileReader(ruta))){

      br.readLine();

      while ((linea = br.readLine()) != null) {

        String[] datos = linea.split(separador);

        String tipoPersona = datos[0];
        String tipoDoc = datos[1];
        String documento = datos[2];
        String nombreORazonSocial = datos[3];
        String email = datos[4];
        String telefono = datos[5];

        Persona personaExistente = buscarPorEmail(email);

        if (personaExistente != null) {

          System.out.println("Actualizando a: " + email);

          if(personaExistente instanceof PersonaHumana) {

            ((PersonaHumana) personaExistente).setNombre(nombreORazonSocial);

          } else if (personaExistente instanceof PersonaJuridica) {

            ((PersonaJuridica) personaExistente).setRazonSocial(nombreORazonSocial);

          }

        } else {

          System.out.println("Creando nuevo usuario para: " + email);

          List<Contacto> nuevosContactos = new ArrayList<>();
          nuevosContactos.add(new Mail(email));
          nuevosContactos.add(new Telefono(telefono));

          if(tipoPersona.equalsIgnoreCase("HUMANA")) {

            PersonaHumana nuevaHumana = new PersonaHumana();
            nuevaHumana.setNombre(nombreORazonSocial);
            nuevaHumana.setContactos(nuevosContactos);
            RepositorioPersonas.getPersonas().add(nuevaHumana);

          } else if (tipoPersona.equalsIgnoreCase("JURIDICA")) {

            PersonaJuridica nuevaJuridica = new PersonaJuridica();
            nuevaJuridica.setRazonSocial(nombreORazonSocial);
            nuevaJuridica.setContactos(nuevosContactos);
            RepositorioPersonas.getPersonas().add(nuevaJuridica);

          }

        }

      }

      System.out.println("Importación finalizada con éxito!");

    } catch (Exception e) {

      System.out.println("Error al leer el archivo: " + e.getMessage());

    }

  }

  private static Persona buscarPorEmail(String emailBuscado) {

    for (Persona persona :RepositorioPersonas.getPersonas()) {

      if (persona.getContactos() != null) {

        for (Contacto contacto : persona.getContactos()) {

          if (contacto instanceof Mail) {

            Mail mail = (Mail) contacto;
            if (emailBuscado.equals(mail.getDireccion())) {

              return persona;

            }

          }

        }

      }

    }

    return null;

  }

}

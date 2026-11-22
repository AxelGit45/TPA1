package arg.com.utn.donatrack.tareas;
import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.personas.Persona;
import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.personas.PersonaJuridica;
import arg.com.utn.donatrack.personas.contactos.Contacto;
import arg.com.utn.donatrack.personas.contactos.Mail;
import arg.com.utn.donatrack.personas.contactos.Telefono;
import arg.com.utn.donatrack.seguridad.SeguridadPassword;
import arg.com.utn.donatrack.usuarios.Rol;
import arg.com.utn.donatrack.usuarios.Usuario;
import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class ImportadorCsv {

  public static int importar(InputStream inputStream) throws Exception {
    int procesados = 0;
    EntityManager em = Persistencia.crearEntityManager();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      em.getTransaction().begin();

      String passwordDefault = SeguridadPassword.hashear("donatrack123");

      String linea = br.readLine();
      while ((linea = br.readLine()) != null) {
        if (linea.isBlank()) continue;
        String[] datos = linea.split(",");
        if (datos.length < 6) continue;
        String tipoPersona = datos[0].trim();
        String tipoDoc = datos[1].trim();
        String documento = datos[2].trim();
        String nombreORazonSocial = datos[3].trim();
        String email = datos[4].trim();
        String telefono = datos[5].trim();

        List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
            .setParameter("email", email)
            .getResultList();
        Usuario usuario;
        if (usuarios.isEmpty()) {
          usuario = new Usuario(email, passwordDefault, Rol.DONANTE);
          em.persist(usuario);
        } else {
          usuario = usuarios.get(0);
        }

        List<Persona> personas = em.createQuery("SELECT p FROM Persona p WHERE p.usuarioId = :uid", Persona.class)
            .setParameter("uid", usuario.getId())
            .getResultList();
        if (!personas.isEmpty()) {
          Persona personaExistente = personas.get(0);
          if (personaExistente instanceof PersonaHumana) {
            ((PersonaHumana) personaExistente).setNombre(nombreORazonSocial);
          } else if (personaExistente instanceof PersonaJuridica) {
            ((PersonaJuridica) personaExistente).setRazonSocial(nombreORazonSocial);
          }
          em.merge(personaExistente);
        } else {
          if (tipoPersona.equalsIgnoreCase("HUMANA")) {
            PersonaHumana nuevaHumana = new PersonaHumana();
            nuevaHumana.setNombre(nombreORazonSocial);
            nuevaHumana.setUsuarioId(usuario.getId());
            nuevaHumana.agregarContacto(new Mail(email));
            if (!telefono.isBlank()) {
              nuevaHumana.agregarContacto(new Telefono(telefono));
            }
            em.persist(nuevaHumana);
          } else {
            PersonaJuridica nuevaJuridica = new PersonaJuridica();
            nuevaJuridica.setRazonSocial(nombreORazonSocial);
            nuevaJuridica.setUsuarioId(usuario.getId());
            nuevaJuridica.agregarContacto(new Mail(email));
            if (!telefono.isBlank()) {
              nuevaJuridica.agregarContacto(new Telefono(telefono));
            }
            em.persist(nuevaJuridica);
          }
        }
        procesados++;
      }
      em.getTransaction().commit();
      return procesados;
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
      throw e;
    } finally {
      em.close();
    }
  }
}
package arg.com.utn.donatrack.usuarios;

import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.personas.PersonaJuridica;
import arg.com.utn.donatrack.personas.TipoRazonSocial;
import arg.com.utn.donatrack.personas.contactos.Contacto;
import arg.com.utn.donatrack.personas.contactos.Mail;
import arg.com.utn.donatrack.personas.contactos.Telefono;
import arg.com.utn.donatrack.personas.contactos.WhatsApp;
import arg.com.utn.donatrack.repositorios.RepositorioUsuarios;

import javax.persistence.EntityManager;
import java.util.ArrayList;

public class ServicioDeUsuarios {

  private final RepositorioUsuarios repositorio = new RepositorioUsuarios();

  // No abre la transacción: la maneja quien lo llama, así el usuario y la persona se guardan juntos o no se guarda nada.
  public Usuario registrarDonante(EntityManager em, String nombre, String apellido, Integer edad,
                                  Integer numeroDeDocumento, String genero, String direccion,
                                  String email, String telefono, String whatsapp,
                                  String medioPredeterminado, String contrasena) {
    if (repositorio.existeEmail(em, email)) {
      throw new IllegalStateException("Ya existe una cuenta con ese email");
    }

    Usuario usuario = new Usuario(email, contrasena, Rol.DONANTE);
    repositorio.guardar(em, usuario);

    // El email es obligatorio, así que siempre hay al menos un contacto
    Mail mail = new Mail(usuario.getEmail());
    Telefono tel = tieneValor(telefono) ? new Telefono(telefono) : null;
    WhatsApp wa = tieneValor(whatsapp) ? new WhatsApp(whatsapp) : null;

    // Si el medio elegido no fue cargado, el predeterminado es el email
    Contacto predeterminado = mail;
    if ("TELEFONO".equals(medioPredeterminado) && tel != null) {
      predeterminado = tel;
    }
    if ("WHATSAPP".equals(medioPredeterminado) && wa != null) {
      predeterminado = wa;
    }

    PersonaHumana persona = new PersonaHumana(nombre, apellido, edad, numeroDeDocumento,
        genero, direccion, null, predeterminado);
    persona.agregarContacto(mail);
    if (tel != null) {
      persona.agregarContacto(tel);
    }
    if (wa != null) {
      persona.agregarContacto(wa);
    }
    persona.setUsuarioId(usuario.getId());
    em.persist(persona);

    return usuario;
  }

  // El email institucional es el usuario y el único medio de contacto por ahora.
  public Usuario registrarDonanteJuridica(EntityManager em, String razonSocial, String tipoRazonSocial,
                                          String rubro, String email, String contrasena) {
    if (repositorio.existeEmail(em, email)) {
      throw new IllegalStateException("Ya existe una cuenta con ese email");
    }

    Usuario usuario = new Usuario(email, contrasena, Rol.DONANTE);
    repositorio.guardar(em, usuario);

    PersonaJuridica persona = new PersonaJuridica(razonSocial, TipoRazonSocial.valueOf(tipoRazonSocial),
        rubro, new ArrayList<>());
    persona.agregarContacto(new Mail(usuario.getEmail()));
    persona.setUsuarioId(usuario.getId());
    em.persist(persona);

    return usuario;
  }

  // Devuelve el usuario si el email y la contraseña coinciden, o null si no.
  public Usuario autenticar(EntityManager em, String email, String contrasena) {
    Usuario usuario = repositorio.buscarPorEmail(em, email);
    if (usuario == null || !usuario.contrasenaCoincide(contrasena)) {
      return null;
    }
    return usuario;
  }

  private boolean tieneValor(String texto) {
    return texto != null && !texto.isBlank();
  }
}
package arg.com.utn.donatrack.usuarios;

import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.personas.contactos.Contacto;
import arg.com.utn.donatrack.personas.contactos.Mail;
import arg.com.utn.donatrack.personas.contactos.Telefono;
import arg.com.utn.donatrack.personas.contactos.WhatsApp;
import arg.com.utn.donatrack.repositorios.RepositorioUsuarios;

import javax.persistence.EntityManager;

public class ServicioDeUsuarios {

  private static final String FORMATO_EMAIL = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
  private static final int LARGO_MINIMO_CONTRASENA = 8;

  private final RepositorioUsuarios repositorio = new RepositorioUsuarios();

  // Crea la persona humana y su cuenta (con el rol de donante humano).
  // No abre la transacción: la maneja quien lo llama, así se guarda todo junto o no se guarda nada.
  // Lanza IllegalArgumentException si falta algún dato e IllegalStateException si el email ya tiene cuenta.
  public Usuario registrarPersonaHumana(EntityManager em, String nombre, String apellido, Integer edad,
                                        Integer numeroDeDocumento, String genero, String direccion,
                                        String email, String telefono, String whatsapp,
                                        String medioPredeterminado, String contrasena) {
    exigir(nombre, "El nombre es obligatorio");
    exigir(apellido, "El apellido es obligatorio");
    if (edad == null || edad <= 0) {
      throw new IllegalArgumentException("La edad es obligatoria");
    }
    if (numeroDeDocumento == null) {
      throw new IllegalArgumentException("El número de documento es obligatorio");
    }
    exigir(genero, "El género es obligatorio");
    exigir(direccion, "La dirección es obligatoria");
    exigir(email, "El email es obligatorio");
    if (!email.trim().matches(FORMATO_EMAIL)) {
      throw new IllegalArgumentException("El email no tiene un formato válido");
    }
    if (contrasena == null || contrasena.length() < LARGO_MINIMO_CONTRASENA) {
      throw new IllegalArgumentException(
          "La contraseña debe tener al menos " + LARGO_MINIMO_CONTRASENA + " caracteres");
    }

    // El constructor de Usuario normaliza el email (sin espacios y en minúscula)
    Usuario usuario = new Usuario(email, contrasena);
    if (repositorio.existeEmail(em, usuario.getEmail())) {
      throw new IllegalStateException("Ya existe una cuenta con ese email");
    }

    // El email es obligatorio, así que siempre hay al menos un contacto
    Mail mail = new Mail(usuario.getEmail());
    Telefono tel = tieneValor(telefono) ? new Telefono(telefono.trim()) : null;
    WhatsApp wa = tieneValor(whatsapp) ? new WhatsApp(whatsapp.trim()) : null;

    // Si el medio elegido no fue cargado, el predeterminado es el email
    Contacto predeterminado = mail;
    if ("TELEFONO".equals(medioPredeterminado) && tel != null) {
      predeterminado = tel;
    }
    if ("WHATSAPP".equals(medioPredeterminado) && wa != null) {
      predeterminado = wa;
    }

    PersonaHumana persona = new PersonaHumana(nombre.trim(), apellido.trim(), edad, numeroDeDocumento,
        genero.trim(), direccion.trim(), null, predeterminado);
    persona.agregarContacto(mail);
    if (tel != null) {
      persona.agregarContacto(tel);
    }
    if (wa != null) {
      persona.agregarContacto(wa);
    }
    em.persist(persona);

    // La cuenta guarda el id de la persona: ese es su rol de donante humano
    usuario.asignarPersonaHumana(persona.getId());
    repositorio.guardar(em, usuario);

    return usuario;
  }

  private void exigir(String valor, String mensaje) {
    if (!tieneValor(valor)) {
      throw new IllegalArgumentException(mensaje);
    }
  }

  private boolean tieneValor(String texto) {
    return texto != null && !texto.isBlank();
  }
}
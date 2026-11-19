package arg.com.utn.donatrack.seguridad;

import org.mindrot.jbcrypt.BCrypt;

public class SeguridadPassword {

  // Genera el hash seguro con salt automático (para usar en el Registro)
  public static String hashear(String passwordPlana) {
    return BCrypt.hashpw(passwordPlana, BCrypt.gensalt());
  }

  // Compara la clave ingresada con el hash guardado en la base (para usar en el Login)
  public static boolean verificar(String passwordPlana, String passwordHasheada) {
    if (passwordPlana == null || passwordHasheada == null) {
      return false;
    }
    return BCrypt.checkpw(passwordPlana, passwordHasheada);
  }
}

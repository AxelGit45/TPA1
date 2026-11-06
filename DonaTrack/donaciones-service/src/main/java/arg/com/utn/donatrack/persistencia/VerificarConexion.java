package arg.com.utn.donatrack.persistencia;

import javax.persistence.EntityManager;

public class VerificarConexion {

  public static void main(String[] args) {
    EntityManager em = Persistencia.crearEntityManager();
    Object resultado = em.createNativeQuery("select 1").getSingleResult();
    System.out.println("[POSTGRES] Conexión OK, select 1 = " + resultado);
    em.close();
    Persistencia.factory().close();
  }
}
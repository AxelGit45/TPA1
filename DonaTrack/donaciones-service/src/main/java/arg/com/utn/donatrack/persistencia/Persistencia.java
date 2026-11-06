package arg.com.utn.donatrack.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Persistencia {

  private static EntityManagerFactory factory;

  public static synchronized EntityManagerFactory factory() {
    if (factory == null) {
      factory = Persistence.createEntityManagerFactory("postgres-persistence-unit");
    }
    return factory;
  }

  public static EntityManager crearEntityManager() {
    return factory().createEntityManager();
  }
}
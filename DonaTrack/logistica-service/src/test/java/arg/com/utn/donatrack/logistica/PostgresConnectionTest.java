package arg.com.utn.donatrack.logistica;

import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PostgresConnectionTest {

  @Test
  void testConexionAPostgres() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres-persistence-unit");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    try {
      tx.begin();
      Camion camion = new Camion("XX999YY", 20, 2, 1000);
      em.persist(camion);
      tx.commit();

      assertNotNull(camion.getId());
      System.out.println("Conectado a Postgres. Camion persistido con id: " + camion.getId());
    } finally {
      em.close();
      emf.close();
    }
  }
}

// levantar docker docker compose up -d
//comprobar que este corriendo docker ps
// comando por terminal para ver: docker exec -it donatrack-postgres-logistica psql -U donatrack -d logistica_db -c "SELECT * FROM Camion;"
// comando por terminal para eliminar: docker exec -it donatrack-postgres-logistica psql -U donatrack -d logistica_db -c "DELETE FROM Camion;"
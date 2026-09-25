package arg.com.utn.donatrack.entidadesBeneficiarias;


import arg.com.utn.donatrack.donaciones.Categoria;
import arg.com.utn.donatrack.donaciones.Subcategoria;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;

/*public class NecesidadPersistenciaPostgresTest {

  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres-persistence-unit");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    try {
      tx.begin();

      Categoria categoria = new Categoria("Alimentos");
      Subcategoria subcategoria = new Subcategoria("Leche en polvo", categoria, false, false);

      NecesidadExtraordinaria extraordinaria = new NecesidadExtraordinaria(
          null,
          subcategoria,
          50,
          "Necesidad urgente por inundación",
          false,
          0
      );

      NecesidadRecurrente recurrente = new NecesidadRecurrente(
          null,
          subcategoria,
          100,
          "Reposición mensual de stock",
          false,
          0,
          LocalDate.now().plusMonths(1)
      );

      em.persist(extraordinaria);
      em.persist(recurrente);

      tx.commit();

      System.out.println("NecesidadExtraordinaria persistida con id: " + extraordinaria.getId());
      System.out.println("NecesidadRecurrente persistida con id: " + recurrente.getId());

    } catch (Exception e) {
      if (tx.isActive()) {
        tx.rollback();
      }
      e.printStackTrace();
    } finally {
      em.close();
      emf.close();
    }
  }

}
*/
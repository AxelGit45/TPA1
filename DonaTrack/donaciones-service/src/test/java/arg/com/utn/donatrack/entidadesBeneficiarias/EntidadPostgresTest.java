package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.donaciones.Categoria;
import arg.com.utn.donatrack.donaciones.Subcategoria;
import arg.com.utn.donatrack.personas.contactos.Contacto;
import arg.com.utn.donatrack.personas.contactos.Mail;
import arg.com.utn.donatrack.personas.contactos.Telefono;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/*
public class EntidadPostgresTest {

  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres-persistence-unit");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    try {
      tx.begin();

      Categoria categoria = new Categoria("Alimentos");
      Subcategoria subcategoria = new Subcategoria("Leche en polvo", categoria, false, false);

      NecesidadExtraordinaria extraordinaria = new NecesidadExtraordinaria(
          null, subcategoria, 50, "Necesidad urgente por inundación", false, 0
      );

      NecesidadRecurrente recurrente = new NecesidadRecurrente(
          null, subcategoria, 100, "Reposición mensual de stock", false, 0,
          LocalDate.now().plusMonths(1)
      );

      List<Necesidad> necesidades = new ArrayList<>();
      necesidades.add(extraordinaria);
      necesidades.add(recurrente);

      Contacto mail = new Mail("comedor.sanjose@example.com");
      Contacto telefono = new Telefono("+54 11 1234 5678");

      List<Contacto> contactos = new ArrayList<>();
      contactos.add(mail);
      contactos.add(telefono);

      EntidadBeneficiaria entidad = new EntidadBeneficiaria(
          "Comedor San José",
          "Calle Falsa 123",
          contactos,
          necesidades
      );

      em.persist(entidad); // cascade ALL persiste necesidades y contactos

      tx.commit();

      System.out.println("EntidadBeneficiaria persistida con id: " + entidad.getId());
      entidad.getNecesidades().forEach(n ->
          System.out.println("  Necesidad persistida con id: " + n.getId())
      );
      entidad.getContactos().forEach(c ->
          System.out.println("  Contacto persistido con id: " + c.getId())
      );

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

}*/

package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.donaciones.Bien;
import arg.com.utn.donatrack.donaciones.Categoria;
import arg.com.utn.donatrack.donaciones.EstadoUso;
import arg.com.utn.donatrack.donaciones.Subcategoria;
import arg.com.utn.donatrack.donaciones.Unidad;
import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.personas.contactos.Contacto;
import arg.com.utn.donatrack.personas.contactos.Telefono;
import arg.com.utn.donatrack.personas.contactos.WhatsApp;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class EntidadBeneficiariaPersistenceTest implements SimplePersistenceTest {
  @Test
  void crearYPersistirUnaEntidadBeneficiaria() {

    

    Telefono telefono = new Telefono("47801234");
    WhatsApp whatsApp = new WhatsApp("5491112345678");

    List<Contacto> contactos = new ArrayList<>();
    contactos.add(telefono);
    contactos.add(whatsApp);

    Subcategoria subcategoria = new Subcategoria("Remera", new Categoria ("Ropa"), false, true);
    Subcategoria subcategoria1 = new Subcategoria("Pantalon", new Categoria ("Ropa"), false, true);

    NecesidadExtraordinaria necesidadExtraordinariaRemera = new NecesidadExtraordinaria(
        null,
        subcategoria,
        20,
        "Remeras de algodón para adultos",
        false,
        0
    );

    NecesidadExtraordinaria necesidadExtraordinariaPantalon = new NecesidadExtraordinaria(
        null,
        subcategoria1,
        20,
        "Pantalones para niños",
        false,
        0
    );


    List<Necesidad> necesidades = new ArrayList<>();
    necesidades.add(necesidadExtraordinariaRemera);
    necesidades.add(necesidadExtraordinariaPantalon);


    EntidadBeneficiaria entidadBeneficiaria = new EntidadBeneficiaria(
        "Fundación Manos Solidarias",
        "Paso 1810",
        contactos,
        necesidades
    );

    withTransaction(() -> {
      entityManager().persist(entidadBeneficiaria);
      entityManager().persist(necesidadExtraordinariaRemera);
      entityManager().persist(necesidadExtraordinariaPantalon);
    });

    assertNotNull(entidadBeneficiaria.getId());

    EntidadBeneficiaria entidadRecuperada = entityManager().find(EntidadBeneficiaria.class, entidadBeneficiaria.getId());
    assertNotNull(entidadRecuperada);
    assertEquals("Fundación Manos Solidarias", entidadRecuperada.getNombre());
    assertEquals("Paso 1810", entidadRecuperada.getDireccion());

    assertNotNull(entidadRecuperada.getNecesidades());
    assertEquals(2, entidadRecuperada.getNecesidades().size());

    assertNotNull(entidadRecuperada.getContactos());
    assertEquals(2, entidadRecuperada.getContactos().size());


  }

}

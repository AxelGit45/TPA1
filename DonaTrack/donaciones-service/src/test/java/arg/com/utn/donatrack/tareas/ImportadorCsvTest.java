package arg.com.utn.donatrack.tareas;

import arg.com.utn.donatrack.repositorios.RepositorioPersonas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImportadorCsvTest {

  @BeforeEach
  public void init() {
    RepositorioPersonas.getPersonas().clear();
  }

  @Test
  public void importarDesde_DebeLeerTuArchivoGiganteYGuardarPersonas() {

    String rutaArchivo = "donantes_prueba.csv";
    if (!new File(rutaArchivo).exists()) {
      rutaArchivo = "DonaTrack/donantes_prueba.csv";
    }


    ImportadorCsv.importarDesde(rutaArchivo);

    int cantidadImportada = RepositorioPersonas.getPersonas().size();

    assertTrue(cantidadImportada > 0, "El repositorio quedó vacío, algo falló al leer el CSV");

    System.out.println("Se importaron " + cantidadImportada + " personas.");
  }
}

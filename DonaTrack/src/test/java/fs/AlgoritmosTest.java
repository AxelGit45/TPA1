package fs;
import arg.com.utn.donatrack.donaciones.Bien;
import arg.com.utn.donatrack.donaciones.Categoria;
import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.donaciones.EstadoUso;
import arg.com.utn.donatrack.donaciones.Subcategoria;
import arg.com.utn.donatrack.donaciones.Unidad;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.personas.contactos.Contacto;
import arg.com.utn.donatrack.personas.contactos.Telefono;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
//import java.util.Collection;
import java.util.Date;
import java.util.List;

public class AlgoritmosTest {

  /** Persona donante **/
  private Telefono telefono = new Telefono("1143789856");
  public List<Contacto> telefonos = new ArrayList<>();

  private PersonaHumana persona= new PersonaHumana("Ana Maria","Martinez",28,38345786,"Femenino","Avenida Siempre Viva", telefonos, telefono);

  /** Paquete de Arroz **/
  private Date fecha1 = new Date(2026,9,4);
  private Categoria categoria = new Categoria("Alimentos");
  private Subcategoria subcategoria = new Subcategoria("Arroz Blanco",categoria,true, false);
  private Bien Arroz = new Bien("Arroz para donar","URL:Foto",subcategoria,1, Unidad.KILOGRAMO,fecha1, EstadoUso.NUEVO,true, persona);

  /** Aceite girasol **/
  private Categoria categoria2 = new Categoria("Alimentos");
  private Date fecha2 = new Date(2026, 10, 8);
  private Subcategoria subcategoria2 = new Subcategoria("Aceite de Girasol",categoria2,true,false);
  private Bien Aceite = new Bien("Aceite","URL:Foto",subcategoria2,1,Unidad.LITRO,fecha2,EstadoUso.NUEVO,true,persona);

  /** Agua **/
  private Categoria categoria3 = new Categoria("Bebidas");
  private Date fecha3 = new Date(2026, 12, 10);
  private Subcategoria subcategoria3 = new Subcategoria("Agua Mineral",categoria3,true,false);
  private Bien Agua = new Bien("Agua mineral botella de 1 litro","URL:Foto", subcategoria3,1, Unidad.LITRO,fecha3,EstadoUso.NUEVO,true,persona);

  /** Papel Higienico **/
  private Categoria categoria4 = new Categoria("Higiene Personal");
  private Date fecha4 = new Date(2026, 12, 10);
  private Subcategoria subcategoria4 = new Subcategoria("Papel Higienico pack 6",categoria4,false,false);
  private Bien PapelHigienico = new Bien("Pack de 6 unidades","URL:Foto", subcategoria4,1, Unidad.UNIDAD,fecha4,EstadoUso.NUEVO,false,persona);

  /** Harina **/
  private Categoria categoria5 = new Categoria("Alimentos");
  private Date fecha5 = new Date(2026, 12, 29);
  private Subcategoria subcategoria5 = new Subcategoria("Harina 0000",categoria5,true,false);
  private Bien Harina = new Bien("Harina 1k","URL:Foto", subcategoria5,1, Unidad.KILOGRAMO,fecha5,EstadoUso.NUEVO,true,persona);

  List<Bien> bienes = new ArrayList<>();

  /** Cantidades: Agua 1, Aceite 2, Papel Higienico pack6 2, Harina 3, Arroz 3**/
  @BeforeEach
  void creacionDeBienes(){

    bienes.add(Agua);
    bienes.add(Aceite);
    bienes.add(Aceite);
    bienes.add(PapelHigienico);
    bienes.add(PapelHigienico);
    bienes.add(Harina);
    bienes.add(Harina);
    bienes.add(Harina);
    bienes.add(Arroz);
    bienes.add(Arroz);
    bienes.add(Arroz);

  }

  @BeforeEach
  void creacionDeEntidades(){

    //EntidadBeneficiaria entidad1 = new EntidadBeneficiaria("PEPAS SA", "Calle Avalos 742",);
  }

  @Test
  void laDonacionContiene11Bienes(){

    Donacion donacion = new Donacion(bienes);
    Assertions.assertEquals(11, donacion.getBienes().size());
  }

  @Test
  void obtengoUnaListaDelTipoMatchEntidadesDeDiezElementos(){
      Donacion donacion = new Donacion(bienes);
      //donacion.realizarProcesoDeMtachmaking();
  }




}

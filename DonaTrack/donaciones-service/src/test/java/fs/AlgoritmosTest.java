package fs;
import arg.com.utn.donatrack.donaciones.Bien;
import arg.com.utn.donatrack.donaciones.Categoria;
import arg.com.utn.donatrack.donaciones.CompatibilidadSemantica;
import arg.com.utn.donatrack.donaciones.ComponenteAsignadorDeDestinatarios;
import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.donaciones.EstadoUso;
import arg.com.utn.donatrack.donaciones.PrioridadASubAtendidos;
import arg.com.utn.donatrack.donaciones.ResultadoAlgoritmo;
import arg.com.utn.donatrack.donaciones.Subcategoria;
import arg.com.utn.donatrack.donaciones.Unidad;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadSinNecesidades;
import arg.com.utn.donatrack.entidadesBeneficiarias.Necesidad;
import arg.com.utn.donatrack.entidadesBeneficiarias.NecesidadExtraordinaria;
import arg.com.utn.donatrack.personas.PersonaHumana;
import arg.com.utn.donatrack.personas.contactos.Contacto;
import arg.com.utn.donatrack.personas.contactos.Telefono;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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

  /**Lista de Entidades beneficiarias**/

    List<EntidadBeneficiaria> entidades = new ArrayList<>();

    Telefono Telefono = new Telefono("1143789856");
    List<Contacto> telefonos2 = new ArrayList<>();
    //telefonos.add(telefono);
    NecesidadExtraordinaria arroz = new NecesidadExtraordinaria(100L,subcategoria,3,"Arroz para familias afectadas",false,0);
    List<Necesidad> necesidades = new ArrayList<>();


    EntidadBeneficiaria entidad1 = new EntidadBeneficiaria("SONRISITAS", "Calle Avalos 742",telefonos,necesidades);

    List<Necesidad> necesidades2 = new ArrayList<>();
    EntidadBeneficiaria entidad2 = new EntidadBeneficiaria("Sociedad de fomento Aguilar", "Pujato 1630", telefonos, necesidades2);


  /** Cantidades: Agua 1, Aceite 2, Papel Higienico pack6 2, Harina 3, Arroz 3**/
  @Test
  void laDonacionContiene11Bienes(){
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

    Donacion donacion = new Donacion(bienes);
    Assertions.assertEquals(11, donacion.getBienes().size());
  }

  /**Test que verifica que se lanza una excepcion mientras se ejecuta el algoritmo de
   * compatibilidad semantica solo si una entidad no contiene necesidades **/
  @Test
  void sinCoincidenciasEnCompatibilidadSemantica(){

    CompatibilidadSemantica algoritmo1 = new CompatibilidadSemantica();
    Donacion donacion = new Donacion(bienes);
    entidades.add(entidad1);

    Assertions.assertThrows(EntidadSinNecesidades.class, ()-> entidad1.cuantoNecesita(donacion));
    Assertions.assertThrows(EntidadSinNecesidades.class, ()-> algoritmo1.ejecutar(donacion, entidades));

    donacion.getEstado().setAlgoritmo(algoritmo1);  //solo para llevar a cabo el test
    Assertions.assertThrows(EntidadSinNecesidades.class, ()-> donacion.getEstado().asignacionDonaciones(entidades,donacion));
    Assertions.assertThrows(EntidadSinNecesidades.class,()-> donacion.realizarProcesoDeMtachmaking(entidades));

  }

  /**Test sobre algoritmo de Compatibilidad Semantica**/
  @Test
  void elAlgoritmoCompatibilidadSemanticaDevuelveUnRankingDe2Entidades(){
    CompatibilidadSemantica algoritmo1 = new CompatibilidadSemantica();

    entidades.add(entidad1);
    entidades.add(entidad2);

    Donacion donacion = new Donacion(bienes);

    necesidades2.add(arroz);
    necesidades.add(arroz);

    ResultadoAlgoritmo resultado =  algoritmo1.ejecutar(donacion,entidades);

    Assertions.assertEquals(2,resultado.getResultadosAlgoritmo().size());

  }

  /** Test que prueba el puntaje que obtiene la primera entidad del raking devuelto por
   * el algoritmo de compatibilidad semantica **/
  @Test
  void elPuntajeDeLaEntidadEnPrimerPuestoDelRankingEs3(){

    CompatibilidadSemantica algoritmo1 = new CompatibilidadSemantica();

    /*La entidad (solo para el test) solo tiene una necesidad*/
    necesidades.add(arroz);

    entidades.add(entidad1);

    /* La donacion contiene 3 bienes */
    Donacion donacion = new Donacion(bienes);
    bienes.add(Aceite);
    bienes.add(Agua);
    bienes.add(Arroz);

    entidades.add(entidad2);

    /*necesidades de la segunda entidad*/
    necesidades2.add(arroz);
    necesidades2.add(arroz);
    necesidades2.add(arroz);

    /* El algoritmo (para el test) se ejecuta con una entidad*/
    ResultadoAlgoritmo ranking = algoritmo1.ejecutar(donacion,entidades);

    Assertions.assertEquals(3, ranking.getResultadosAlgoritmo().get(0).getPuntaje());

  }

  /**Test que verifica que el puntaje de la entidad en el primer
   * puesto del ranking obtenido del algoritmo Prioridad a sub atendidos
   * es 5 **/
  @Test
  void elPuntajeDeLaPrimeraEntidadDelRankingEs5(){

    PrioridadASubAtendidos algoritmo1 = new PrioridadASubAtendidos();

    necesidades.add(arroz);

    entidades.add(entidad1);
    entidad1.setDonacionesRecibidasUltimoTrimestre(10);

    /* La donacion contiene 3 bienes */
    Donacion donacion = new Donacion(bienes);
    bienes.add(PapelHigienico);
    bienes.add(Harina);
    bienes.add(Arroz);

    entidades.add(entidad2);
    entidad2.setDonacionesRecibidasUltimoTrimestre(5);
    /*necesidades de la segunda entidad*/
    necesidades2.add(arroz);
    necesidades2.add(arroz);
    necesidades2.add(arroz);

    /* El algoritmo (para el test) se ejecuta con una entidad*/
    ResultadoAlgoritmo ranking = algoritmo1.ejecutar(donacion,entidades);

    Assertions.assertEquals(5,ranking.getResultadosAlgoritmo().get(0).getPuntaje());

  }

  @Test
  void elComponenteAsignadorDeDonacionesRecive1RankingDeUnaDonacion(){

    CompatibilidadSemantica algoritmo1 = new CompatibilidadSemantica();
    PrioridadASubAtendidos algoritmo2 = new PrioridadASubAtendidos();

    ComponenteAsignadorDeDestinatarios componenteExterno = new ComponenteAsignadorDeDestinatarios();
    Donacion donacion = new Donacion(bienes);

    bienes.add(Harina);
    bienes.add(PapelHigienico);
    bienes.add(Agua);
    bienes.add(Arroz);

    donacion.getEstado().setAlgoritmo(algoritmo1);
    donacion.getEstado().setAlgoritmo(algoritmo2);
    donacion.getEstado().setComponenteExterno(componenteExterno);

    entidades.add(entidad1);
    entidades.add(entidad2);

    /*nececidades de la primera entidad*/
    necesidades.add(arroz);

    /*nececidades de la segunda entidad*/
    necesidades2.add(arroz);
    necesidades2.add(arroz);
    necesidades2.add(arroz);

    donacion.getEstado().asignacionDonaciones(entidades,donacion);

    Assertions.assertEquals(1, componenteExterno.getResultadosAsignacionDeDonaciones().size() );

  }




}

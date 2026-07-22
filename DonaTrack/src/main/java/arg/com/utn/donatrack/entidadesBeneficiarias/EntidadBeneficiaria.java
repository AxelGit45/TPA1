package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.donaciones.Estados;
import arg.com.utn.donatrack.estados.EntregaFallida;
import arg.com.utn.donatrack.estados.Entregada;
import arg.com.utn.donatrack.logistica.Camion;
import arg.com.utn.donatrack.logistica.Entrega;
import arg.com.utn.donatrack.logistica.EstadoEntrega;
import arg.com.utn.donatrack.personas.contactos.Contacto;
import arg.com.utn.donatrack.personas.contactos.Mail;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class EntidadBeneficiaria {

  private Long id;
  private String razonSocial;
  private String direccion;
  private List<Necesidad> necesidades;
  protected List<Contacto> contactos;
  private LocalDate ultimaConexion;
  // private List<Camion> historialDeCamiones;
  private List<Entrega> peticionesEntregadas;
  private int donacionesRecibidasUltimoTrimestre; //POR AHORA ES ASI
  //SERIA MEJOR CREAR UN OBJETO QUE CUENTE LAS DONACIONES Y
  //AL OBJETO PREGUNTARLE LAS DONACIONES DEL ULTIMO TRIMESTRE, OTRA CLASE.
  //INDEPENDIENTEMENTE DE CON QUE METODO OBTENGA ESA CANTIDAD LA ESTRUCTURA DEL ALGORITMO ES LA MISMA
  //SE DELEGA TODO EN EL METODO DE LA ENTIDAD

  public EntidadBeneficiaria(){}

  public EntidadBeneficiaria(String razonSocial, String direccion,List<Contacto> contactos, List<Necesidad> necesidades){

    this.razonSocial = razonSocial;
    this.direccion = direccion;
    this.contactos = contactos;
    this.necesidades = necesidades;

  }


  public List<Necesidad> getNecesidades(){
    return necesidades;
  }

  public int getDonacionesRecibidasUltimoTrimestre(){return donacionesRecibidasUltimoTrimestre;}


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public String getDireccion() {
    return direccion;
  }

  public int cuantoNecesita(Donacion donacion){

    int puntaje = necesidades.stream().filter(necesidad -> donacion.contieneBienPara(necesidad)).toList().size();

    return puntaje;
  }

 /* public void confirmarRecepcionDeEntrega(Entrega entrega, Camion camionEntregador){
    entrega.cambiarEstado(EstadoEntrega.ENTREGADA);
    // historialDeCamiones.add(camionEntregador); // REVISAR !!!!!!!
    entrega.registrarCamion(camionEntregador);
  } */

  public void cargarFotosDeEntrega(List<String> urlFotos) {
    // TODO
  }

 /* public void informarNoRecepcion(Entrega entrega) {
    if(this.entregaTardia(entrega)) {
      entrega.cambiarEstado(EstadoEntrega.NORECIBIDA);
      entrega.getDonaciones().forEach(donacion -> donacion.cambiarEstado(Estados.ENTREGAFALLIDA));
    } // Si no se cumple debería lanzar un error porque la fecha de hoy
    // aún no es posterior a la fecha de entrega esperada.
    // "EL CASO SERÁ REVISADO POR LAS PERSONAS ADMINISTRADORAS"
  } */

  public boolean entregaTardia(Entrega entrega) {
    LocalDate fechaDeHoy = LocalDate.now();
    return entrega.getFechaDeEntregaEsperada().isAfter(fechaDeHoy);
  }

  public List<Contacto> getContactos() {
    return contactos;
  }

  public LocalDate getUltimaConexion(){ return this.ultimaConexion;}
}
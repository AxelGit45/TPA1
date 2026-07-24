package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.entidadesBeneficiarias.Necesidad;
import arg.com.utn.donatrack.estados.EnDeposito;
import arg.com.utn.donatrack.estados.EstadoDonacion;
import arg.com.utn.donatrack.personas.Persona;
import java.util.ArrayList;
import java.util.List;

public class Donacion {

  private Long id;
  private List<Bien> bienes;
  private EstadoDonacion estadoDonacion;
  private List<EstadoDonacion> historialDeCambiosDeEstado;
  private List<Algoritmo> algoritmos;
  private List<Persona> donadores;

  public Donacion(Bien bien /*List<Bien> bienes*/){
    this.bienes = bienes;
    this.historialDeCambiosDeEstado = new ArrayList<>();

    EstadoDonacion estadoInicial = new EnDeposito();

    this.cambiarEstado(estadoInicial);
  }



  public Donacion(List<Bien> bienes){
    this.bienes = bienes;
    this.historialDeCambiosDeEstado = new ArrayList<>();
    this.donadores = new ArrayList<>();

    EstadoDonacion estadoInicial = new EnDeposito();

    this.cambiarEstado(estadoInicial);

    for (Bien bien : bienes) {

      Persona donador = bien.getDonador();

      if(!donadores.contains(donador)) {

        donadores.add(bien.getDonador());

      }

    }

  }

  public void cambiarEstado(EstadoDonacion nuevoEstado){

    this.estadoDonacion = nuevoEstado;
    this.historialDeCambiosDeEstado.add(nuevoEstado);

  }

  /*
  method cambiarEstadoAFallida(String justificacion){
    cambiarEstado(enum Fallida),
    this.justificacion = justificacion
  }
  */

  public List<Bien> getBienes(){
    return bienes;
  }

  public boolean contieneBienPara(Necesidad necesidad){ //una necesidad representa un Bien
    return bienes.stream().anyMatch(necesidad::esSatisfechaPor);
  }

  public void realizarProcesoDeMtachmaking(List<EntidadBeneficiaria> entidades){
    this.estadoDonacion.matchmaking(entidades, this);
  }

  /* //METODO PARA QUE UNA DONACION REALICE EL PROCESO DE MATCHMAKING
   public void matchmakin(List<EntidadBeneficiaria> entidades){  //BIEN
     if (estadoDonacion == Estados.ENDEPOSITO){
       //ACA SE CREA EL RESULTADO DEL MATCHMAKING
       //new ResultadoMatchmaking(this, resultadosDeAlgoritmos);
       List<ResultadoAlgoritmo> resultadosDeAlgoritmos = algoritmos.stream().map(algoritmo -> algoritmo.ejecutar(this,entidades)).toList();

       ResultadoMatchmaking ranking = new ResultadoMatchmaking(this, resultadosDeAlgoritmos);
     }else {
       //MANEJO DE ERROR
       //RUNTIMEEXCEPTION
     }

     //lista de algoritmos
     //ejecuto cada algoritmo y guardo su resultado en la lista (transformacion)
     //aca uso map
     //return lista de listas (de EntidadBeneficiaria);
   }
 */
  public EstadoDonacion getEstado() {
    return estadoDonacion;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<EstadoDonacion> getHistorialDeCambiosDeEstado() {
    return historialDeCambiosDeEstado;
  }

  public List<Persona> getDonadores() {
    return donadores;
  }

}
package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.entidadesBeneficiarias.Necesidad;
import arg.com.utn.donatrack.estados.EnDeposito;
import arg.com.utn.donatrack.estados.EnTraslado;
import arg.com.utn.donatrack.estados.EstadoDonacion;
import arg.com.utn.donatrack.personas.Persona;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "donacion")
public class Donacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "donacion_id")
  private List<Bien> bienes = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "estado_actual_id")
  private EstadoDonacion estadoDonacion;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "donacion_id")
  @OrderColumn(name = "indice_historial")
  private List<EstadoDonacion> historialDeCambiosDeEstado = new ArrayList<>();

  @Transient
  private List<Algoritmo> algoritmos;

  @Transient
  private List<Persona> donadores;

  public Donacion(){
      this.historialDeCambiosDeEstado = new ArrayList<>();

  };

  public Donacion(Bien bien /*List<Bien> bienes*/){
    //this.bienes = bienes;
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

  public void iniciarTraslado(Long camionId) {
    this.cambiarEstado(new EnTraslado(camionId));
  }

  public List<Bien> getBienes(){
    return bienes;
  }

  public boolean contieneBienPara(Necesidad necesidad){ //una necesidad representa un Bien
    return bienes.stream().anyMatch(necesidad::esSatisfechaPor);
  }

  public void realizarProcesoDeMtachmaking(List<EntidadBeneficiaria> entidades){
    this.estadoDonacion.asignacionDonaciones(entidades, this);
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

  public void setDonadores(List<Persona> donadores) { this.donadores = donadores; }

}
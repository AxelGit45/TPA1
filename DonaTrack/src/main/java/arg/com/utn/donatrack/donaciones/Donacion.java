package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.entidadesBeneficiarias.Necesidad;
import java.util.ArrayList;
import java.util.List;

public class Donacion {


  private List<Bien> bienes;
  private Estados estadoDonacion;
  private List<Estados> historialDeCambiosDeEstado;
  private List<Algoritmo> algoritmos;

  public Donacion(Bien bien /*List<Bien> bienes*/){
    //this.bienes = bienes;
    this.historialDeCambiosDeEstado = new ArrayList<>();

    Estados estadoInicial = Estados.ENDEPOSITO;

    this.cambiarEstado(estadoInicial);
  }

  public void cambiarEstado(Estados nuevoEstado){

    this.estadoDonacion = nuevoEstado;
    this.historialDeCambiosDeEstado.add(nuevoEstado);

  }

  public List<Bien> getBienes(){
    return bienes;
  }

  public boolean contieneBienPara(Necesidad necesidad){ //una necesidad representa un Bien
    return bienes.stream().anyMatch(necesidad::esSatisfechaPor);
  }

//METODO PARA QUE UNA DONACION REALICE EL PROCESO DE MATCHMAKING
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

}

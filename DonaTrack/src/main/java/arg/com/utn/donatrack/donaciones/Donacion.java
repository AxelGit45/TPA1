package arg.com.utn.donatrack.donaciones;

import java.util.ArrayList;
import java.util.List;

public class Donacion {

  //private Bien bien;
  private List<Bien> bienes;
  private Estados estadoDonacion;
  private List<Estados> historialDeCambiosDeEstado;
  private List<Algoritmo>  algoritmos;

  public Donacion(Bien bien){

    //this.bien = bien; RECIBIR LISTA DE BIENES

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


  public void matchmakin(){

    //lista de algoritmos
    //ejecutoto cada algoritmo y guardo su resultado en la lista (transformacion)
    //aca uso map
    //return lista de listas (de EntidadBeneficiaria);
  }


  List<Algoritmo>

  public algoritmo1(){}
  public algoritmo2(){}

}

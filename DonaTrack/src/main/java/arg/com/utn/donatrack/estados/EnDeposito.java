package arg.com.utn.donatrack.estados;

//import arg.com.utn.donatrack.donaciones.Algoritmo;
import arg.com.utn.donatrack.donaciones.*;
import arg.com.utn.donatrack.entidadesBeneficiarias.*;
import java.util.ArrayList;
import java.util.List;

public class EnDeposito extends EstadoDonacion{

  /*public EnDeposito (List<Algoritmo> algoritmos){
    this.algoritmos = algoritmos;
  }*/

  public List<Algoritmo> algoritmos = new ArrayList<>();

  @Override
  public void asignacionDonaciones(List<EntidadBeneficiaria> entidades, Donacion donacion){  //BIEN

      //ACA SE CREA EL RESULTADO DEL MATCHMAKING

      List<ResultadoAlgoritmo> resultadosDeAlgoritmos = algoritmos.stream().map(algoritmo -> algoritmo.ejecutar(donacion,entidades)).toList();

      ResultadoMatchmaking ranking = new ResultadoMatchmaking(donacion, resultadosDeAlgoritmos);


  }

  @Override
  public void setAlgoritmo(Algoritmo algoritmo) {
    this.algoritmos.add(algoritmo);
  }
}
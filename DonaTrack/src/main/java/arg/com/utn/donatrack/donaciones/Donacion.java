package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.estados.EnDeposito;
import arg.com.utn.donatrack.estados.EstadoDonacion;
import java.util.ArrayList;
import java.util.List;

public class Donacion {

  private Bien bien;
  private EstadoDonacion estadoDonacion;
  private List<EstadoDonacion> historialDeCambiosDeEstado;

  public Donacion(Bien bien){

    this.bien = bien;

    this.historialDeCambiosDeEstado = new ArrayList<>();

    EstadoDonacion estadoInicial = new EnDeposito();

    this.cambiarEstado(estadoInicial);
  }

  void cambiarEstado(EstadoDonacion nuevoEstado){

    this.estadoDonacion = nuevoEstado;
    this.historialDeCambiosDeEstado.add(nuevoEstado);

  }

}

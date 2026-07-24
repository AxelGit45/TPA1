package arg.com.utn.donatrack.donaciones;

import arg.com.utn.donatrack.logistica.Camion;
import arg.com.utn.donatrack.logistica.Ruta;

import java.util.ArrayList;
import java.util.List;

public class ComponenteExternoMock extends ComponenteExterno {

  private List<Ruta> respuestaSimulada;
  private boolean generarRutaFueInvocado = false;
  private List<Donacion> donacionesRecibidas;
  private List<Camion> camionesRecibidos;

  public ComponenteExternoMock(List<Ruta> respuesta) {
    this.respuestaSimulada = respuesta != null ? respuesta : new ArrayList<>();
  }

  @Override
  List<Ruta> generarRuta(List<Donacion> donacionesAsignadas, List<Camion> camionesEnDeposito) {
    generarRutaFueInvocado = true;
    this.donacionesRecibidas = donacionesAsignadas;
    this.camionesRecibidos = camionesEnDeposito;
    return respuestaSimulada;
  }

  public boolean wasGenerarRutaInvocado() {
    return generarRutaFueInvocado;
  }

  public List<Donacion> getDonacionesRecibidas() {
    return donacionesRecibidas;
  }

  public List<Camion> getCamionesRecibidos() {
    return camionesRecibidos;
  }
}

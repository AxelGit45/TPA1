package arg.com.utn.donatrack.donaciones;

import java.util.ArrayList;
import java.util.List;

public class ComponenteExternoMock extends ComponenteExterno {

  private List<Long> respuestaSimulada;
  private boolean generarRutaFueInvocado = false;
  private List<Donacion> donacionesRecibidas;
  private List<Long> camionesRecibidos;

  public ComponenteExternoMock(List<Long> respuesta) {
    this.respuestaSimulada = respuesta != null ? respuesta : new ArrayList<>();
  }

  @Override
  List<Long> generarRuta(List<Donacion> donacionesAsignadas, List<Long> camionesEnDeposito) {
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

  public List<Long> getCamionesRecibidos() {
    return camionesRecibidos;
  }
}

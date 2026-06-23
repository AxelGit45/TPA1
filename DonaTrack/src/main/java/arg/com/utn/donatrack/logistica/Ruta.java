package arg.com.utn.donatrack.logistica;

import arg.com.utn.donatrack.estados.EnTraslado;

import java.util.List;

public class Ruta {

    private Camion camion;
    private List<Entrega> entregas;

    public Ruta(Camion camion, List<Entrega> entregas) {
      this.camion = camion;
      this.entregas = entregas;
    }

    public void Iniciarse(){
      entregas.stream()
          .flatMap(entrega -> entrega.getDonaciones().stream())
          .forEach(donacion -> donacion.cambiarEstado(new EnTraslado(this.getCamion())));
    }

  private Camion getCamion() {
      return camion;
  }
}

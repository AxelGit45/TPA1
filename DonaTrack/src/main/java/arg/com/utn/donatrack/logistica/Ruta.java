package arg.com.utn.donatrack.logistica;

import arg.com.utn.donatrack.donaciones.Estados;
import arg.com.utn.donatrack.estados.*;

import java.util.List;

public class Ruta {

    private Long Id;
    private Camion camion;
    private List<Entrega> entregas;

    public Ruta(Long Id, Camion camion, List<Entrega> entregas) {
      this.Id = Id;
      this.camion = camion;
      this.entregas = entregas;
    }

    /*public void Iniciarse(){
      entregas.stream()
          .flatMap(entrega -> entrega.getDonaciones().stream())
          .forEach(donacion -> donacion.cambiarEstado(new EnTraslado(this.getCamion())));
    }*/

  /*
  public void Iniciarse(){
    entregas.forEach(entrega -> entrega.cambiarEstado(EstadoEntrega.ENTRASLADO));
  }*/

  public void Iniciarse(){
    this.cambiarEstadoDeDonaciones();
    this.cambiarEstadoDeEntrega();
  }

  public void cambiarEstadoDeDonaciones() {
    entregas.stream()
        .flatMap(entrega -> entrega.getDonaciones().stream())
        .forEach(donacion -> donacion.cambiarEstado(Estados.ENTRASLADO));
  }

  public void cambiarEstadoDeEntrega() {
    entregas.forEach(entrega -> entrega.cambiarEstado(EstadoEntrega.ENTRASLADO));
  }

  public Camion getCamion() {return camion;}
  public Long getId() { return Id; }
  public List<Entrega> getEntregas() {return entregas;}
  public void setCamion(Camion camion) { this.camion = camion; }
  public void setEntregas(List<Entrega> entregas) { this.entregas = entregas; }
}

package arg.com.utn.donatrack.logistica;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.donaciones.Estados;
import arg.com.utn.donatrack.estados.*;
import arg.com.utn.donatrack.personas.Persona;
import arg.com.utn.donatrack.personas.contactos.Contacto;

import java.util.List;

public class Ruta {

    private Long id;
    private Camion camion;
    private List<Entrega> entregas;
    private String linkMapa;

    public Ruta(Long id, Camion camion, List<Entrega> entregas) {
      this.id = id;
      this.camion = camion;
      this.entregas = entregas;
      this.linkMapa = "http://donatrack.com/mapa/ruta/" + this.id;

      for (Entrega entrega : entregas) {

        for (Contacto contacto : entrega.getEntidadBeneficiaria().getContactos()) {

          contacto.contactar("Su entrega está en camino. Seguilo acá: " + linkMapa);

        }

        for (Donacion donacion : entrega.getDonaciones()) {

          for (Persona donador : donacion.getDonadores()) {

            for (Contacto contacto : donador.getContactos()) {

              contacto.contactar("Una donacion que usted ha hecho esta en su camino a ser donada." +
                  " Seguilo acá: " + linkMapa);

            }

          }

        }

      }

    }

  public void Iniciarse() {
    entregas.forEach(entrega -> entrega.iniciarTraslado());
  }

  public Camion getCamion() {return camion;}
  public Long getId() { return id; }
  public List<Entrega> getEntregas() {return entregas;}
  public void setCamion(Camion camion) { this.camion = camion; }
  public void setEntregas(List<Entrega> entregas) { this.entregas = entregas; }
}

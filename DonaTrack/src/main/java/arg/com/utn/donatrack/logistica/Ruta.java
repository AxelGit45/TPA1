package arg.com.utn.donatrack.logistica;

import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import java.util.List;

public class Ruta {

    private Camion camion;
    private List<Destino> destionos;

    public Ruta(Camion camion, List<Destino> destionos) {
      this.camion = camion;
      this.destionos = destionos;
    }

    public void IniciarRuta(){
    ////A todoas la donaciones las  cambia a estado ENTRASLADO
    }
}

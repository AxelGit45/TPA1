package arg.com.utn.donatrack.estados;

import arg.com.utn.donatrack.logistica.Camion;

public class EnTraslado extends EstadoDonacion{

  private Camion camion;

  public EnTraslado(Camion camion){

    this.camion = camion;

  }

}

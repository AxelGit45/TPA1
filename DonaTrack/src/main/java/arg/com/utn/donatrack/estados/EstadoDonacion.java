package arg.com.utn.donatrack.estados;

import java.time.LocalDate;

public abstract class EstadoDonacion {

  private LocalDate fechaDeAsignacion;

  public EstadoDonacion() {

    this.fechaDeAsignacion = LocalDate.now();

  }

}

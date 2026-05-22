package Modelos;

import lombok.Setter;

import java.time.LocalDate;


public class NecesidadRecurrente extends Necesidad {
  @Setter
  private LocalDate fechaInicioPedido;
  private int cantidadRequerida;
  @Setter
  private int cantidadRecibidad;
  private ComprobarFechaEstablecida periodoEstablecido;

  public NecesidadRecurrente(Bien categoria, String descripcion, int cantidadRequerida, ComprobarFechaEstablecida periodo) {
    super(categoria, descripcion);
    this.cantidadRequerida = cantidadRequerida;
    this.cantidadRecibidad = 0;
    this.fechaInicioPedido = LocalDate.now();
    this.periodoEstablecido = periodo;
  }

  public void registrarItems(int cantidad){
    cantidadRecibidad += cantidad;
  }

  private boolean verificarCantiadadNecesaria(){
    return  cantidadRecibidad >= cantidadRequerida;
  }

  public void renovarPeriodo(){
    LocalDate hoy = LocalDate.now();
    if(necesidadSatisfecha()){
      setFechaInicioPedido(hoy);
      setCantidadRecibidad(0);
    }
  }

  private boolean verificarPeriodo(){
    return periodoEstablecido.cumplePeriodo(fechaInicioPedido,LocalDate.now());
  }

  @Override
  public boolean necesidadSatisfecha() {
    return  verificarCantiadadNecesaria() && verificarPeriodo();
  }
}

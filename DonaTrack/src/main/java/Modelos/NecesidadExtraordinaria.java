package Modelos;

public class NecesidadExtraordinaria extends Necesidad {
  private int cantidadRequeridad;
  private  int cantidadRecbidad;
  public NecesidadExtraordinaria(int cantidadRequeridad, Bien categoria, String descripcion) {
    super( categoria, descripcion);
    this.cantidadRecbidad =0;
    this.cantidadRequeridad = cantidadRequeridad;
  }
  public void registrarItems(int cantidad){
    cantidadRecbidad += cantidad;
  }
  @Override
  public boolean necesidadSatisfecha() {

    return cantidadRecbidad >= this.cantidadRequeridad;
  }
}

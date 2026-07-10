package arg.com.utn.donatrack.estados;

public class ListaParaEntregar extends EstadoDonacion{

  private String ruta;

  public ListaParaEntregar(){}

  public ListaParaEntregar(String ruta){

    this.ruta = ruta;

  }

  public String getRuta() {
    return ruta;
  }

}
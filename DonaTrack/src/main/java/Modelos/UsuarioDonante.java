package Modelos;
import java.util.List;

public class UsuarioDonante {

  private int id;
  private String nombreDeUsuario;
  private List<Donacion> donaciones;


  public String getNombreDeUsuario() {
    return nombreDeUsuario;
  }

  public void setNombreDeUsuario(String nombreDeUsuario) {
    this.nombreDeUsuario = nombreDeUsuario;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}

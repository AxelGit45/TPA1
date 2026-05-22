package Services;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import Modelos.Donacion;
import Modelos.Bien;
import Enums.EstadoDonacion;
import Modelos.Perecedero;

public class DonacionService {

  private String generarClaveParaHash(Bien bien) {
    String subcategoria = bien.getSubcategoria();

    if(bien.getTipoDelBien() instanceof Perecedero perecedero) {
      return subcategoria + '/' + perecedero.getFechaVencimiento();
    }

    return subcategoria;
  }

  public List<Donacion> dividirPorSubcategoria(Donacion donacionOriginal){

    Map<String, List<Bien>> bienesporSubcategoria = new HashMap<>();

    for(Bien bien : donacionOriginal.getBienes()) {
      String subcategoria = generarClaveParaHash(bien);
      bienesporSubcategoria.computeIfAbsent(subcategoria, nombreSubcategoria -> new ArrayList<>()).add(bien);
    }

    List<Donacion> donacionesPorSubcategoria = new ArrayList<>();
    for(List<Bien> bienesDeLaSubcategoria  : bienesporSubcategoria.values()) {
      donacionesPorSubcategoria.add(
          new Donacion(donacionOriginal.getNombreDonante(), bienesDeLaSubcategoria, 1, "", EstadoDonacion.ENDEPOSITO));
    }
    
    return donacionesPorSubcategoria;
  }
}
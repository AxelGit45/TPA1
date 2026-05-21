package Services;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import Modelos.Donacion;
import Modelos.Bien;
import Enums.EstadoDonacion;

public class DonacionService {

  public List<Donacion> dividirPorSubcategoria(Donacion donacionOriginal){

    Map<String, List<Bien>> bienesporSubcategoria = new HashMap<>();

    for(Bien bien : donacionOriginal.getBienes()) {
      String subcategoria = bien.getSubcategoria();
      bienesporSubcategoria.computeIfAbsent(subcategoria, nombreSubcategoria -> new ArrayList<>()).add(bien);
    }

    List<Donacion> donacionesPorSubcategoria = new ArrayList<>();
    for(List<Bien> bienesDeLaSubcategoria  : bienesporSubcategoria.values()){
      donacionesPorSubcategoria.add(
          new Donacion(donacionOriginal.getNombreDonante(),bienesDeLaSubcategoria, EstadoDonacion.ENDEPOSITO, 1));
    }
    return donacionesPorSubcategoria;
  }
}

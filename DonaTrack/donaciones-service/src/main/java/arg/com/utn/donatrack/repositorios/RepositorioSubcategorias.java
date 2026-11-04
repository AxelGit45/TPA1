package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.donaciones.Categoria;
import arg.com.utn.donatrack.donaciones.Subcategoria;

import javax.persistence.EntityManager;
import java.util.List;

public class RepositorioSubcategorias {

  /** Devuelve la subcategoría guardada con ese nombre; si no existe, la crea (con su categoría). */
  public Subcategoria obtenerOCrear(EntityManager em, Subcategoria buscada) {
    if (buscada == null) {
      return null;
    }
    List<Subcategoria> existentes = em
        .createQuery("select s from Subcategoria s where s.nombre = :nombre", Subcategoria.class)
        .setParameter("nombre", buscada.getNombre())
        .getResultList();
    if (!existentes.isEmpty()) {
      return existentes.get(0);
    }
    Subcategoria nueva = new Subcategoria(
        buscada.getNombre(),
        obtenerOCrearCategoria(em, buscada.getCategoriaPadre()),
        buscada.getExigeFechaVencimiento(),
        buscada.getExigeEstadoUso());
    em.persist(nueva);
    return nueva;
  }

  public Categoria obtenerOCrearCategoria(EntityManager em, Categoria buscada) {
    if (buscada == null) {
      return null;
    }
    List<Categoria> existentes = em
        .createQuery("select c from Categoria c where c.nombre = :nombre", Categoria.class)
        .setParameter("nombre", buscada.getNombre())
        .getResultList();
    if (!existentes.isEmpty()) {
      return existentes.get(0);
    }
    Categoria nueva = new Categoria(buscada.getNombre());
    em.persist(nueva);
    return nueva;
  }
}
package arg.com.utn.donatrack.repositorios;

import arg.com.utn.donatrack.donaciones.Categoria;
import arg.com.utn.donatrack.donaciones.Subcategoria;
import arg.com.utn.donatrack.persistencia.Persistencia;

import javax.persistence.EntityManager;
import java.util.List;

public class RepositorioSubcategorias {

  private EntityManager entityManager;

  public  RepositorioSubcategorias() {
    this.entityManager = Persistencia.crearEntityManager();
  }

  public List<Subcategoria> listarSubcategorias() {
    return entityManager.createQuery("select s from Subcategoria s", Subcategoria.class).getResultList();
  }

  public Subcategoria buscarSubcategoriaPorId (Long id) {
    if (id == null) {
      return null;
    }
    return entityManager.find(Subcategoria.class, id);
  }


  /** Devuelve la subcategoría guardada con ese nombre; si no existe, la crea (con su categoría). */
  public Subcategoria obtenerOCrear(Subcategoria buscada) {
    if (buscada == null) {
      return null;
    }
    List<Subcategoria> existentes = entityManager
        .createQuery("select s from Subcategoria s where s.nombre = :nombre", Subcategoria.class)
        .setParameter("nombre", buscada.getNombre())
        .getResultList();
    if (!existentes.isEmpty()) {
      return existentes.get(0);
    }
    Subcategoria nueva = new Subcategoria(
        buscada.getNombre(),
        new Categoria("ejemplo"),
        // obtenerOCrearCategoria(buscada.getCategoriaPadre()),
        buscada.getExigeFechaVencimiento(),
        buscada.getExigeEstadoUso());
    entityManager.persist(nueva);
    return nueva;
  }

  /*public Categoria obtenerOCrearCategoria(Categoria buscada) {
    if (buscada == null) {
      return null;
    }
    List<Categoria> existentes = entityManager
        .createQuery("select c from Categoria c where c.nombre = :nombre", Categoria.class)
        .setParameter("nombre", buscada.getNombre())
        .getResultList();
    if (!existentes.isEmpty()) {
      return existentes.get(0);
    }
    Categoria nueva = new Categoria(buscada.getNombre());
    entityManager.persist(nueva);
    return nueva;
  }*/

  public List<Categoria> listarCategorias() {
    return entityManager.createQuery("select c from Categoria c", Categoria.class).getResultList();
  }
}
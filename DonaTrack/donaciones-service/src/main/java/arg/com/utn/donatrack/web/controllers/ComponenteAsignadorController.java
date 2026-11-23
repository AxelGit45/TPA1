package arg.com.utn.donatrack.web.controllers;

import arg.com.utn.donatrack.donaciones.CompatibilidadSemantica;
import arg.com.utn.donatrack.donaciones.ComponenteAsignadorDeDestinatarios;
import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.donaciones.PrioridadASubAtendidos;
import arg.com.utn.donatrack.donaciones.ResultadoComponenteAsignador;
import arg.com.utn.donatrack.donaciones.ResultadoMatchmaking;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.repositorios.RepositorioComponenteExterno;
import arg.com.utn.donatrack.repositorios.RepositorioDonaciones;
import arg.com.utn.donatrack.repositorios.RepositorioEntidadesBeneficiarias;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.javalin.http.Context;
import javax.persistence.EntityManager;

public class ComponenteAsignadorController {
  /** Controller encargado de comunicarse con el Componente Externo que se encarga de emparejar las donaciones con las entidades beneficiarias. **/
  private ComponenteAsignadorDeDestinatarios componenteExterno = new ComponenteAsignadorDeDestinatarios();

  /*Envio resultados de MatchMaking al componente externo, recibimos los resultados y los persistimos*/
  /*public ResultadoComponenteAsignador enviarResultadoAlgoritmos(ResultadoMatchmaking resultadoAlgoritmos) {
    return componenteExterno.obtenerResultadoDeAlgoritmosPorDonacion(resultadoAlgoritmos);
  }
  */

  public ComponenteAsignadorController(){}



  public void guardarResultadosDeComponenteExterno(Context ctx) {
    EntityManager em = Persistencia.crearEntityManager();

    RepositorioEntidadesBeneficiarias repoEntidades = new RepositorioEntidadesBeneficiarias();
    RepositorioDonaciones repositorioDonaciones = new RepositorioDonaciones();
    RepositorioComponenteExterno repositorioComponenteExterno = new RepositorioComponenteExterno();

    int cantidadDeDonaciones = repositorioDonaciones.listarTodas(em).size();
    List<Donacion> donaciones = repositorioDonaciones.listarTodas(em);
    String id = "29";
    Long donacionId = Long.parseLong(id);
    donaciones.stream().dropWhile(donacion -> donacion.getId() < donacionId);
    List<EntidadBeneficiaria> entidadesBeneficiarias = repoEntidades.listarTodas(em);
    //entidadesBeneficiarias.get(4);
    List<EntidadBeneficiaria> entidades = new ArrayList<>();
    entidades.add(entidadesBeneficiarias.get(3));

    List<ResultadoMatchmaking> resultadosMatchmaking = donaciones.
        stream().map(donacion -> donacion.getEstado().asignacionDonaciones(entidades, donacion)).toList();

    // ComponenteAsignadorDeDestinatarios compExterno = new ComponenteAsignadorDeDestinatarios();

    List<ResultadoComponenteAsignador> resultadoComponenteAsignador = resultadosMatchmaking.stream().
        map(resultadoMatchmaking -> componenteExterno.obtenerResultadoDeAlgoritmosPorDonacion(resultadoMatchmaking))
        .toList();

    repositorioComponenteExterno.guardarResultadosDeComponenteExterno(em, resultadoComponenteAsignador);

    Map<String, Object> modelo = new HashMap<>();
    ctx.render("admin-matchmaking.html.hbs", modelo);
  }



  /* ejecuta y obtiene ResultadosMatchMaking*/
  /*Le paso los resultados al componenteExterno*/
  /*Obtengo lo que me devolvio el componente*/
  /*Llamo al repo*/
  /*Persisto*/

  /*ctx,redirect(pagina admin/matchmaking)*/



}

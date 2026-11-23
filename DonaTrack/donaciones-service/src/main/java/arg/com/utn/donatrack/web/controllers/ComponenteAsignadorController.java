package arg.com.utn.donatrack.web.controllers;

import arg.com.utn.donatrack.donaciones.CompatibilidadSemantica;
import arg.com.utn.donatrack.donaciones.ComponenteAsignadorDeDestinatarios;
import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.donaciones.Estados;
import arg.com.utn.donatrack.donaciones.PrioridadASubAtendidos;
import arg.com.utn.donatrack.donaciones.ResultadoComponenteAsignador;
import arg.com.utn.donatrack.donaciones.ResultadoMatchmaking;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.estados.AsignacionRealizada;
import arg.com.utn.donatrack.grpc.logistica.PlanificarRutasRequest;
import arg.com.utn.donatrack.persistencia.Persistencia;
import arg.com.utn.donatrack.repositorios.RepositorioComponenteExterno;
import arg.com.utn.donatrack.repositorios.RepositorioDonaciones;
import arg.com.utn.donatrack.repositorios.RepositorioEntidadesBeneficiarias;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.javalin.http.Context;
import javax.persistence.EntityManager;

public class ComponenteAsignadorController {
  /** Controller encargado de comunicarse con el Componente Externo que se encarga de emparejar las donaciones con las entidades beneficiarias. **/
  private ComponenteAsignadorDeDestinatarios componenteExterno = new ComponenteAsignadorDeDestinatarios();

  public ComponenteAsignadorController(){}

  public void guardarResultadosDeComponenteExterno(Context ctx) {
    EntityManager em = Persistencia.crearEntityManager();

    RepositorioEntidadesBeneficiarias repoEntidades = new RepositorioEntidadesBeneficiarias();
    RepositorioDonaciones repositorioDonaciones = new RepositorioDonaciones();
    RepositorioComponenteExterno repositorioComponenteExterno = new RepositorioComponenteExterno();

    List<Donacion> donaciones = repositorioDonaciones.listarEnDeposito(em);
    List<EntidadBeneficiaria> entidades = repoEntidades.listarConNecesidades(em);

    LocalDate haceTresMeses = LocalDate.now().minusMonths(3);
    entidades.forEach(entidad -> entidad.setDonacionesRecibidasUltimoTrimestre(
        repoEntidades.contarDonacionesRecibidasDesde(em, entidad.getId(), haceTresMeses)));

    List<ResultadoMatchmaking> resultadosMatchmaking = donaciones.
        stream().map(donacion -> donacion.getEstado().asignacionDonaciones(entidades, donacion)).toList();

    List<ResultadoComponenteAsignador> resultadoComponenteAsignador = resultadosMatchmaking.stream().
        map(resultadoMatchmaking -> componenteExterno.obtenerResultadoDeAlgoritmosPorDonacion(resultadoMatchmaking))
        .filter(resultado -> resultado!=null)
        .toList();

    repositorioComponenteExterno.guardarResultadosDeComponenteExterno(em, resultadoComponenteAsignador);

    em.close();
    ctx.redirect("/admin/matchmaking");

  }

}

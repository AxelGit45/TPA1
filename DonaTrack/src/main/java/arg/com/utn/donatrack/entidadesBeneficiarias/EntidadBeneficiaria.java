package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.estados.EntregaFallida;
import arg.com.utn.donatrack.estados.Entregada;
import arg.com.utn.donatrack.logistica.Camion;
import arg.com.utn.donatrack.logistica.Entrega;
import arg.com.utn.donatrack.personas.contactos.Mail;
import java.util.List;

public class EntidadBeneficiaria {

  private String razonSocial;
  private String direccion;
  private Integer telefono;
  private List<Mail> correos;
  private List<Necesidad> necesidades;
  private List<Camion> historialDeCamiones;

  public EntidadBeneficiaria(String razonSocial, String direccion, Integer telefono,
                             List<Mail> correos, List<Necesidad> necesidades){

    this.razonSocial = razonSocial;
    this.direccion = direccion;
    this.telefono = telefono;
    this.correos = correos;
    this.necesidades = necesidades;

  }
  public List<Necesidad> getNecesidades(){
    return necesidades;
  }

  public

  public void confirmarRecepcionDeEntrega(Entrega entrega, Camion camionEntregador){
    entrega.getDonaciones().forEach(donacion -> donacion.cambiarEstado(new Entregada()));
    historialDeCamiones.add(camionEntregador); // REVISAR !!!!!!!
  }

  public void cargarFotosDeEntrega(List<String> urlFotos){
    // TO DO
  }

  public void informarNoRecepcion(Entrega entrega){
    // entrega.getDonaciones().forEach(donacion -> donacion.cambiarEstado(new EntregaFallida(justificacion)));
    // FALTA DE DÓNDE SACAR LA JUSTIFICACIÓN
  }
}

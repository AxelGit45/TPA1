package arg.com.utn.donatrack.logistica;

import arg.com.utn.donatrack.clients.DonacionesClient;
import arg.com.utn.donatrack.dtos.*;

import java.util.ArrayList;
import java.util.List;

public class Ruta {

    private Long id;
    private Long camionId;
    private List<Long> entregaIds;
    private String linkMapa;

    public Ruta() {}

    public Ruta(Long id, Long camionId, List<Long> entregaIds) {
      this.id = id;
      this.camionId = camionId;
      this.entregaIds = entregaIds;
      this.linkMapa = "http://donatrack.com/mapa/ruta/" + this.id;
    }

    public void Iniciarse(List<Entrega> entregasRepositorio) {
      for (Entrega entrega : entregasRepositorio) {
        if (entregaIds.contains(entrega.getId())) {
          entrega.iniciarTraslado(this.camionId);
        }
      }
      notificar(entregasRepositorio);
    }

    public void notificar(List<Entrega> entregasRepositorio) {
      DonacionesClient donacionesClient = new DonacionesClient();

      for (Entrega entregaLocal : entregasRepositorio) {
        if (!entregaIds.contains(entregaLocal.getId())) continue;

        Long entidadId = entregaLocal.getEntidadBeneficiariaId();
        EntidadBeneficiariaDTO entidad = donacionesClient.obtenerEntidad(entidadId);
        List<ContactoDTO> contactosEntidad = donacionesClient.obtenerContactosEntidad(entidadId);

        for (ContactoDTO contacto : contactosEntidad) {
          enviarContacto(contacto, "Su entrega está en camino. Seguilo acá: " + linkMapa);
        }

        for (Long donacionId : entregaLocal.getDonacionIds()) {
          List<PersonaDTO> donadores = donacionesClient.obtenerDonadores(donacionId);
          for (PersonaDTO donador : donadores) {
            for (ContactoDTO contacto : donador.getContactos()) {
              enviarContacto(contacto, "Una donacion que usted ha hecho esta en su camino a ser donada. Seguilo acá: " + linkMapa);
            }
          }
        }
      }
    }

    private void enviarContacto(ContactoDTO contacto, String mensaje) {
      System.out.println("[NOTIFICACIÓN " + contacto.getTipo() + "] " + contacto.getValor() + ": " + mensaje);
    }

    public Long getCamionId() { return camionId; }
    public Long getId() { return id; }
    public List<Long> getEntregaIds() { return entregaIds; }
    public String getLinkMapa() { return linkMapa; }
    public void setId(Long id) {
      this.id = id;
      this.linkMapa = "http://donatrack.com/mapa/ruta/" + this.id;
    }
    public void setCamionId(Long camionId) { this.camionId = camionId; }
    public void setEntregaIds(List<Long> entregaIds) { this.entregaIds = entregaIds; }
}

package arg.com.utn.donatrack.logistica;

import arg.com.utn.donatrack.clients.DonacionesClient;
import arg.com.utn.donatrack.dtos.DonacionDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Entrega {
  private Long id;
  private String direccionEntidadBeneficiaria;
  private List<Long> donacionIds;
  private EstadoEntrega estadoEntrega;
  private Long camionId;
  private LocalDate fechaDeEntregaEsperada;
  private Long entidadBeneficiariaId;

  public Entrega() {
    this.donacionIds = new ArrayList<>();
  }

  public Entrega(Long id, String direccionEntidadBeneficiaria, LocalDate fechaDeEntregaEsperada,
                 Long entidadBeneficiariaId) {
    this.id = id;
    this.direccionEntidadBeneficiaria = direccionEntidadBeneficiaria;
    this.fechaDeEntregaEsperada = fechaDeEntregaEsperada;
    this.estadoEntrega = EstadoEntrega.PENDIENTE;
    this.entidadBeneficiariaId = entidadBeneficiariaId;
    this.donacionIds = new ArrayList<>();
  }

  public Long getId() { return id; }
  public List<Long> getDonacionIds() { return this.donacionIds; }

  public void cambiarEstado(EstadoEntrega nuevoEstado){
    this.estadoEntrega = nuevoEstado;
  }

  public void iniciarTraslado(Long camionId) {
    this.camionId = camionId;
    this.cambiarEstado(EstadoEntrega.ENTRASLADO);
    DonacionesClient client = new DonacionesClient();
    for (Long donacionId : donacionIds) {
      client.cambiarEstadoDonacion(donacionId, "EN_TRASLADO", camionId, null, null);
    }
  }

  public void confirmarRecepcionDeEntrega(Long camionId) {
    this.cambiarEstado(EstadoEntrega.ENTREGADA);
    DonacionesClient client = new DonacionesClient();
    for (Long donacionId : donacionIds) {
      client.cambiarEstadoDonacion(donacionId, "ENTREGADA", camionId, this.id, null);
    }
  }

  public void informarNoRecepcion(Long camionId) {
    if (this.entregaTardia()) {
      this.cambiarEstado(EstadoEntrega.NORECIBIDA);
      DonacionesClient client = new DonacionesClient();
      for (Long donacionId : donacionIds) {
        client.cambiarEstadoDonacion(donacionId, "ENTREGA_FALLIDA", camionId, this.id, null);
      }
    }
  }

  public boolean entregaTardia() {
    LocalDate fechaDeHoy = LocalDate.now();
    return fechaDeEntregaEsperada.isBefore(fechaDeHoy);
  }

  public void volverAPendiente() {
    DonacionesClient client = new DonacionesClient();
    boolean algunaEnDeposito = donacionIds.stream()
        .map(client::obtenerDonacion)
        .filter(d -> d != null)
        .anyMatch(d -> "EN_DEPOSITO".equals(d.getEstado()));
    if (algunaEnDeposito) {
      this.cambiarEstado(EstadoEntrega.PENDIENTE);
    }
  }

  public String getDireccionEntidadBeneficiaria() { return direccionEntidadBeneficiaria; }
  public Long getEntidadBeneficiariaId() { return entidadBeneficiariaId; }
  public EstadoEntrega getEstadoEntrega() { return estadoEntrega; }
  public Long getCamionId() { return camionId; }
  public LocalDate getFechaDeEntregaEsperada() { return fechaDeEntregaEsperada; }

  public void setDireccionEntidadBeneficiaria(String d) { this.direccionEntidadBeneficiaria = d; }
  public void setDonacionIds(List<Long> donacionIds) { this.donacionIds = donacionIds; }
  public void setEstadoEntrega(EstadoEntrega estadoEntrega) { this.estadoEntrega = estadoEntrega; }
  public void setCamionId(Long camionId) { this.camionId = camionId; }
  public void setFechaDeEntregaEsperada(LocalDate f) { this.fechaDeEntregaEsperada = f; }
  public void setEntidadBeneficiariaId(Long id) { this.entidadBeneficiariaId = id; }
}

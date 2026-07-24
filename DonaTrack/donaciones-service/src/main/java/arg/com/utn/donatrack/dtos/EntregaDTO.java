package arg.com.utn.donatrack.dtos;

public class EntregaDTO {
    private Long id;
    private String direccionEntidadBeneficiaria;
    private String fechaDeEntregaEsperada;
    private String estadoEntrega;
    private Long entidadBeneficiariaId;

    public EntregaDTO() {}

    public EntregaDTO(Long id, String direccion, String fecha, String estado, Long entidadId) {
        this.id = id;
        this.direccionEntidadBeneficiaria = direccion;
        this.fechaDeEntregaEsperada = fecha;
        this.estadoEntrega = estado;
        this.entidadBeneficiariaId = entidadId;
    }

    public Long getId() { return id; }
    public String getDireccionEntidadBeneficiaria() { return direccionEntidadBeneficiaria; }
    public String getFechaDeEntregaEsperada() { return fechaDeEntregaEsperada; }
    public String getEstadoEntrega() { return estadoEntrega; }
    public Long getEntidadBeneficiariaId() { return entidadBeneficiariaId; }

    public void setId(Long id) { this.id = id; }
    public void setDireccionEntidadBeneficiaria(String d) { this.direccionEntidadBeneficiaria = d; }
    public void setFechaDeEntregaEsperada(String f) { this.fechaDeEntregaEsperada = f; }
    public void setEstadoEntrega(String e) { this.estadoEntrega = e; }
    public void setEntidadBeneficiariaId(Long e) { this.entidadBeneficiariaId = e; }
}
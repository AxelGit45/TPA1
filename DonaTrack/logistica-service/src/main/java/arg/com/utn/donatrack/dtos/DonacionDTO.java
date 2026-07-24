package arg.com.utn.donatrack.dtos;

import java.util.List;

public class DonacionDTO {
    private Long id;
    private String estado;
    private List<PersonaDTO> donadores;

    public DonacionDTO() {}

    public DonacionDTO(Long id, String estado, List<PersonaDTO> donadores) {
        this.id = id;
        this.estado = estado;
        this.donadores = donadores;
    }

    public Long getId() { return id; }
    public String getEstado() { return estado; }
    public List<PersonaDTO> getDonadores() { return donadores; }

    public void setId(Long id) { this.id = id; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setDonadores(List<PersonaDTO> donadores) { this.donadores = donadores; }
}

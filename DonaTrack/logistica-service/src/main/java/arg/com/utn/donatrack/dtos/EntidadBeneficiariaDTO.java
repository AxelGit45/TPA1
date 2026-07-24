package arg.com.utn.donatrack.dtos;

import java.util.List;

public class EntidadBeneficiariaDTO {
    private Long id;
    private String razonSocial;
    private String direccion;
    private List<ContactoDTO> contactos;

    public EntidadBeneficiariaDTO() {}

    public EntidadBeneficiariaDTO(Long id, String razonSocial, String direccion, List<ContactoDTO> contactos) {
        this.id = id;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.contactos = contactos;
    }

    public Long getId() { return id; }
    public String getRazonSocial() { return razonSocial; }
    public String getDireccion() { return direccion; }
    public List<ContactoDTO> getContactos() { return contactos; }

    public void setId(Long id) { this.id = id; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setContactos(List<ContactoDTO> contactos) { this.contactos = contactos; }
}

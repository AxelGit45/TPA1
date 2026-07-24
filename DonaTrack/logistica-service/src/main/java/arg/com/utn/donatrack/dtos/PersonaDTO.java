package arg.com.utn.donatrack.dtos;

import java.util.List;

public class PersonaDTO {
    private Long id;
    private String nombre;
    private List<ContactoDTO> contactos;

    public PersonaDTO() {}

    public PersonaDTO(Long id, String nombre, List<ContactoDTO> contactos) {
        this.id = id;
        this.nombre = nombre;
        this.contactos = contactos;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public List<ContactoDTO> getContactos() { return contactos; }

    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setContactos(List<ContactoDTO> contactos) { this.contactos = contactos; }
}

package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.personas.contactos.Contacto;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entidad_beneficiaria")
public class EntidadBeneficiaria {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "razon_social", nullable = false)
  private String razonSocial;

  private String direccion;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "entidad_beneficiaria_id")
  private List<Necesidad> necesidades;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "entidad_beneficiaria_id")
  protected List<Contacto> contactos;

  private LocalDate ultimaConexion;
  @ElementCollection
  @CollectionTable(
      name = "peticiones_entregadas",
      joinColumns = @JoinColumn(name = "entidad_beneficiaria_id")
  )
  @Column(name = "peticion_id")
  private List<Long> peticionesEntregadas;

  private int donacionesRecibidasUltimoTrimestre;

  public EntidadBeneficiaria(){
    this.peticionesEntregadas = new ArrayList<>();
  }

  public EntidadBeneficiaria(String razonSocial, String direccion, List<Contacto> contactos, List<Necesidad> necesidades){
    this.razonSocial = razonSocial;
    this.direccion = direccion;
    this.contactos = contactos;
    this.necesidades = necesidades;
    this.peticionesEntregadas = new ArrayList<>();
  }

  public List<Necesidad> getNecesidades(){
    return necesidades;
  }

  public int getDonacionesRecibidasUltimoTrimestre(){return donacionesRecibidasUltimoTrimestre;}
  public void setDonacionesRecibidasUltimoTrimestre(int valor){this.donacionesRecibidasUltimoTrimestre = valor;}

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getRazonSocial() { return razonSocial; }
  public String getDireccion() { return direccion; }

  @Transient
  public int cuantoNecesita(Donacion donacion){
    if (!necesidades.isEmpty()) {
      return necesidades.stream().filter(necesidad -> donacion.contieneBienPara(necesidad)).toList().size();
    } else {
      throw new EntidadSinNecesidades("La entidad no posee necesidades en su lista");
    }
  }

  public void cargarFotosDeEntrega() {
    // TODO
  }

  public List<Contacto> getContactos() { return contactos; }
  public void setContactos(List<Contacto> contactos) { this.contactos = contactos; }
  public LocalDate getUltimaConexion(){ return this.ultimaConexion; }
}

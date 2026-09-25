package arg.com.utn.donatrack.entidadesBeneficiarias;

import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.personas.contactos.Contacto;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entidades_beneficiarias")
public class EntidadBeneficiaria {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String razonSocial;
  private String direccion;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "entidad_id")
  private List<Necesidad> necesidades = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinTable(
      name = "entidad_beneficiaria_contactos",
      joinColumns = @JoinColumn(name = "entidad_id"),
      inverseJoinColumns = @JoinColumn(name = "contacto_id")
  )
  protected List<Contacto> contactos = new ArrayList<>();

  private LocalDate ultimaConexion;

  @ElementCollection
  @CollectionTable(
      name = "peticiones_entregadas",
      joinColumns = @JoinColumn(name = "entidad_id")
  )
  @Column(name = "peticion_id")
  private List<Long> peticionesEntregadas = new ArrayList<>();

  private int donacionesRecibidasUltimoTrimestre;

  public EntidadBeneficiaria(){
    this.peticionesEntregadas = new ArrayList<>();
    this.necesidades = new ArrayList<>();
  }

  public EntidadBeneficiaria(String razonSocial, String direccion, List<Contacto> contactos, List<Necesidad> necesidades){
    this.razonSocial = razonSocial;
    this.direccion = direccion;
    this.contactos = contactos;
    this.necesidades = necesidades != null ? necesidades : new ArrayList<>();
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

  public String getNombre() {
    return this.razonSocial;
  }
}

package arg.com.utn.donatrack.dtos;

public class CamionDTO {
    private Long id;
    private String patente;
    private String idGps;
    private Double latitud;
    private Double longitud;

    public CamionDTO() {}

    public CamionDTO(Long id, String patente, String idGps, Double latitud, Double longitud) {
        this.id = id;
        this.patente = patente;
        this.idGps = idGps;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Long getId() { return id; }
    public String getPatente() { return patente; }
    public String getIdGps() { return idGps; }
    public Double getLatitud() { return latitud; }
    public Double getLongitud() { return longitud; }

    public void setId(Long id) { this.id = id; }
    public void setPatente(String patente) { this.patente = patente; }
    public void setIdGps(String idGps) { this.idGps = idGps; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }
}

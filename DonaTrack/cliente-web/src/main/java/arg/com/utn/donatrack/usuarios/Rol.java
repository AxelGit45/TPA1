package arg.com.utn.donatrack.usuarios;

public enum Rol {
  DONANTE("/donante"),
  ENTIDAD("/entidad"),
  ADMINISTRADOR("/admin");

  private final String landing;

  Rol(String landing) { this.landing = landing; }

  public String getLanding() { return landing; }
}

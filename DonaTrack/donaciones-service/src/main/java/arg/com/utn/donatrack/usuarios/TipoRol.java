package arg.com.utn.donatrack.usuarios;

public enum TipoRol {
  DONANTE_HUMANO(1),
  DONANTE_JURIDICO(2),
  ENTIDAD(3);

  private final int codigo;

  TipoRol(int codigo) {
    this.codigo = codigo;
  }

  public int getCodigo() {
    return codigo;
  }
}
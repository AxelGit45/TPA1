package arg.com.utn.donatrack.personas.contactos;

public class Telefono extends Contacto{

  private Integer numero;

  public Telefono(Integer numero){

    this.numero = numero;

  }

  @Override
  public void contactar(String mensaje){

    telefonoAPI.contactar(numero, mensaje);

  }

}

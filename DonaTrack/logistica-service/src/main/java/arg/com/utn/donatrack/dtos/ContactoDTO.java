package arg.com.utn.donatrack.dtos;

public class ContactoDTO {
    private String tipo;
    private String valor;

    public ContactoDTO() {}

    public ContactoDTO(String tipo, String valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getTipo() { return tipo; }
    public String getValor() { return valor; }

    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setValor(String valor) { this.valor = valor; }
}

package arg.com.utn.donatrack.clientes;

import arg.com.utn.donatrack.registro.RegistroPersonaHumana;

public interface ClienteDonaciones {
  Long registrarPersonaHumana(Long usuarioId, RegistroPersonaHumana datos);
}

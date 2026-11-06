package arg.com.utn.donatrack.clientes;

import arg.com.utn.donatrack.registro.RegistroPersonaHumana;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ClienteDonacionesMock implements ClienteDonaciones {

  private final AtomicLong ultimoId = new AtomicLong(0);
  private final Map<Long, RegistroPersonaHumana> personasPorUsuario = new ConcurrentHashMap<>();

  @Override
  public Long registrarPersonaHumana(Long usuarioId, RegistroPersonaHumana datos) {
    personasPorUsuario.put(usuarioId, datos);
    System.out.println("[MOCK donaciones-service] PersonaHumana para usuario " + usuarioId
        + ": " + datos.getNombre() + " " + datos.getApellido());
    return ultimoId.incrementAndGet();
  }
}
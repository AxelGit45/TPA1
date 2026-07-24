package arg.com.utn.donatrack.tareas;

import arg.com.utn.donatrack.apis.DonacionesApi;
import arg.com.utn.donatrack.donaciones.Donacion;
import arg.com.utn.donatrack.entidadesBeneficiarias.EntidadBeneficiaria;
import arg.com.utn.donatrack.estados.EnDeposito;
import arg.com.utn.donatrack.repositorios.RepositorioEntidadesBeneficiarias;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AsignadorDeEntidadesParaDonaciones {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void iniciar() {
        Runnable tareaMatchmaking = () -> {
            try {
                System.out.println("[CRON] Ejecutando asignación automática de entidades en horario de baja carga...");
                List<EntidadBeneficiaria> entidades = RepositorioEntidadesBeneficiarias.getEntidadesBeneficiarias();
                List<Donacion> donaciones = DonacionesApi.getDonacionesEnMemoria();

                if (entidades.isEmpty() || donaciones.isEmpty()) {
                    System.out.println("[CRON] No hay entidades o donaciones suficientes para procesar.");
                    return;
                }

                int procesadas = 0;
                for (Donacion donacion : donaciones) {
                    if (donacion.getEstado() instanceof EnDeposito) {
                        donacion.realizarProcesoDeMtachmaking(entidades);
                        procesadas++;
                    }
                }
                System.out.println("[CRON] Matchmaking nocturno completado. Donaciones evaluadas: " + procesadas);
            } catch (Exception e) {
                System.err.println("[CRON] Error en la ejecución automática: " + e.getMessage());
            }
        };

        // Ejecutar la primera vez en 1 minuto y luego repetir cada 24 horas
        scheduler.scheduleAtFixedRate(tareaMatchmaking, 1, 24, TimeUnit.HOURS);
        System.out.println("[CRON] AsignadorDeEntidadesParaDonaciones programado exitosamente.");
    }

    public void detener() {
        scheduler.shutdown();
        System.out.println("[CRON] AsignadorDeEntidadesParaDonaciones detenido.");
    }
}

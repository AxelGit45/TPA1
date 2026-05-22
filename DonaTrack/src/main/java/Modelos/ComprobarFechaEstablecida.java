package Modelos;

import java.time.LocalDate;

public interface ComprobarFechaEstablecida {
   boolean cumplePeriodo(LocalDate inicio,LocalDate diaAcutal);
}

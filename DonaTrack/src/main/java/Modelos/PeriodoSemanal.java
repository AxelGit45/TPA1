package Modelos;

import java.time.LocalDate;

public class PeriodoSemanal implements ComprobarFechaEstablecida {
  @Override
  public boolean cumplePeriodo(LocalDate inicio,LocalDate diaActual) {
    return !diaActual.isAfter(inicio.plusWeeks(1));
  }
}

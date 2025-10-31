package independent_clocks;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/** Ahora Clock hereda de Widget (no implementa Observer directamente). */
public abstract class Clock extends Widget {
    protected String worldPlace = "";
    protected int hoursOffsetTimeZone = 0;
    protected LocalDateTime lastTimeRepaint = null;

    /** Utilidad común para limitar el repintado. */
    protected boolean isTimeToRepaint(LocalDateTime now) {
        return lastTimeRepaint == null
                || now.minus(repaintPeriod, ChronoUnit.MILLIS).isAfter(lastTimeRepaint);
    }
}

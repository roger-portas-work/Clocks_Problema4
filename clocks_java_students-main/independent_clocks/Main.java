package independent_clocks;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        // Único ClockTimer para todos los relojes
        ClockTimer clockTimer = new ClockTimer(100);

        // Relojes
        AnalogClock relojAnalogicoEspana = new AnalogClock(0, "España", 1000);
        AnalogClock relojAnalogicoNY = new AnalogClock(-4, "Nova York", 1000);
        DigitalClock relojDigitalEspana = new DigitalClock(0, "España", 1000);

        // Cuenta atrás de 1 minuto
        CountdownTimer countdown = new CountdownTimer(
                Duration.ofMinutes(1), "Cuenta atrás (1 min)", 1000
        );

        //cronometre
        Stopwatch stopwatch = new Stopwatch(clockTimer, "Cronómetro", 10);

        // Registrar los observadores al ClockTimer
        clockTimer.addObserver(relojAnalogicoEspana);
        clockTimer.addObserver(relojDigitalEspana);
        clockTimer.addObserver(relojAnalogicoNY);
        clockTimer.addObserver(countdown);
        clockTimer.addObserver(stopwatch);

        // Mostrar las ventanas
        relojAnalogicoEspana.show();
        relojDigitalEspana.show();
        relojAnalogicoNY.show();
        countdown.show();
        stopwatch.show();
    }
}

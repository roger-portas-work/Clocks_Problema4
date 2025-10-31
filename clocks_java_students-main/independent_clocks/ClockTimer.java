package independent_clocks;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class ClockTimer extends Observable {
    private final Timer timer;
    private final int period; // ms

    public ClockTimer(int period) {
        this.period = period;
        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override public void run() {
                setChanged();
                notifyObservers(LocalDateTime.now());
            }
        }, period, period);
    }

    public int getPeriod() { return period; }

    public void stop() { timer.cancel(); }
}

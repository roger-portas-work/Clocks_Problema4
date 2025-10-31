package independent_clocks;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

/** Ahora hereda directamente de Widget (no de Clock). */
public class Stopwatch extends Widget {
    private long elapsedMs = 0;
    private boolean running = false;
    private final JLabel label = new JLabel("0:00:00:000");
    private final JButton startStopButton = new JButton("Start");
    private final ClockTimer clockTimer;

    public Stopwatch(ClockTimer clockTimer, String name, int repaintEveryMs) {
        this.clockTimer = clockTimer;
        this.repaintPeriod = repaintEveryMs;

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        label.setFont(new Font(Font.DIALOG, Font.PLAIN, 48));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(new JLabel(name));
        startStopButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startStopButton.addActionListener(e -> toggle());
        panel.add(startStopButton);
    }

    private void toggle() {
        running = !running;
        startStopButton.setText(running ? "Stop" : "Start");
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!running) return;
        int step = clockTimer.getPeriod();
        elapsedMs += step;
        label.setText(formatHMSm(elapsedMs));
        panel.repaint();
    }

    private static String formatHMSm(long ms) {
        long hours = ms / 3_600_000;
        long rem = ms % 3_600_000;
        long mins = rem / 60_000;
        rem %= 60_000;
        long secs = rem / 1_000;
        long millis = rem % 1_000;
        return String.format("%d:%02d:%02d:%03d", hours, mins, secs, millis);
    }
}

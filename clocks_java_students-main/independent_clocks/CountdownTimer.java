package independent_clocks;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.util.Observable;

/** Ahora hereda directamente de Widget (no de Clock). */
public class CountdownTimer extends Widget {
    private Duration remaining;
    private final JLabel label = new JLabel();

    public CountdownTimer(Duration duration, String name, int repaintEveryMs) {
        this.remaining = duration;
        this.repaintPeriod = repaintEveryMs;

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        label.setFont(new Font(Font.DIALOG, Font.PLAIN, 48));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(new JLabel(name));
        label.setText(format(remaining));
    }

    @Override
    public void update(Observable o, Object arg) {
        int stepMs = (o instanceof ClockTimer) ? ((ClockTimer) o).getPeriod() : repaintPeriod;
        if (!remaining.isZero() && !remaining.isNegative()) {
            remaining = remaining.minusMillis(stepMs);
            if (remaining.isNegative()) remaining = Duration.ZERO;
            label.setText(format(remaining));
            panel.repaint();
        }
    }

    private static String format(Duration d) {
        long h = d.toHours();
        int m = d.toMinutesPart();
        int s = d.toSecondsPart();
        return String.format("%d:%02d:%02d", h, m, s);
    }
}

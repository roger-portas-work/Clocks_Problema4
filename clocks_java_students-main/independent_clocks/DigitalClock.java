package independent_clocks;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Observable;

public class DigitalClock extends Clock {
    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("H:mm:ss S");
    private final JLabel clockLabel = new JLabel();
    private final JLabel placeLabel = new JLabel();

    public DigitalClock(int hoursOffsetTimeZone, String worldPlace, int repaintEveryMs) {
        this.hoursOffsetTimeZone = hoursOffsetTimeZone;
        this.worldPlace = worldPlace;
        this.repaintPeriod = repaintEveryMs;

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 45, 5, 45));
        panel.setPreferredSize(new Dimension(500, 120));
        clockLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 72));
        clockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(clockLabel);

        placeLabel.setText(worldPlace);
        placeLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
        placeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(placeLabel);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!(arg instanceof LocalDateTime)) return;
        LocalDateTime base = ((LocalDateTime) arg).plus(hoursOffsetTimeZone, ChronoUnit.HOURS);
        if (isTimeToRepaint(base)) {
            clockLabel.setText(base.format(formatter));
            lastTimeRepaint = base;
            panel.revalidate();
            panel.repaint();
        }
    }
}

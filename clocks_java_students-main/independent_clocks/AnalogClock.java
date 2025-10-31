package independent_clocks;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Observable;

public class AnalogClock extends Clock {
    public AnalogClock(int hoursOffsetTimeZone, String worldPlace, int repaintEveryMs) {
        this.hoursOffsetTimeZone = hoursOffsetTimeZone;
        this.worldPlace = worldPlace;
        this.repaintPeriod = repaintEveryMs;
        panel = new MyJPanel();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!(arg instanceof LocalDateTime)) return;
        LocalDateTime base = ((LocalDateTime) arg).plus(hoursOffsetTimeZone, ChronoUnit.HOURS);
        if (isTimeToRepaint(base)) {
            lastTimeRepaint = base;
            panel.repaint();
        }
    }

    private class MyJPanel extends JPanel {
        public MyJPanel() {
            setPreferredSize(new Dimension(400, 300));
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int side = Math.min(getWidth(), getHeight());
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            LocalDateTime now = (lastTimeRepaint != null)
                    ? lastTimeRepaint
                    : LocalDateTime.now().plus(hoursOffsetTimeZone, ChronoUnit.HOURS);

            int second = now.getSecond();
            int minute = now.getMinute();
            int hour = now.getHour() % 12;

            drawHand(g2d, side/2 - 40, (hour + minute / 60.0) / 12.0, 4.0f, Color.BLACK);
            drawHand(g2d, side/2 - 30, (minute + second / 60.0) / 60.0, 2.0f, Color.BLUE);
            drawHand(g2d, side/2 - 20, second / 60.0, 1.0f, Color.RED);

            drawClockFace(g2d, centerX, centerY, side/2 - 40);
        }

        private void drawHand(Graphics2D g2d, int length, double value, float stroke, Color color) {
            double angle = Math.PI * 2 * (value - 0.25);
            int endX = (int) (getWidth()/2 + length * Math.cos(angle));
            int endY = (int) (getHeight()/2 + length * Math.sin(angle));
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(stroke));
            g2d.drawLine(getWidth()/2, getHeight()/2, endX, endY);
        }

        private void drawClockFace(Graphics2D g2d, int centerX, int centerY, int radius) {
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2.0f));
            g2d.drawOval(centerX - radius, centerY - radius, 2*radius, 2*radius);
            for (int i = 1; i <= 12; i++) {
                double angle = Math.PI * 2 * (i / 12.0 - 0.25);
                int dx = centerX + (int) ((radius + 20) * Math.cos(angle));
                int dy = centerY + (int) ((radius + 20) * Math.sin(angle));
                g2d.drawString(Integer.toString(i), dx, dy);
            }
            g2d.drawString(worldPlace, (int)(radius * 1.2), (int)(radius * 2.0));
        }
    }
}

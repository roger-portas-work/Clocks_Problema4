package independent_clocks;

import javax.swing.*;
import java.util.Observer;
import java.util.Observable;

public abstract class Widget implements Observer {
    protected JPanel panel = new JPanel();
    protected int repaintPeriod = 1000; // ms

    /** Observers deben implementar update; por defecto, no hace nada. */
    @Override
    public void update(Observable o, Object arg) {
        // por defecto, vacío; las subclases que lo necesiten lo sobrescriben
    }

    /** Muestra el panel en una ventana Swing. */
    public void show() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}

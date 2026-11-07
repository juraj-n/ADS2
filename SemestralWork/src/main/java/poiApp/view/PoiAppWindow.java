package poiApp.view;

import javax.swing.*;
import java.awt.*;

/**
 * Class represents the main application window frame that hosts the POI Manager content.
 * Views are injected into it.
 *
 * @author Juraj Nižnanský
 * */
public class PoiAppWindow {
    private final JFrame frame;
    public PoiAppWindow() {
        this.frame = new JFrame("gg - POI Manager");
        this.frame.setSize(500, 500);
        this.frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    /**
     * Replaces the current content of the frame with the given panel.
     *
     * @param content the panel to be displayed.
     * */
    public void setContent(JPanel content) {
        this.frame.setContentPane(content);
        this.frame.revalidate();
        this.frame.repaint();
    }
    public JFrame getFrame() {
        return this.frame;
    }
}

package poiApp.view;

import javax.swing.*;

public class PoiAppWindow {
    private final JFrame frame;
    public PoiAppWindow() {
        this.frame = new JFrame("gg - POI Manager");
        this.frame.setSize(1024, 720);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    public void setContent(JPanel content) {
        this.frame.setContentPane(content);
        this.frame.revalidate();
        this.frame.repaint();
    }
}

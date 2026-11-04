package poiApp.view;

import javax.swing.*;
import java.awt.*;

public class PoiView {
    private final JFrame frame;

    public PoiView() {
        this.frame = new JFrame("gg - POI Application");
        this.initializeView();
    }
    private void initializeView() {
        this.frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }
}

package poiApp.view.views;

import javax.swing.*;
import java.awt.*;

public class SelectTreeView {
    private final JPanel view;
    private final JButton kdButton;
    private final JButton quadButton;
    private final JButton quadKDButton;
    public SelectTreeView() {
        this.view = new JPanel(new GridBagLayout());

        this.kdButton = new JButton("KD-Strom");
        this.quadButton = new JButton("Quad-Strom");
        this.quadKDButton = new JButton("Quad-KD-Strom");

        JLabel title = new JLabel("Vyberte si údajovú štruktúru: ", SwingConstants.CENTER);

        JPanel buttons = new JPanel(new GridLayout(3, 1, 10,10));
        buttons.add(this.kdButton);
        buttons.add(this.quadButton);
        buttons.add(this.quadKDButton);

        JPanel content = new JPanel(new BorderLayout(0, 10));
        content.add(title, BorderLayout.NORTH);
        content.add(buttons, BorderLayout.CENTER);
        content.setPreferredSize(new Dimension(300, 150));

        this.view.add(content);
    }

    public JPanel getView() {
        return this.view;
    }
    public JButton getKdButton() {
        return this.kdButton;
    }
    public JButton getQuadButton() {
        return this.quadButton;
    }
    public JButton getQuadKDButton() {
        return this.quadKDButton;
    }
}

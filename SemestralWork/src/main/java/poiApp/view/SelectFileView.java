package poiApp.view;

import javax.swing.*;

public class SelectFileView {
    private final JPanel view;
    private final JButton button = new JButton("Nový");
    public SelectFileView() {
        this.view = new JPanel();
        this.view.add(this.button);
    }

    public JPanel getView() {
        return this.view;
    }
    public JButton getButton() {
        return this.button;
    }
}

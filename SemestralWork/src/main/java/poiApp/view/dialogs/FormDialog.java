package poiApp.view.dialogs;

import javax.swing.*;
import java.awt.*;

public class FormDialog {
    private final JDialog dialog;
    private final JButton submitBtn = new JButton("Odoslať");
    private final JButton cancelBtn = new JButton("Zrušiť");
    private final JLabel error = new JLabel(" ");

    public FormDialog(JFrame frame, String title) {
        this.dialog = new JDialog(frame, title, true);
    }

    public JButton getSubmitBtn() {
        return this.submitBtn;
    }
    public JButton getCancelBtn() {
        return this.cancelBtn;
    }

    public void addForm(JPanel form) {
        this.dialog.add(form, BorderLayout.CENTER);
        this.dialog.add(this.error, BorderLayout.SOUTH);
        this.dialog.pack();
    }
    public void show() {
        this.dialog.setVisible(true);
    }
    public void close() {
        this.dialog.dispose();
    }
    public void showError(String error) {
        this.error.setForeground(Color.RED);
        this.error.setText(error);
    }
}

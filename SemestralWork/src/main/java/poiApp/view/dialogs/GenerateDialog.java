package poiApp.view.dialogs;

import javax.swing.*;
import java.awt.*;

public class GenerateDialog extends FormDialog {
    private final JTextField countField = new JTextField(10);
    public GenerateDialog(JFrame frame) {
        super(frame, "Generovať náhodné POI");

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 10));
        form.add(new JLabel("Počet hodnôt: "));
        form.add(this.countField);
        form.add(this.getCancelBtn());
        form.add(this.getSubmitBtn());
        this.addForm(form);
    }

    public String getCount() {
        return this.countField.getText();
    }
}

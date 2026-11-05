package poiApp.view.dialogs;

import javax.swing.*;
import java.awt.*;

public class SaveDialog extends FormDialog {
    private final JTextField nameField = new JTextField(30);
    public SaveDialog(JFrame frame) {
        super(frame, "Uložiť evidované POI");

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 10));
        form.add(new JLabel("Názov súboru: "));
        form.add(this.nameField);
        form.add(this.getCancelBtn());
        form.add(this.getSubmitBtn());
        this.addForm(form);
    }

    public String getName() {
        return this.nameField.getText();
    }
}

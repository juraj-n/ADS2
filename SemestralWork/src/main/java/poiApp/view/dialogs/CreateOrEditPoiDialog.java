package poiApp.view.dialogs;

import javax.swing.*;
import java.awt.*;

public class CreatePoiDialog extends FormDialog {
    private final JTextField nameField = new JTextField(20);
    private final JTextField descField = new JTextField(50);
    private final JTextField addressField = new JTextField(20);
    private final JTextField latField = new JTextField(10);
    private final JTextField lonField = new JTextField(10);

    public CreatePoiDialog(JFrame frame) {
        super(frame, "Vytvoriť Nový POI");

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.add(new JLabel("Názov: "));
        form.add(this.nameField);
        form.add(new JLabel("Popis: "));
        form.add(this.descField);
        form.add(new JLabel("Adresa: "));
        form.add(this.addressField);
        form.add(new JLabel("Geo. Šírka: "));
        form.add(this.latField);
        form.add(new JLabel("Geo. Dĺžka: "));
        form.add(this.lonField);
        form.add(this.getCancelBtn());
        form.add(this.getSubmitBtn());

        this.addForm(form);
    }

    public String getName() {
        return this.nameField.getText();
    }
    public String getDescription() {
        return this.descField.getText();
    }
    public String getAddress() {
        return this.addressField.getText();
    }
    public String getLatitude() {
        return this.latField.getText();
    }
    public String getLongitude() {
        return this.lonField.getText();
    }
}

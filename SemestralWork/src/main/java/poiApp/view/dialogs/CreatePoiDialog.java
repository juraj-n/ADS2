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

        JPanel formFields = new JPanel(new GridLayout(0, 2, 5, 5));
        formFields.add(new JLabel("Názov: "));
        formFields.add(this.nameField);
        formFields.add(new JLabel("Popis: "));
        formFields.add(this.descField);
        formFields.add(new JLabel("Adresa: "));
        formFields.add(this.addressField);
        formFields.add(new JLabel("Geo. Šírka: "));
        formFields.add(this.latField);
        formFields.add(new JLabel("Geo. Dĺžka: "));
        formFields.add(this.lonField);
        formFields.add(this.getCancelBtn());
        formFields.add(this.getSubmitBtn());

        this.addForm(formFields);
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
    public String getLongidute() {
        return this.lonField.getText();
    }
}

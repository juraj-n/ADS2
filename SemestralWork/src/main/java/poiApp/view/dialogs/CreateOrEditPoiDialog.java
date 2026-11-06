package poiApp.view.dialogs;

import poiApp.model.PoiDto;

import javax.swing.*;
import java.awt.*;

public class CreateOrEditPoiDialog extends FormDialog {
    private final JTextField nameField = new JTextField(20);
    private final JTextField descField = new JTextField(50);
    private final JTextField addressField = new JTextField(20);
    private final JTextField latField = new JTextField(10);
    private final JTextField lonField = new JTextField(10);

    public CreateOrEditPoiDialog(JFrame frame, String title, PoiDto poi) {
        super(frame, title);

        if (poi != null) {
            this.nameField.setText(poi.getName());
            this.descField.setText(poi.getDescription());
            this.addressField.setText(poi.getAddress());
            this.latField.setText(String.valueOf(poi.getLatitude()));
            this.lonField.setText(String.valueOf(poi.getLongitude()));
        }

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

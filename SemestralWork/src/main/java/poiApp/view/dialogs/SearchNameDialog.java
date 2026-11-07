package poiApp.view.dialogs;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog that asks user for area and name of the Pois to be found.
 *
 * @author Juraj Nižnanský
 * */
public class SearchNameDialog extends FormDialog {
    private final JTextField nameField = new JTextField(20);
    private final JTextField minLatField = new JTextField(10);
    private final JTextField maxLatField = new JTextField(10);
    private final JTextField minLonField = new JTextField(10);
    private final JTextField maxLonField = new JTextField(10);

    public SearchNameDialog(JFrame frame) {
        super(frame, "Vyhľadať POI podľa názvu");

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 10));
        form.add(new JLabel("Názov: "));
        form.add(this.nameField);
        form.add(new JLabel("Minimálna Geo. Šírka: "));
        form.add(this.minLatField);
        form.add(new JLabel("Maximálna Geo. Šírka: "));
        form.add(this.maxLatField);
        form.add(new JLabel("Minimálna Geo. Dĺžka: "));
        form.add(this.minLonField);
        form.add(new JLabel("Maximálna Geo. Dĺžka: "));
        form.add(this.maxLonField);
        form.add(this.getCancelBtn());
        form.add(this.getSubmitBtn());

        this.addForm(form);
    }

    public String getName() {
        return this.nameField.getText();
    }
    public String getMinLatitude() {
        return this.minLatField.getText();
    }
    public String getMaxLatitude() {
        return this.maxLatField.getText();
    }
    public String getMinLongitude() {
        return this.minLonField.getText();
    }
    public String getMaxLongitude() {
        return this.maxLonField.getText();
    }
}

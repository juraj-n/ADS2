package poiApp.view.dialogs;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog that asks user how many elements and in which area to be generated.
 *
 * @author Juraj Nižnanský
 * */
public class GenerateDialog extends FormDialog {
    private final JTextField countField = new JTextField(10);
    private final JTextField minLatField = new JTextField(10);
    private final JTextField maxLatField = new JTextField(10);
    private final JTextField minLonField = new JTextField(10);
    private final JTextField maxLonField = new JTextField(10);
    public GenerateDialog(JFrame frame) {
        super(frame, "Generovať náhodné POI");

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 10));
        form.add(new JLabel("Počet hodnôt: "));
        form.add(this.countField);
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

    public String getCount() {
        return this.countField.getText();
    }
    public String getMinLatField() {
        return minLatField.getText();
    }
    public String getMaxLatField() {
        return maxLatField.getText();
    }
    public String getMinLonField() {
        return minLonField.getText();
    }
    public String getMaxLonField() {
        return maxLonField.getText();
    }
}

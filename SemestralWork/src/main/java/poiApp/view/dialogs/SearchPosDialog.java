package poiApp.view.dialogs;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog that asks user for latitude and longitude of the Pois to be found.
 *
 * @author Juraj Nižnanský
 * */
public class SearchPosDialog extends FormDialog {
    private final JTextField latField = new JTextField(10);
    private final JTextField lonField = new JTextField(10);
    public SearchPosDialog(JFrame frame) {
        super(frame, "Vyhľadať POI podľa pozície");

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 10));
        form.add(new JLabel("Geo. Šírka: "));
        form.add(this.latField);
        form.add(new JLabel("Geo. Dĺžka: "));
        form.add(this.lonField);
        form.add(this.getCancelBtn());
        form.add(this.getSubmitBtn());

        this.addForm(form);
    }

    public String getLatitude() {
        return this.latField.getText();
    }
    public String getLongitude() {
        return this.lonField.getText();
    }
}

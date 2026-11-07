package poiApp.view.dialogs;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog that asks user for latitude and longitude and radius of the Pois to be found.
 *
 * @author Juraj Nižnanský
 * */
public class SearchRadiusDialog extends FormDialog {
    private final JTextField latField = new JTextField(10);
    private final JTextField lonField = new JTextField(10);
    private final JTextField radiusField = new JTextField(10);

    public SearchRadiusDialog(JFrame frame) {
        super(frame, "Vyhľadať POI v okolí (m)");

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 10));
        form.add(new JLabel("Geo. Šírka: "));
        form.add(this.latField);
        form.add(new JLabel("Geo. Dĺžka: "));
        form.add(this.lonField);
        form.add(new JLabel("Polomer [m]: "));
        form.add(this.radiusField);
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
    public String getRadius() {
        return this.radiusField.getText();
    }
}

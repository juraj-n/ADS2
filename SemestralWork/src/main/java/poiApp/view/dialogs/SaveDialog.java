package poiApp.view.dialogs;

import javax.swing.*;

public class SaveDialog extends FormDialog {
    private final JTextField nameField = new JTextField(30);
    public SaveDialog(JFrame frame, String title) {
        super(frame, title);
    }
}

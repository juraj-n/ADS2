package poiApp.view.views;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.List;

public class SelectFileView {
    private final JPanel view;
    private final JPanel filesPanel;
    private final JButton newButton;
    public SelectFileView() {
        this.view = new JPanel(new GridBagLayout());

        this.newButton = new JButton("Nová Evidencia");
        this.filesPanel = new JPanel(new BorderLayout());
        this.filesPanel.setLayout(new BoxLayout(this.filesPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(this.filesPanel);

        JPanel content = new JPanel(new BorderLayout(0, 10));
        content.add(scrollPane, BorderLayout.CENTER);
        content.add(newButton, BorderLayout.SOUTH);
        content.setPreferredSize(new Dimension(500, 150));

        this.view.add(content);
    }

    /**
     * Kód vytvorený pomocou AI, zdokumentované v kapitole 4.
     * */
    public void setFiles(List<String> fileNames, Consumer<String> onSelected) {
        for (String name : fileNames) {
            JPanel row = new JPanel(new BorderLayout());

            JButton selectButton = new JButton("Vybrať");
            selectButton.addActionListener(e -> onSelected.accept(name));

            row.add(new JLabel(name), BorderLayout.CENTER);
            row.add(selectButton, BorderLayout.EAST);

            this.filesPanel.add(row);
        }
    }
    public JPanel getView() {
        return this.view;
    }
    public JButton getNewButton() {
        return this.newButton;
    }
}

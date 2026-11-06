package poiApp.view.views;

import poiApp.model.PoiDto;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class MainView {
    private final JPanel view = new JPanel(new BorderLayout());
    private JPanel mainbar, sidebar;
    private JPanel poiPanel;
    private JButton generate, showAll, save;
    private JButton searchPos, searchRadius, createNew, searchName;

    public MainView() {
        this.mainbar = new JPanel();
        this.sidebar = new JPanel(new GridBagLayout());

        this.poiPanel = new JPanel(new BorderLayout());
        this.poiPanel.setLayout(new BoxLayout(this.poiPanel, BoxLayout.Y_AXIS));

        this.view.add(this.mainbar, BorderLayout.CENTER);
        this.view.add(this.sidebar, BorderLayout.EAST);

        this.mainbarSetUp();
        this.sidebarSetUp();
    }

    public JPanel getView() {
        return this.view;
    }
    private void sidebarSetUp() {
        this.sidebar.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));

        this.generate = new JButton("Generovať");
        this.showAll = new JButton("Všetky");
        this.save = new JButton("Save");

        this.searchPos = new JButton("Vyhľadať Pozíciu");
        this.searchRadius = new JButton("Vyhľadať Okolie");
        this.createNew = new JButton("Vytvoriť POI");
        this.searchName = new JButton("Vyhľadať Názov");

        JPanel topButtons = new JPanel(new GridLayout(1, 3, 5, 5));
        topButtons.add(this.generate);
        topButtons.add(this.showAll);
        topButtons.add(this.save);

        JPanel bottomButtons = new JPanel(new GridLayout(4, 1, 5, 5));
        bottomButtons.add(this.searchPos);
        bottomButtons.add(this.searchRadius);
        bottomButtons.add(this.createNew);
        bottomButtons.add(this.searchName);

        JPanel content = new JPanel(new BorderLayout(0, 10));

        content.add(topButtons, BorderLayout.NORTH);
        content.add(bottomButtons, BorderLayout.CENTER);

        this.sidebar.add(content);
    }
    private void mainbarSetUp() {
        // TODO: add header
        JScrollPane scrollPane = new JScrollPane(this.poiPanel);
        scrollPane.setPreferredSize(new Dimension(1200, 800));

        this.mainbar.add(scrollPane);
    }
    public void setPois(List<PoiDto> pois, Consumer<PoiDto> onDelSelected, Consumer<PoiDto> onEdtSelected) {
        for (var p : pois) {
            JPanel row = new JPanel(new BorderLayout());

            JLabel info = new JLabel(p.getInfo());
            info.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));

            JButton deleteBtn = new JButton("Vymazať");
            deleteBtn.addActionListener(e -> onDelSelected.accept(p));
            JButton editBtn = new JButton("Upraviť");
            editBtn.addActionListener(e -> onEdtSelected.accept(p));

            JPanel buttons = new JPanel();
            buttons.add(deleteBtn);
            buttons.add(editBtn);

            row.add(info, BorderLayout.CENTER);
            row.add(buttons, BorderLayout.EAST);

            this.poiPanel.add(row);
        }
    }

    public JButton getGenerateButton() {
        return this.generate;
    }
    public JButton getShowAllButton() {
        return this.showAll;
    }
    public JButton getSaveButton() {
        return this.save;
    }
    public JButton getSearchPosButton() {
        return this.searchPos;
    }
    public JButton getSearchRadiusButton() {
        return this.searchRadius;
    }
    public JButton getCreateNewButton() {
        return this.createNew;
    }
    public JButton getSearchNameButton() {
        return this.searchName;
    }
}

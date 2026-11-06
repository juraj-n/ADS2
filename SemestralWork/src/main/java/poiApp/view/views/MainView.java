package poiApp.view.views;

import poiApp.model.PoiDto;

import java.time.format.DateTimeFormatter;
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
        this.mainbar = new JPanel(new BorderLayout());
        this.sidebar = new JPanel(new GridBagLayout());

        this.poiPanel = new JPanel();
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
        content.add(bottomButtons, BorderLayout.SOUTH);

        this.sidebar.add(content);
    }
    private void mainbarSetUp() {
        // TODO: add header
        JScrollPane scrollPane = new JScrollPane(this.poiPanel);

        this.mainbar.add(scrollPane);
    }
    public void setPois(List<PoiDto> pois, Consumer<PoiDto> onDeleteBtn, Consumer<PoiDto> onEditBtn) {
        this.poiPanel.removeAll();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        for (var p : pois) {
            String[] values = {
                    p.getName(),
                    p.getDescription(),
                    p.getAddress(),
                    String.valueOf(p.getLatitude()),
                    String.valueOf(p.getLongitude()),
                    p.getCreatedAt().format(format),
                    p.getUpdatedAt().format(format)
            };
            JPanel row = this.createRow(values, createButtons(p, onDeleteBtn, onEditBtn));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            this.poiPanel.add(row);
        }

        this.poiPanel.revalidate();
        this.poiPanel.repaint();
    }
    private JPanel createRow(String[] values, JComponent btns) {
        JPanel row = new JPanel(new BorderLayout());

        JPanel cells = new JPanel(new GridLayout(1, values.length, 0, 0));
        for (String v : values) {
            cells.add(this.cell(v));
        }

        row.add(cells, BorderLayout.CENTER);
        row.add(btns, BorderLayout.EAST);

        return row;
    }
    private JPanel createButtons(PoiDto poi, Consumer<PoiDto> onDelelte, Consumer<PoiDto> onEdit) {
        JButton deleteBtn = new JButton("✕");
        deleteBtn.addActionListener(e -> onDelelte.accept(poi));

        JButton editBtn = new JButton("✎");
        editBtn.addActionListener(e -> onEdit.accept(poi));

        JPanel buttons = new JPanel(new GridLayout(2, 0));
        buttons.add(deleteBtn);
        buttons.add(editBtn);

        return buttons;
    }
    private JLabel cell(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
        label.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        return label;
    }


    public JButton getGenerateBtn() {
        return this.generate;
    }
    public JButton getShowAllBtn() {
        return this.showAll;
    }
    public JButton getSaveBtn() {
        return this.save;
    }
    public JButton getSearchPosBtn() {
        return this.searchPos;
    }
    public JButton getSearchRadiusBtn() {
        return this.searchRadius;
    }
    public JButton getCreateBtn() {
        return this.createNew;
    }
    public JButton getSearchNameBtn() {
        return this.searchName;
    }
}

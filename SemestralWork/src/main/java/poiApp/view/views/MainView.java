package poiApp.view.views;

import poiApp.model.PoiDto;
import poiApp.view.dialogs.FormDialog;

import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class MainView {
    private final JPanel view = new JPanel(new BorderLayout());
    private JPanel mainbar, topbar;
    private JPanel poiPanel;
    private JButton generate, showAll, save;
    private JButton searchPos, searchRadius, createNew, searchName;

    public MainView() {
        this.mainbar = new JPanel(new BorderLayout());
        this.topbar = new JPanel(new BorderLayout());

        this.mainbar.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        this.topbar.setBorder(BorderFactory.createEmptyBorder(5,5,10,5));

        this.poiPanel = new JPanel();
        this.poiPanel.setLayout(new BoxLayout(this.poiPanel, BoxLayout.Y_AXIS));

        this.view.add(this.mainbar, BorderLayout.CENTER);
        this.view.add(this.topbar, BorderLayout.NORTH);

        this.mainbarSetUp();
        this.sidebarSetUp();
    }

    public JPanel getView() {
        return this.view;
    }
    private void sidebarSetUp() {
        this.generate = new JButton("Generovať");
        this.showAll = new JButton("Všetky");
        this.save = new JButton("Uložiť");

        this.searchPos = new JButton("Hľadať Pozíciu");
        this.searchRadius = new JButton("Hľadať Okolie");
        this.createNew = new JButton("Vytvoriť POI");
        this.searchName = new JButton("Hľadať Názov");

        JPanel btns = new JPanel(new GridLayout(1, 7, 5, 5));
        btns.add(this.searchPos);
        btns.add(this.searchRadius);
        btns.add(this.createNew);
        btns.add(this.searchName);
        btns.add(this.showAll);
        btns.add(this.generate);
        btns.add(this.save);
        btns.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));

        this.topbar.add(btns);
    }
    private void mainbarSetUp() {
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
        String[] headerValues = new String[]{"Názov", "Popis", "Adresa", "Geo. Šírka", "Geo. Dĺžka", "Vytvorené", "Upravené"};

        JPanel cells = new JPanel(new GridLayout(1, values.length, 0, 0));
        for (int i = 0; i < values.length; i++) {
            JPanel cell = new JPanel(new BorderLayout());

            JLabel head = new JLabel(headerValues[i]);
            head.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
            head.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel val = new JLabel(values[i], SwingConstants.CENTER);
            val.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));


            cell.add(head, BorderLayout.NORTH);
            cell.add(val, BorderLayout.CENTER);
            cell.setBorder(BorderFactory.createLineBorder(Color.BLUE));

            cells.add(cell);
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

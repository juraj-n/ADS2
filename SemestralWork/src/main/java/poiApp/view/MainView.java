package poiApp.view;

import javax.swing.*;
import java.awt.*;

public class MainView {
    private final JPanel view = new JPanel(new BorderLayout());
    private JSplitPane split;
    private JPanel mainbar, sidebar;
    private JButton generate, showAll, save;
    private JButton searchPos, searchRadius, createNew, searchName;

    public MainView() {
        this.mainbar = new JPanel();
        this.sidebar = new JPanel(new BorderLayout(0, 10));

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

        this.sidebar.add(topButtons, BorderLayout.NORTH);
        this.sidebar.add(bottomButtons, BorderLayout.CENTER);
    }
    private void mainbarSetUp() {
        JPanel header = new JPanel(new GridLayout(1, 9));
        header.add(new JLabel("Názov"));
        header.add(new JLabel("Popis"));
        header.add(new JLabel("Geografická šírka"));
        header.add(new JLabel("Geografická dĺžka"));
        header.add(new JLabel("Adresa"));
        header.add(new JLabel("Vytvorený"));
        header.add(new JLabel("Upravený"));

        this.mainbar.add(header);
    }
}

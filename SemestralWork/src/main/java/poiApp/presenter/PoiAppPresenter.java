package poiApp.presenter;

import poiApp.model.Poi;
import poiApp.model.PoiModel;
import poiApp.model.adapter.KDTreeAdapter;
import poiApp.model.adapter.QuadKDTreeAdapter;
import poiApp.model.adapter.QuadTreeAdapter;
import poiApp.model.adapter.TreeAdapter;
import poiApp.view.MainView;
import poiApp.view.SelectFileView;
import poiApp.view.SelectTreeView;
import poiApp.view.PoiAppWindow;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PoiAppPresenter {
    private PoiModel model;
    private final PoiAppWindow window;
    public PoiAppPresenter() {
        this.window = new PoiAppWindow();
        this.showSelectTreeView();
    }

    private void setView(JPanel panel) {
        this.window.setContent(panel);
    }
    private void showMainView() {
        MainView view = new MainView();
        this.setView(view.getView());
    }
    private void showSelectTreeView() {
        SelectTreeView view = new SelectTreeView();
        this.setView(view.getView());

        view.getKdButton().addActionListener(e -> this.onTreeSelected(new KDTreeAdapter()));
        view.getQuadButton().addActionListener(e -> this.onTreeSelected(new QuadTreeAdapter()));
        view.getQuadKDButton().addActionListener(e -> this.onTreeSelected(new QuadKDTreeAdapter()));
    }
    private void showSelectFileView() {
        SelectFileView view = new SelectFileView();
        this.setView(view.getView());

        view.getButton().addActionListener(e -> this.onFileSelected(null));
    }

    /**
     * Kód vytvorený pomocou AI, zdokumentované v kapitole 3.
     * */
    private void onTreeSelected(TreeAdapter<Poi> tree) {
        this.model = new PoiModel(tree);
        boolean emptyDataFolder = true;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("data/S1T3/"), "*.csv")) {
            emptyDataFolder = !stream.iterator().hasNext();
        } catch (IOException ignored) {}

        if (emptyDataFolder) {
            this.showMainView();
        } else {
            this.showSelectFileView();
        }
    }
    private void onFileSelected(File fileToLead) {
        if (fileToLead != null) {
            // TODO: load data from file
        } else {
            this.showMainView();
        }
    }
}

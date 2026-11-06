package poiApp.presenter;

import poiApp.model.Poi;
import poiApp.model.PoiModel;
import poiApp.model.adapter.TreeAdapter;
import poiApp.view.PoiAppWindow;

import javax.swing.*;
import java.util.List;

public class PoiAppNavigator {
    private final PoiAppWindow window = new PoiAppWindow();
    private final PoiMiscMethods miscMethods = new PoiMiscMethods();
    private PoiModel model;

    public void startApp() {
        showSelectTreeView();
    }
    private void showSelectTreeView() {
        this.window.setContent(new SelectTreePresenter(this).getView());
    }
    public void onTreeChosen(TreeAdapter<Poi> tree) {
        this.model = new PoiModel(tree);

        List<String> fileNames = miscMethods.listCsvFiles();
        if (!fileNames.isEmpty())
            this.showSelectFileView(fileNames);
        else
            this.showMainView();
    }
    private void showSelectFileView(List<String> fileNames) {
        this.window.setContent(new SelectFilePresenter(fileNames, this).getView());
    }
    public void onFileChosen(String fileName) {
        this.miscMethods.loadFileToModel(fileName, this.model);
        this.showMainView();
    }
    public void onNewEvidenceChosen() {
        this.showMainView();
    }
    private void showMainView() {
        this.window.setContent(new MainPresenter(this.model, this).getView());
    }

    public JFrame getFrame() {
        return this.window.getFrame();
    }
}

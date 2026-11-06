package poiApp.presenter;

import poiApp.view.views.SelectFileView;

import javax.swing.*;
import java.util.List;

public class SelectFilePresenter {
    private final SelectFileView view = new SelectFileView();

    public SelectFilePresenter(List<String> files, PoiAppNavigator navigator) {
        view.setFiles(files, navigator::onFileChosen);
        view.getNewButton().addActionListener(e -> navigator.onNewEvidenceChosen());
    }

    public JPanel getView() {
        return this.view.getView();
    }
}

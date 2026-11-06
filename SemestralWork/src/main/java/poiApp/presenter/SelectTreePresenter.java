package poiApp.presenter;

import poiApp.model.adapter.KDTreeAdapter;
import poiApp.model.adapter.QuadKDTreeAdapter;
import poiApp.model.adapter.QuadTreeAdapter;
import poiApp.view.views.SelectTreeView;

import javax.swing.*;

public class SelectTreePresenter {
    private final SelectTreeView view = new SelectTreeView();
    public SelectTreePresenter(PoiAppNavigator navigator) {
        view.getKdButton().addActionListener(e -> navigator.onTreeChosen(new KDTreeAdapter()));
        view.getQuadButton().addActionListener(e -> navigator.onTreeChosen(new QuadTreeAdapter()));
        view.getQuadKDButton().addActionListener(e -> navigator.onTreeChosen(new QuadKDTreeAdapter()));
    }
    public JPanel getView() {
        return this.view.getView();
    }
}

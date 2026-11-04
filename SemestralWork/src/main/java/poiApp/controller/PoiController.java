package poiApp.controller;

import poiApp.model.Poi;
import poiApp.model.PoiModel;
import poiApp.model.adapter.KDTreeAdapter;
import poiApp.model.adapter.TreeAdapter;
import poiApp.view.PoiView;

public class PoiController {
    private final PoiView view;
    private final PoiModel model;
    public PoiController() {
        this.view = new PoiView();
        this.model = new PoiModel(chooseTree());
    }

    public TreeAdapter<Poi> chooseTree() {
        return new KDTreeAdapter();
    }
    public void findPoi() {

    }
    public void findPoi(int polomer) {

    }
    public void createPoi() {

    }

}

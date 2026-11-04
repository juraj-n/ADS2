package poiApp.model;

import poiApp.model.adapter.TreeAdapter;

public class PoiModel {
    private final TreeAdapter<Poi> tree;
    public PoiModel(TreeAdapter<Poi> tree) {
        this.tree = tree;
    }
}

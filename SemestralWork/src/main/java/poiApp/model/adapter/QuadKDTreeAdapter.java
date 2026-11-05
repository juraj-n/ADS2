package poiApp.model.adapter;

import dataStructures.tree.quadKDTree.QuadKDTree;
import poiApp.model.Poi;
import poiApp.model.PoiCoordinate;

import java.util.List;

public class QuadKDTreeAdapter implements TreeAdapter<Poi> {
    private final QuadKDTree<Poi> tree = new QuadKDTree<>(
            new PoiCoordinate(-90.0),
            new PoiCoordinate(90.0),
            new PoiCoordinate(-180.0),
            new PoiCoordinate(180.0),
            10);

    @Override
    public boolean insert(double[] keys, Poi value) {
        return false;
    }
    @Override
    public List<Poi> fint(double[] keys) {
        return null;
    }
    @Override
    public List<Poi> find(double[] minKeys, double[] maxKeys) {
        return null;
    }
}

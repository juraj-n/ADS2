package poiApp.model.adapter;

import dataStructures.tree.kdTree.KDTree;
import poiApp.model.Poi;
import poiApp.model.PoiCoordinate;

import java.util.List;

public class KDTreeAdapter implements TreeAdapter<Poi> {
    private final KDTree<Poi> tree = new KDTree<>(2);

    @Override
    public boolean insert(double[] keys, Poi value) {
        PoiCoordinate[] poiKeys = {new PoiCoordinate(keys[0]), new PoiCoordinate(keys[1])};
        return tree.insert(poiKeys, value);
    }
    @Override
    public List<Poi> find(double[] keys) {
        PoiCoordinate[] poiKeys = {new PoiCoordinate(keys[0]), new PoiCoordinate(keys[1])};
        return tree.find(poiKeys);
    }
    @Override
    public List<Poi> find(double[] minKeys, double[] maxKeys) {
        PoiCoordinate[] minPoiKeys = {new PoiCoordinate(minKeys[0]), new PoiCoordinate(minKeys[1])};
        PoiCoordinate[] maxPoiKeys = {new PoiCoordinate(maxKeys[0]), new PoiCoordinate(maxKeys[1])};

        return tree.find(minPoiKeys, maxPoiKeys);
    }
}

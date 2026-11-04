package poiApp.model.adapter;

import dataStructures.tree.kdTree.KDTree;
import poiApp.model.Poi;

import java.util.List;

public class KDTreeAdapter implements TreeAdapter<Poi> {
    private final KDTree<Poi> tree = new KDTree<>(2);

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

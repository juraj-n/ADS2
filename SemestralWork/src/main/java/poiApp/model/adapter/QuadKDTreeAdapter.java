package poiApp.model.adapter;

import dataStructures.tree.quadKDTree.QuadKDTree;
import poiApp.model.Poi;
import poiApp.model.PoiCoordinate;

import java.util.List;

/**
 * Implementation of TreeAtapert<Poi> using QuadKDTree.
 *
 * @author Juraj Nižnanský
 * */
public class QuadKDTreeAdapter implements TreeAdapter<Poi> {
    private final QuadKDTree<Poi> tree = new QuadKDTree<>(
            new PoiCoordinate(-90.0),
            new PoiCoordinate(90.0),
            new PoiCoordinate(-180.0),
            new PoiCoordinate(180.0),
            10);

    @Override
    public boolean insert(double[] keys, Poi value) {
        PoiCoordinate xPoi = new PoiCoordinate(keys[0]);
        PoiCoordinate yPoi = new PoiCoordinate(keys[1]);

        return tree.insert(xPoi, yPoi, value);
    }
    @Override
    public List<Poi> find(double[] keys) {
        PoiCoordinate xPoi = new PoiCoordinate(keys[0]);
        PoiCoordinate yPoi = new PoiCoordinate(keys[1]);

        return tree.find(xPoi, yPoi);
    }
    @Override
    public List<Poi> find(double[] minKeys, double[] maxKeys) {
        PoiCoordinate xMinPoi = new PoiCoordinate(minKeys[0]);
        PoiCoordinate xMaxPoi = new PoiCoordinate(maxKeys[0]);
        PoiCoordinate yMinPoi = new PoiCoordinate(minKeys[1]);
        PoiCoordinate yMaxPoi = new PoiCoordinate(maxKeys[1]);

        return tree.find(xMinPoi, xMaxPoi, yMinPoi, yMaxPoi);
    }
}

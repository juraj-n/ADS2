package generalRandTest.adapter.s1t2Adapter;

import com.thegeekyasian.geoassist.kdtree.KDTree;
import com.thegeekyasian.geoassist.kdtree.KDTreeObject;
import com.thegeekyasian.geoassist.kdtree.geometry.BoundingBox;
import com.thegeekyasian.geoassist.kdtree.geometry.Point;
import generalRandTest.adapter.TreeAdapter;

import java.util.ArrayList;
import java.util.List;

public class GeoassistKDAdapter<T> implements TreeAdapter<T> {
    private final KDTree<Double, T> tree = new KDTree<>();
    @Override
    public boolean insert(double[] keys, T value) {
        tree.insert(new KDTreeObject.Builder<Double, T>()
                .data(value)
                .latitude(keys[0])
                .longitude(keys[1])
                .build());
        return true;
    }
    @Override
    public List<T> find(double[] minKeys, double[] maxKeys) {
        BoundingBox box = new BoundingBox.Builder()
                .lowerPoint(new Point.Builder()
                        .latitude(minKeys[0])
                        .longitude(minKeys[1])
                        .build())
                .upperPoint(new Point.Builder()
                        .latitude(maxKeys[0])
                        .longitude(maxKeys[1])
                        .build())
                .build();
        List<T> found = new ArrayList<>();
        for (var obj : tree.findInRange(box)) {
            found.add(obj.getData());
        }

        return found;
    }
    @Override
    public void delete(double[] keys) {

    }
}

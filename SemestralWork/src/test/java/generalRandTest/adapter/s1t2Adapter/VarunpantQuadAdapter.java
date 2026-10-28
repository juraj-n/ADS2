package generalRandTest.adapter.s1t2Adapter;

import com.github.varunpant.quadtree.QuadTree;
import generalRandTest.adapter.TreeAdapter;
import generalRandTest.misc.TestConstants;

import java.util.LinkedList;
import java.util.List;

public class VarunpantQuadAdapter<T> implements TreeAdapter<T> {
    QuadTree<T> tree = new QuadTree<>(0, 0, TestConstants.WORLD_SIZE, TestConstants.WORLD_SIZE);
    @Override
    public boolean insert(double[] keys, T value) {
        int sizeBefore = tree.getCount();
        tree.set(keys[0], keys[1], value);

        return sizeBefore < tree.getCount();
    }
    @Override
    public List<T> find(double[] minKeys, double[] maxKeys) {
        var points = tree.searchIntersect(minKeys[0], minKeys[1], maxKeys[0], maxKeys[1]);
        List<T> found = new LinkedList<>();
        for (var point : points) {
            found.add(point.getValue());
        }

        return found;
    }
}

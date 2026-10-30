package generalRandTest.adapter.myAdapter;

import dataStructures.tree.quadKDTree.QuadKDTree;
import generalRandTest.adapter.TreeAdapter;
import generalRandTest.misc.TestConstants;
import generalRandTest.misc.TestDouble;

import java.util.List;

public class QuadKDTreeAdapter<T> implements TreeAdapter<T> {
    private final QuadKDTree<T> tree = new QuadKDTree<>(
            new TestDouble(0),
            new TestDouble(TestConstants.WORLD_SIZE),
            new TestDouble(0),
            new TestDouble(TestConstants.WORLD_SIZE),
            TestConstants.Q_DEPTH
    );
    @Override
    public boolean insert(double[] keys, T value) {
        TestDouble xKey = new TestDouble(keys[0]);
        TestDouble yKey = new TestDouble(keys[1]);

        return tree.insert(xKey, yKey, value);
    }
    @Override
    public List<T> find(double[] minKeys, double[] maxKeys) {
        TestDouble xMin = new TestDouble(minKeys[0]);
        TestDouble xMax = new TestDouble(maxKeys[0]);
        TestDouble yMin = new TestDouble(minKeys[1]);
        TestDouble yMax = new TestDouble(maxKeys[1]);

        return tree.find(xMin, xMax, yMin, yMax);
    }
}

package generalRandTest.adapter.myAdapter;

import dataStructures.tree.kdTree.KDTree;
import generalRandTest.adapter.TreeAdapter;
import generalRandTest.misc.TestConstants;
import generalRandTest.misc.TestDouble;

import java.util.List;

public class KDTreeAdapter<T> implements TreeAdapter<T> {
    private final KDTree<T> tree = new KDTree<>(TestConstants.DIM);
    @Override
    public boolean insert(double[] keys, T value) {
        TestDouble[] kdKeys = new TestDouble[TestConstants.DIM];
        for (int i = 0; i < TestConstants.DIM; i++) {
            kdKeys[i] = new TestDouble(keys[i]);
        }

        return tree.insert(kdKeys, value);
    }
    @Override
    public List<T> find(double[] minKeys, double[] maxKeys) {
        TestDouble[] kdMinKeys = new TestDouble[TestConstants.DIM];
        TestDouble[] kdMaxKeys = new TestDouble[TestConstants.DIM];
        for (int i = 0; i < TestConstants.DIM; i++) {
            kdMinKeys[i] = new TestDouble(minKeys[i]);
            kdMaxKeys[i] = new TestDouble(maxKeys[i]);
        }

        return tree.find(kdMinKeys, kdMaxKeys);
    }
}

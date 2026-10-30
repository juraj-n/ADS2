package generalRandTest.adapter.s1t2Adapter;

import edu.wlu.cs.levy.cg.KDTree;
import generalRandTest.adapter.TreeAdapter;
import generalRandTest.misc.TestConstants;

import java.util.LinkedList;
import java.util.List;

public class OschrenkKDAdapter<T> implements TreeAdapter<T> {
    private final KDTree<T> tree = new KDTree<>(TestConstants.DIM);
    @Override
    public boolean insert(double[] keys, T value) {
        try {
            tree.insert(keys, value);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }
    @Override
    public List<T> find(double[] minKeys, double[] maxKeys) {
        try {
            return tree.range(minKeys, maxKeys);
        } catch (Exception ex) {
            return new LinkedList<>();
        }
    }
}

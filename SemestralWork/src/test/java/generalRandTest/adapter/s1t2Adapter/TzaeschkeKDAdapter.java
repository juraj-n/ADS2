package generalRandTest.adapter.s1t2Adapter;

import generalRandTest.adapter.TreeAdapter;
import org.tinspin.index.kdtree.KDTree;

import java.util.LinkedList;
import java.util.List;

public class TzaeschkeKDAdapter<T> implements TreeAdapter<T> {
    private final KDTree<T> tree = KDTree.create(2);

    @Override
    public boolean insert(double[] keys, T value) {
        int sizeBefore = tree.size();
        tree.insert(keys, value);

        return tree.size() > sizeBefore;
    }

    @Override
    public List<T> find(double[] minKeys, double[] maxKeys) {
        List<T> found = new LinkedList<>();
        var iterator = tree.query(minKeys, maxKeys);
        while (iterator.hasNext()) {
            found.add(iterator.next().value());
        }

        return found;
    }
}

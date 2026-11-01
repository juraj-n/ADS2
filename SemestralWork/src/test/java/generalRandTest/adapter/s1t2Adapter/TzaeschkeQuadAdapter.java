package generalRandTest.adapter.s1t2Adapter;

import generalRandTest.adapter.TreeAdapter;
import org.tinspin.index.qtplain.QuadTreeKD0;

import java.util.ArrayList;
import java.util.List;

public class TzaeschkeQuadAdapter<T> implements TreeAdapter<T> {
    private final QuadTreeKD0<T> tree = QuadTreeKD0.create(2);

    @Override
    public boolean insert(double[] keys, T value) {
        tree.insert(keys, value);
        return true;
    }
    @Override
    public List<T> find(double[] minKeys, double[] maxKeys) {
        List<T> found = new ArrayList<>();
        var iterator = tree.query(minKeys, maxKeys);
        while (iterator.hasNext())
            found.add(iterator.next().value());

        return found;
    }
    @Override
    public void delete(double[] keys) {
        tree.remove(keys);
    }
}

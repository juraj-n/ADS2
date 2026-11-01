package s1t2.otherTrees.qt;

import org.tinspin.index.qtplain.QuadTreeKD0;
import s1t2.loadData.SvkNode;
import s1t2.loadData.SvkNodesArea;

import java.util.ArrayList;
import java.util.List;

public class TesterTzaeschkeQT {
    private static final QuadTreeKD0<String> tree = QuadTreeKD0.create(2);

    public static void insertNodes(List<SvkNode> nodes) {
        for (var node : nodes) {
            double[] keys = {node.getLatitude(), node.getLongitude()};
            tree.insert(keys, node.getId());
        }
    }
    public static void findAreas(List<SvkNodesArea> areas) {
        for (var area : areas) {
            List<String> found = new ArrayList<>();
            double[] minKeys = {area.getMinLat(), area.getMinLon()};
            double[] maxKeys = {area.getMaxLat(), area.getMaxLon()};
            var iterator = tree.query(minKeys, maxKeys);
            while (iterator.hasNext()) {
                found.add(iterator.next().value());
            }
        }
    }
}

package s1t2.myTrees;

import dataStructures.tree.quadKDTree.QuadKDTree;
import s1t2.loadData.SvkDouble;
import s1t2.loadData.SvkNode;
import s1t2.loadData.SvkNodesArea;

import java.util.List;

public class TesterQKDT {
    private static final double MIN_LAT = 47.0;
    private static final double MAX_LAT = 50.0;
    private static final double MIN_LON = 16.0;
    private static final double MAX_LON = 22.6;
    private static final QuadKDTree<String> tree = new QuadKDTree<>(
            new SvkDouble(MIN_LAT),
            new SvkDouble(MAX_LAT),
            new SvkDouble(MIN_LON),
            new SvkDouble(MAX_LON),
            10);

    public static void insertNodes(List<SvkNode> nodes) {
        for (var node : nodes) {
            tree.insert(
                    new SvkDouble(node.getLatitude()), new SvkDouble(node.getLongitude()),
                    node.getId());
        }
    }
    public static void findAreas(List<SvkNodesArea> areas) {
        for (var area : areas) {
            tree.find(
                    new SvkDouble(area.getMinLat()),
                    new SvkDouble(area.getMaxLat()),
                    new SvkDouble(area.getMinLon()),
                    new SvkDouble(area.getMaxLon()));
        }
    }
}

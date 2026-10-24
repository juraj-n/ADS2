package s1t2.myTrees;

import dataStructures.common.Comparable;
import dataStructures.tree.kdTree.KDTree;
import s1t2.loadData.SvkDouble;
import s1t2.loadData.SvkNode;
import s1t2.loadData.SvkNodesArea;

import java.util.List;

public class TesterKDT {
    private static final KDTree<String> tree = new KDTree<>(2);

    public static void insertNodes(List<SvkNode> nodes) {
        for (var node : nodes) {
            tree.insert(
                    new Comparable[] {new SvkDouble(node.getLatitude()), new SvkDouble(node.getLongitude())},
                    node.getId());
        }
    }
    public static void findAreas(List<SvkNodesArea> areas) {
        for (var area : areas) {
            tree.find(
                    new Comparable[] {new SvkDouble(area.getMinLat()), new SvkDouble(area.getMinLon())},
                    new Comparable[] {new SvkDouble(area.getMaxLat()), new SvkDouble(area.getMaxLon())});
        }
    }

}

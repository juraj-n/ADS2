package s1t2.otherTrees.qt;

import com.github.varunpant.quadtree.QuadTree;
import s1t2.loadData.SvkNode;
import s1t2.loadData.SvkNodesArea;

import java.util.List;

public class TesterVarunpantQT {
    private static final double MIN_LAT = 47.0;
    private static final double MAX_LAT = 50.0;
    private static final double MIN_LON = 16.0;
    private static final double MAX_LON = 22.6;
    private static final QuadTree<String> tree = new QuadTree<>(
            MIN_LAT, MIN_LON, MAX_LAT, MAX_LON);
    public static void insertNodes(List<SvkNode> nodes) {
        for (var node : nodes) {
            tree.set(node.getLatitude(), node.getLongitude(), node.getId());
        }
    }
    public static void findAreas(List<SvkNodesArea> areas) {
        for (var area : areas) {
            tree.searchWithin(area.getMinLat(), area.getMinLon(), area.getMaxLat(), area.getMaxLon());
        }
    }
}

package s1t2.otherTrees.qt;

import io.metaloom.quadtree.Size;
import io.metaloom.quadtree.point.Point;
import io.metaloom.quadtree.point.PointNodeElement;
import io.metaloom.quadtree.point.PointQuadTree;
import io.metaloom.quadtree.point.impl.PointImpl;
import io.metaloom.quadtree.point.impl.PointQuadTreeImpl;
import s1t2.loadData.SvkNode;
import s1t2.loadData.SvkNodesArea;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TesterJotschiQT {
    private static final long MIN_LAT = 47L* 1_000_000_000;
    private static final long MAX_LAT = 50L* 1_000_000_000;
    private static final long MIN_LON = 16L* 1_000_000_000;
    private static final long MAX_LON = (long)(22.6 * 1_000_000_000);
    private static PointQuadTree<String> tree = new PointQuadTreeImpl<>(
            Point.of(MIN_LAT, MIN_LON),
            Size.of(MAX_LAT, MAX_LON)
    );

    public static void insertNodes(List<SvkNode> nodes) {
        for (var node : nodes) {
            tree.insert(new PointImpl(
                    (long)(node.getLatitude() * 1_000_000_000),
                    (long)(node.getLongitude() * 1_000_000_000)),
                    node.getId());
        }
    }
    public static void additionalTest(List<SvkNode> nodes) {
        ArrayList<PointNodeElement<String>> found = new ArrayList<>();
        for (var node : nodes) {
            Vector<PointNodeElement<String>> e = tree.getElements(new PointImpl(
                    (long)(node.getLatitude() * 1_000_000_000),
                    (long)(node.getLongitude() * 1_000_000_000)
            ));
            found.add(e.get(1));
        }

        if (found.size() != nodes.size())
            System.out.println("Found do not match expected size");
    }
}

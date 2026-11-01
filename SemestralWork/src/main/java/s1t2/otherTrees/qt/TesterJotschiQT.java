package s1t2.otherTrees.qt;

import io.metaloom.quadtree.Size;
import io.metaloom.quadtree.point.Point;
import io.metaloom.quadtree.point.PointNodeElement;
import io.metaloom.quadtree.point.PointQuadTree;
import io.metaloom.quadtree.point.impl.PointImpl;
import io.metaloom.quadtree.point.impl.PointQuadTreeImpl;
import s1t2.loadData.SvkNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TesterJotschiQT {
    private static final long MIN_LAT = 47L* 1_000_000_000;
    private static final long MAX_LAT = 50L* 1_000_000_000;
    private static final long MIN_LON = 16L* 1_000_000_000;
    private static final long MAX_LON = (long)(22.6 * 1_000_000_000);

    public static void insertNodes(List<SvkNode> nodes) {
        PointQuadTree<String> tree = new PointQuadTreeImpl<>(
                Point.of(MIN_LAT, MIN_LON),
                Size.of(MAX_LAT - MIN_LAT, MAX_LON - MIN_LON)
        );
        for (var node : nodes) {
            tree.insert(new PointImpl(
                    (long)(node.getLatitude() * 1_000_000_000),
                    (long)(node.getLongitude() * 1_000_000_000)),
                    node.getId());
        }
    }
    public static void additionalTest(List<SvkNode> nodes) {
        findAllNodesTest(nodes);
        duplicatesTest(nodes);
    }

    private static void findAllNodesTest(List<SvkNode> nodes) {
        PointQuadTree<String> tree = new PointQuadTreeImpl<>(
                Point.of(MIN_LAT, MIN_LON),
                Size.of(MAX_LAT - MIN_LAT, MAX_LON - MIN_LON)
        );
        // Fill tree
        for (var node : nodes) {
            tree.insert(new PointImpl(
                            (long)(node.getLatitude() * 1_000_000_000),
                            (long)(node.getLongitude() * 1_000_000_000)),
                    node.getId());
        }
        // Find all elements
        ArrayList<PointNodeElement<String>> found = new ArrayList<>();
        for (var node : nodes) {
            Vector<PointNodeElement<String>> e = tree.getElements(new PointImpl(
                    (long)(node.getLatitude() * 1_000_000_000),
                    (long)(node.getLongitude() * 1_000_000_000)
            ));
            found.add(e.getFirst());
        }
        // Check if all inserted elements were found
        if (found.size() != nodes.size())
            System.out.println("Found does not match expected size!");
    }
    private static void duplicatesTest(List<SvkNode> nodes) {
        PointQuadTree<String> tree = new PointQuadTreeImpl<>(
                Point.of(MIN_LAT, MIN_LON),
                Size.of(MAX_LAT - MIN_LAT, MAX_LON - MIN_LON)
        );

        SvkNode a = nodes.getFirst();
        SvkNode b = nodes.get(10);

        PointImpl pointA = new PointImpl(
                (long) (a.getLatitude() * 1_000_000_000),
                (long) (a.getLongitude() * 1_000_000_000));
        PointImpl pointB = new PointImpl(
                (long) (b.getLatitude() * 1_000_000_000),
                (long) (b.getLongitude() * 1_000_000_000));

        // Insert 5 & 5 duplicates
        for (int i = 0; i < 5; i++) {
            tree.insert(pointA, a.getId());
            tree.insert(pointB, b.getId());
        }
        // Find duplicates
        Vector<PointNodeElement<String>> found = tree.getElements(pointA);
        for (var f : found) {
            System.out.println(f.getElement());
        }

        // Check if 5 duplicates were found
        if (found.size() != 5)
            System.out.println("Duplicates: Found does not match expected size!");
    }
}

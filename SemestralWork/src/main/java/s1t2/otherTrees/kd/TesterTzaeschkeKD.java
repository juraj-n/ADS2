package s1t2.otherTrees.kd;

import edu.wlu.cs.levy.cg.KeySizeException;
import org.tinspin.index.kdtree.KDTree;
import s1t2.loadData.SvkNode;
import s1t2.loadData.SvkNodesArea;

import java.util.ArrayList;
import java.util.List;

public class TesterTzaeschkeKD {
    private static final KDTree<String> tree = KDTree.create(2);

    public static void insertNodes(List<SvkNode> nodes) {
        for (var node : nodes) {
            try {
                tree.insert(new double[] { node.getLatitude(), node.getLongitude() }, node.getId());
            } catch (Exception ex) {
                System.out.println("[ERROR] " + ex.getMessage());
            }
        }
    }
    public static void findAreas(List<SvkNodesArea> areas) {
        for (var area : areas) {
            List<String> found = new ArrayList<>();
            var iterator = tree.query(new double[] {area.getMinLat(), area.getMinLon()}, new double[] {area.getMaxLat(), area.getMaxLon()});
            while (iterator.hasNext()) {
                found.add(iterator.next().value());
            }
        }
    }
}

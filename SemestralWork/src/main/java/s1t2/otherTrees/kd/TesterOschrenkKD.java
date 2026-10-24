package s1t2.otherTrees.kd;

import edu.wlu.cs.levy.cg.KDTree;
import edu.wlu.cs.levy.cg.KeySizeException;
import s1t2.loadData.SvkNode;
import s1t2.loadData.SvkNodesArea;

import java.util.List;

public class TesterOschrenkKD {
    private static final KDTree<String> tree = new KDTree<>(2);

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
            try {
                tree.range(new double[] {area.getMinLat(), area.getMaxLat()}, new double[] {area.getMaxLat(), area.getMaxLon()});
            } catch (KeySizeException ksex) {
                System.out.println("[ERROR] " + ksex.getMessage());
            }
        }
    }
}

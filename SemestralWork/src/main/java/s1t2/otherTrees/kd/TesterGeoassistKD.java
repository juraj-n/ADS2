package s1t2.otherTrees.kd;

import com.thegeekyasian.geoassist.kdtree.KDTree;
import com.thegeekyasian.geoassist.kdtree.KDTreeObject;
import com.thegeekyasian.geoassist.kdtree.geometry.BoundingBox;
import com.thegeekyasian.geoassist.kdtree.geometry.Point;
import s1t2.loadData.SvkNode;
import s1t2.loadData.SvkNodesArea;

import java.util.List;

public class TesterGeoassistKD {
    private static final KDTree<Double, String> tree = new KDTree<>();
    public static void insertNodes(List<SvkNode> nodes) {
        for (var node : nodes) {
            tree.insert(new KDTreeObject.Builder<Double, String>()
                            .data(node.getId())
                            .latitude(node.getLatitude())
                            .longitude(node.getLongitude())
                            .build());
        }
    }
    public static void findAreas(List<SvkNodesArea> areas) {
        for (var area : areas) {
            BoundingBox box = new BoundingBox.Builder()
                    .lowerPoint(new Point.Builder()
                            .latitude(area.getMinLat())
                            .longitude(area.getMinLon())
                            .build())
                    .upperPoint(new Point.Builder()
                            .latitude(area.getMaxLat())
                            .longitude(area.getMaxLon())
                            .build())
                    .build();
            List<KDTreeObject<Double, String>> found = tree.findInRange(box);
        }
    }
}

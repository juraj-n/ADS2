package zadanie2;

import dataStructures.common.Comparable;
import dataStructures.tree.kdTree.KDTree;
import dataStructures.tree.quadKDTree.QuadKDTree;
import dataStructures.tree.quadTree.QuadTree;
import edu.wlu.cs.levy.cg.KeySizeException;

import java.util.List;

public class StructuresTestWithData {
    public static void main(String[] args) throws Exception {
        var nodes = SvkNodeReader.loadSvkNodes("data/S1/slovakia_nodes.csv");
        var areas = SvkNodeReader.loadSvkNodesAreas("data/S1/testovacie_oblasti.csv");

        // Other QuadTrees
        var varunpantQuad = varunpantQuadInsertNodes(nodes);
        varunpantQuadFindAreas(varunpantQuad, areas);

        //
        //

        //
        //

        // Other KDTrees
        var oschrenkKD = oschrenkKDInsertNodes(nodes);
        oschrenkKDFindAreas(oschrenkKD, areas);

        //
        //

        //
        //

        // My QuadTree
        var quadTree = quadTreeInsertNodes(nodes);
        quadTreeFindAreas(quadTree, areas);

        // My KDTree
        var kdTree = kDTreeInsertNodes(nodes);
        kDTreeFindAreas(kdTree, areas);

        // My QuadKDTree
        var quadKDTree = quadKDTreeIsertNodes(nodes);
        quadKDTreeFindAreas(quadKDTree, areas);
    }

    private static com.github.varunpant.quadtree.QuadTree<String> varunpantQuadInsertNodes(List<SvkNode> nodes) {
        com.github.varunpant.quadtree.QuadTree<String> tree = new com.github.varunpant.quadtree.QuadTree<>(
                47.0, 16.0, 50.0, 22.6);

        for (var node : nodes) {
            tree.set(node.getLatitude(), node.getLongitude(), node.getId());
        }

        return tree;
    }
    private static void varunpantQuadFindAreas(com.github.varunpant.quadtree.QuadTree<String> tree, List<SvkNodesArea> areas) {
        for (var area : areas) {
            tree.searchWithin(area.getMinLat(), area.getMinLon(), area.getMaxLat(), area.getMaxLon());
        }
    }
    private static edu.wlu.cs.levy.cg.KDTree<String> oschrenkKDInsertNodes(List<SvkNode> nodes) throws Exception{
        edu.wlu.cs.levy.cg.KDTree<String> tree = new edu.wlu.cs.levy.cg.KDTree<>(2);

        for (var node : nodes) {
            tree.insert(new double[] { node.getLatitude(), node.getLongitude() }, node.getId());
        }

        return tree;
    }
    private static void oschrenkKDFindAreas(edu.wlu.cs.levy.cg.KDTree<String> tree, List<SvkNodesArea> areas) {
        for (var area : areas) {
            try {
                tree.range(new double[] {area.getMinLat(), area.getMaxLat()}, new double[] {area.getMaxLat(), area.getMaxLon()});
            } catch (KeySizeException ksex) {
                System.out.println("Exception caught: " + ksex.getMessage());
            }
        }
    }
    private static QuadTree<String> quadTreeInsertNodes(List<SvkNode> nodes) {
        QuadTree<String> tree = new QuadTree<>(
                new SvkDouble(47.0),
                new SvkDouble(50.0),
                new SvkDouble(16.0),
                new SvkDouble(22.6),
                10);

        for (var node : nodes) {
            tree.insert(new SvkDouble(node.getLatitude()), new SvkDouble(node.getLongitude()), node.getId());
        }

        return tree;
    }
    private static void quadTreeFindAreas(QuadTree<String> tree, List<SvkNodesArea> areas) {
        for (var area : areas) {
            tree.find(new SvkDouble(area.getMinLat()), new SvkDouble(area.getMaxLat()), new SvkDouble(area.getMinLon()), new SvkDouble(area.getMaxLon()));
        }

    }
    private static KDTree<String> kDTreeInsertNodes(List<SvkNode> nodes) {
        KDTree<String> kdTree = new KDTree<>(2);

        for (var node : nodes) {
            kdTree.insert(new Comparable[] {new SvkDouble(node.getLatitude()), new SvkDouble(node.getLongitude())}, node.getId());
        }

        return kdTree;
    }
    private static void kDTreeFindAreas(KDTree<String> tree, List<SvkNodesArea> areas) {
        for (var area : areas) {
            tree.find(new Comparable[] {new SvkDouble(area.getMinLat()), new SvkDouble(area.getMinLon())},
                    new Comparable[] {new SvkDouble(area.getMaxLat()), new SvkDouble(area.getMaxLon())});
        }
    }
    private static QuadKDTree<String> quadKDTreeIsertNodes(List<SvkNode> nodes) {
        QuadKDTree<String> tree = new QuadKDTree<>(
                new SvkDouble(47.0),
                new SvkDouble(50.0),
                new SvkDouble(47.0),
                new SvkDouble(50.0),
                10);

        for (var node : nodes) {
            tree.insert(new SvkDouble(node.getLatitude()), new SvkDouble(node.getLongitude()), node.getId());
        }

        return tree;
    }
    private static void quadKDTreeFindAreas(QuadKDTree<String> tree, List<SvkNodesArea> areas) {
        for (var area : areas) {
            tree.find(new SvkDouble(area.getMinLat()), new SvkDouble(area.getMaxLat()), new SvkDouble(area.getMinLon()), new SvkDouble(area.getMaxLon()));
        }
    }
}

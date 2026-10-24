package s1t2;

import s1t2.loadData.SvkNodeReader;
import s1t2.myTrees.TesterKDT;
import s1t2.myTrees.TesterQKDT;
import s1t2.myTrees.TesterQT;
import s1t2.otherTrees.kd.TesterOschrenkKD;
import s1t2.otherTrees.qt.TesterVarunpantQT;

public class StructuresTestWithData {
    public static void main(String[] args) {
        var nodes = SvkNodeReader.loadSvkNodes("data/S1/slovakia_nodes.csv");
        var areas = SvkNodeReader.loadSvkNodesAreas("data/S1/testovacie_oblasti.csv");

        // Other QuadTrees
        TesterVarunpantQT.insertNodes(nodes);
        TesterVarunpantQT.findAreas(areas);

        //
        //

        //
        //

        // Other KDTrees
        TesterOschrenkKD.insertNodes(nodes);
        TesterOschrenkKD.findAreas(areas);

        //
        //

        //
        //

        // My QuadTree
        TesterQT.insertNodes(nodes);
        TesterQT.findAreas(areas);

        // My KDTree
        TesterKDT.insertNodes(nodes);
        TesterKDT.findAreas(areas);

        // My QuadKDTree
        TesterQKDT.insertNodes(nodes);
        TesterQKDT.findAreas(areas);
    }
}

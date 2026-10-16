import dataStructures.tree.quadTree.QuadTree;
import temp.Double;
import temp.Integer;

public class Main {
    public static void main(String[] args) {
        Integer xMin = new Integer(0);
        Integer xMax = new Integer(100);
        Double yMin = new Double(0.0);
        Double yMax = new Double(100.0);

        QuadTree<String> quadTree = new QuadTree<>(xMin, xMax, yMin, yMax, 100);

        Integer xKey1 = new Integer(25);
        Double yKey1 = new Double(55.0);
        quadTree.insert(xKey1, yKey1, "X_1");

        Integer xKey2 = new Integer(10);
        Double yKey2 = new Double(40.0);
        quadTree.insert(xKey2, yKey2, "X_2");

        Integer xKey3 = new Integer(30);
        Double yKey3 = new Double(40.0);
        quadTree.insert(xKey3, yKey3, "X_3");

        var found = quadTree.find(new Integer(15), new Integer(40), new Double(30.0), new Double(60.0));
        for (var f : found) {
            System.out.println(f);
        }
        found = quadTree.find(new Integer(10), new Double(40));
        System.out.println(found.getFirst());

    }
}

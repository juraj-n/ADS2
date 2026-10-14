import dataStructures.tree.quadTree.QuadTree;
import temp.Double;
import temp.Integer;

public class Main {
    public static void main(String[] args) {
        Integer xMin = new Integer(0);
        Integer xMax = new Integer(100);
        Double yMin = new Double(0.0);
        Double yMax = new Double(100);

        QuadTree<String> quadTree = new QuadTree<>(xMin, xMax, yMin, yMax, 10);

        Integer xKey1 = new Integer(15);
        Double yKey1 = new Double(15.0);
        quadTree.insert(xKey1, yKey1, "First");

        Integer xKey2 = new Integer(75);
        Double yKey2 = new Double(15.0);
        quadTree.insert(xKey2, yKey2, "Second");
    }
}

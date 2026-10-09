import common.Comparable;
import common.Test;
import tree.kdTree.KDTree;

public class Main {
    public static void main(String[] args) {
        KDTree<String> kdTree = new KDTree<>(2);

        Comparable[] keys = new Comparable[2];
        keys[0] = new Test(100);
        keys[1] = new Test(99);

        Comparable[] keys2 = new Comparable[2];
        keys2[0] = new Test(10);
        keys2[1] = new Test(9);

        Comparable[] keys3 = new Comparable[2];
        keys3[0] = new Test(1);
        keys3[1] = new Test(999);

        kdTree.insert(keys, "1.Item");
        kdTree.insert(keys2, "2.Item");
        kdTree.insert(keys3, "3.Item");
    }
}

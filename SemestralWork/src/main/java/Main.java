import common.Comparable;
import common.Test;
import common.TestDouble;
import tree.kdTree.KDNode;
import tree.kdTree.KDTree;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        KDTree<String> kdTree = new KDTree<>(2);

        Comparable[] keys = new Comparable[2];
        keys[0] = new Test(11);
        keys[1] = new TestDouble(10.0);

        Comparable[] keys2 = new Comparable[2];
        keys2[0] = new Test(5);
        keys2[1] = new TestDouble(12.0);

        Comparable[] keys3 = new Comparable[2];
        keys3[0] = new Test(10);
        keys3[1] = new TestDouble(15.0);

        Comparable[] keys4 = new Comparable[2];
        keys4[0] = new Test(9);
        keys4[1] = new TestDouble(5.0);

        Comparable[] keys5 = new Comparable[2];
        keys5[0] = new Test(15);
        keys5[1] = new TestDouble(10.0);

        kdTree.insert(keys, "D");
        kdTree.insert(keys2, "B");
        kdTree.insert(keys3, "C");
        kdTree.insert(keys4, "A");
        kdTree.insert(keys4, "A_Second");
        kdTree.insert(keys5, "E");

        Comparable[] minKeys = new Comparable[2];
        minKeys[0] = new Test(5);
        minKeys[1] = new TestDouble(5.0);

        Comparable[] maxKeys = new Comparable[2];
        maxKeys[0] = new Test(15);
        maxKeys[1] = new TestDouble(15.0);

        LinkedList<KDNode<String>> found = kdTree.find(minKeys, maxKeys);
        LinkedList<KDNode<String>> one = kdTree.find(keys4);
        for (var node : found) {
            System.out.println("Najdeny node: " + node.getData());
        }
        for (var node : one) {
            System.out.println("Samostatny find: " + node.getData());
        }
    }
}

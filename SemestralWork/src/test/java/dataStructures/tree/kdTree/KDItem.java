package dataStructures.tree.kdTree;

public class KDItem {
    public final KDDouble[] keys;
    public final KDDouble data;
    KDItem(KDDouble[] keys, KDDouble data) {
        this.keys = keys;
        this.data = data;
    }
}

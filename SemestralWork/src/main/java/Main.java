import edu.wlu.cs.levy.cg.KDTree;

public class Main {
    public static void main(String[] args) throws Exception {
        KDTree<String> tree = new KDTree<>(2);

        tree.insert(new double[]{2, 3}, "A");
        tree.insert(new double[]{5, 4}, "B");
        tree.insert(new double[]{9, 6}, "C");
        tree.insert(new double[]{4, 7}, "D");

        // najbližší sused k bodu (5, 5)
        String nearest = tree.nearest(new double[]{5, 5});
        System.out.println("Najbližší: " + nearest);
    }
}

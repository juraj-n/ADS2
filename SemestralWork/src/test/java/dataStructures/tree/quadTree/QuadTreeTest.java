package dataStructures.tree.quadTree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class QuadTreeTest {
    private final long seed = System.nanoTime();
    private final Random generator = new Random(seed);
    private final int NUMBER_OF_OPERATIONS
            //= 10_000;
            //= 20_000;
            = 50_000;
            //= 100_000;
            //=250_000;
            //= 500_000;
            //= 1_000_000;
    @Test
    public void testInsertFind() {
        int insertCount = 0;
        int findCount = 0;

        QuadTree<QuadItem<QuadDouble>> tree = new QuadTree<>(new QuadDouble(0),
                new QuadDouble(100),
                new QuadDouble(0),
                new QuadDouble(100),
                100);
        List<QuadItem<QuadDouble>> insItems = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_OPERATIONS; i++) {
            if (generator.nextDouble(100) < 50) {
                insertCount++;
                this.insertRandomKeys(tree, insItems);
            } else {
                findCount++;
                this.findRandomRange(tree, insItems);
            }
        }
        System.out.println("[INSERT] " + insertCount);
        System.out.println("[-FIND-] " + findCount);
    }
    @Test
    public void testTreeContainsInserted() {
        QuadTree<QuadItem<QuadDouble>> tree = new QuadTree<>(new QuadDouble(0),
                new QuadDouble(100),
                new QuadDouble(0),
                new QuadDouble(100),
                100);
        List<QuadItem<QuadDouble>> insItems = new ArrayList<>();
        this.insertRandomKeys(tree, insItems);

        LinkedList<QuadItem<QuadDouble>> found = tree.find(new QuadDouble(0),
                new QuadDouble(100),
                new QuadDouble(0),
                new QuadDouble(100));

        assertEquals(found.size(), insItems.size(), "Seed: " + this.seed);
        assertTrue(found.containsAll(insItems), "Seed " + this.seed);
    }
    private void insertRandomKeys(QuadTree<QuadItem<QuadDouble>> tree, List<QuadItem<QuadDouble>> insItems) {
        QuadDouble xKey = new QuadDouble(generator.nextDouble(100));
        QuadDouble yKey = new QuadDouble(generator.nextDouble(100));
        QuadDouble data = new QuadDouble(generator.nextDouble());
        QuadItem<QuadDouble> item = new QuadItem<>(xKey, yKey, data);

        if (tree.insert(xKey, yKey, item)) {
            insItems.add(item);
        }
    }
    private void findRandomRange(QuadTree<QuadItem<QuadDouble>> tree, List<QuadItem<QuadDouble>> insItems) {
        QuadDouble xMin = new QuadDouble(generator.nextDouble(50));
        QuadDouble xMax = new QuadDouble(generator.nextDouble(50) + 50);
        QuadDouble yMin = new QuadDouble(generator.nextDouble(50));
        QuadDouble yMax = new QuadDouble(generator.nextDouble(50) + 50);

        LinkedList<QuadItem<QuadDouble>> found = tree.find(xMin, xMax, yMin, yMax);
        LinkedList<QuadItem<QuadDouble>> expected = insItems.stream()
                .filter(item ->
                            item.xKey().compare(xMin) >= 0
                        &&  item.xKey().compare(xMax) <= 0
                        &&  item.yKey().compare(yMin) >= 0
                        &&  item.yKey().compare(yMax) <= 0)
                .collect(Collectors.toCollection(LinkedList::new));

        assertEquals(found.size(), expected.size(), "Seed: " + this.seed);
        assertTrue(found.containsAll(expected), "Seed: " + this.seed);
    }
}
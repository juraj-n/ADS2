package dataStructures.tree.quadTree;

import org.junit.jupiter.api.BeforeEach;
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
    private final int WORLD_SIZE = 100;
    private final int FIND_PERC = 1;
    private final int NUMBER_OF_OPERATIONS = 100_000;
    @BeforeEach
    public void consolePrint() {
        System.out.println("************************");
        System.out.println(" [TEST] QuadTree [TEST] ");
        System.out.println("************************");
        System.out.println("SEED:. . " + this.seed + "L");
        System.out.println("OPERATION COUNT: " + this.NUMBER_OF_OPERATIONS);
        System.out.println("WORLD SIZE:. . . . ." + this.WORLD_SIZE);
        System.out.println("FIND [%]:. . . . . . " + this.FIND_PERC + "%");
        System.out.println("************************");
    }
    @Test
    public void testInsertFind() {
        int insertCount = 0;
        int findCount = 0;

        QuadTree<QuadItem<QuadDouble>> tree = new QuadTree<>(new QuadDouble(0),
                new QuadDouble(this.WORLD_SIZE),
                new QuadDouble(0),
                new QuadDouble(this.WORLD_SIZE),
                10);
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
        System.out.println("[INSERT] . . . . . " + insertCount);
        System.out.println("[ FIND ] . . . . . " + findCount);
    }
    @Test
    public void testTreeContainsInserted() {
        QuadTree<QuadItem<QuadDouble>> tree = new QuadTree<>(new QuadDouble(0),
                new QuadDouble(this.WORLD_SIZE),
                new QuadDouble(0),
                new QuadDouble(this.WORLD_SIZE),
                10);
        List<QuadItem<QuadDouble>> insItems = new ArrayList<>();
        for (int i = 0; i < this.NUMBER_OF_OPERATIONS; i++) {
            this.insertRandomKeys(tree, insItems);
        }

        LinkedList<QuadItem<QuadDouble>> found = tree.find(new QuadDouble(0),
                new QuadDouble(this.WORLD_SIZE),
                new QuadDouble(0),
                new QuadDouble(this.WORLD_SIZE));

        assertEquals(found.size(), insItems.size());
        assertTrue(found.containsAll(insItems));
    }
    private void insertRandomKeys(QuadTree<QuadItem<QuadDouble>> tree, List<QuadItem<QuadDouble>> insItems) {
        QuadDouble xKey = new QuadDouble(generator.nextDouble(this.WORLD_SIZE));
        QuadDouble yKey = new QuadDouble(generator.nextDouble(this.WORLD_SIZE));
        QuadDouble data = new QuadDouble(generator.nextDouble());
        QuadItem<QuadDouble> item = new QuadItem<>(xKey, yKey, data);

        if (tree.insert(xKey, yKey, item)) {
            insItems.add(item);
        }
    }
    private void findRandomRange(QuadTree<QuadItem<QuadDouble>> tree, List<QuadItem<QuadDouble>> insItems) {
        double side = Math.sqrt(this.FIND_PERC / 100.0) * this.WORLD_SIZE;

        QuadDouble xMin = new QuadDouble(generator.nextDouble() * (this.WORLD_SIZE - side));
        QuadDouble xMax = new QuadDouble(xMin.value + side);
        QuadDouble yMin = new QuadDouble(generator.nextDouble() * (this.WORLD_SIZE - side));
        QuadDouble yMax = new QuadDouble(yMin.value + side);

        LinkedList<QuadItem<QuadDouble>> found = tree.find(xMin, xMax, yMin, yMax);

        if (this.FIND_PERC <= 5) {
            LinkedList<QuadItem<QuadDouble>> expected = insItems.stream()
                    .filter(item ->
                            item.xKey().compare(xMin) >= 0
                                    &&  item.xKey().compare(xMax) <= 0
                                    &&  item.yKey().compare(yMin) >= 0
                                    &&  item.yKey().compare(yMax) <= 0)
                    .collect(Collectors.toCollection(LinkedList::new));

            assertEquals(expected.size(), found.size());
            assertTrue(found.containsAll(expected));
        }
    }
}
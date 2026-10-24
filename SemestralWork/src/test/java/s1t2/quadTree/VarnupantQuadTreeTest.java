package s1t2.quadTree;

import com.github.varunpant.quadtree.Point;
import com.github.varunpant.quadtree.QuadTree;
import dataStructures.tree.TestConstatns;
import dataStructures.tree.quadTree.QuadDouble;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VarnupantQuadTreeTest extends TestConstatns {
    private final long seed = System.nanoTime();
    private final Random generator = new Random(seed);
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
    public void testInsertFindDelete() {
        int insertCount = 0;
        int findCount = 0;

        QuadTree<VarnupantItem> tree = new QuadTree<>(0.0, 0.0, this.WORLD_SIZE, this.WORLD_SIZE);
        List<VarnupantItem> insItems = new ArrayList<>();
        for (int i = 0; i < this.NUMBER_OF_OPERATIONS; i++) {
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
        QuadTree<VarnupantItem> tree = new QuadTree<>(0, 0, this.WORLD_SIZE, this.WORLD_SIZE);
        ArrayList<VarnupantItem> insItems = new ArrayList<>(this.NUMBER_OF_OPERATIONS);
        for (int i = 0; i < this.NUMBER_OF_OPERATIONS; i++) {
            this.insertRandomKeys(tree, insItems);
        }

        Point<VarnupantItem>[] found = tree.searchWithin(0, 0, this.WORLD_SIZE, this.WORLD_SIZE);
        ArrayList<VarnupantItem> foundData = new ArrayList<>(found.length);
        for (var point : found) {
            foundData.add(point.getValue());
        }

        assertEquals(foundData.size(), insItems.size());
        assertTrue(foundData.containsAll(insItems));
    }

    private void insertRandomKeys(QuadTree<VarnupantItem> tree, List<VarnupantItem> insItems) {
        double xKey = generator.nextDouble(this.WORLD_SIZE);
        double yKey = generator.nextDouble(this.WORLD_SIZE);
        double data = generator.nextDouble();
        VarnupantItem item = new VarnupantItem(xKey, yKey, data);

        tree.set(xKey, yKey, item);
        insItems.add(item);
    }
    private void findRandomRange(QuadTree<VarnupantItem> tree, List<VarnupantItem> insItems) {
        double side = Math.sqrt(this.FIND_PERC / 100.0) * this.WORLD_SIZE;

        double xMin = generator.nextDouble() * (this.WORLD_SIZE - side);
        double xMax = xMin + side;
        double yMin = generator.nextDouble() * (this.WORLD_SIZE - side);
        double yMax = yMin + side;

        Point<VarnupantItem>[] found = tree.searchWithin(xMin, yMin, xMax, yMax);

        if (this.FIND_PERC <= 5) {
            ArrayList<VarnupantItem> expectedData = insItems.stream()
                    .filter(item ->
                            item.xKey > xMin
                            && item.xKey < xMax
                            && item.yKey > yMin
                            && item.yKey < yMax
                    )
                    .collect(Collectors.toCollection(ArrayList::new));
            ArrayList<VarnupantItem> foundData = new ArrayList<>(found.length);
            for (var point : found) {
                foundData.add(point.getValue());
            }

            assertEquals(expectedData.size(), foundData.size());
            assertTrue(foundData.containsAll(expectedData));
        }
    }
}

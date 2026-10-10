package temp;

import dataStructures.common.Comparable;

public class Test implements Comparable {
    private int val;
    public Test(int val) {
        this.val = val;
    }
    public int getVal() { return this.val; }
    @Override
    public int compare(Comparable other) {
        if (this.val < ((Test)other).getVal()) {
            return -1;
        }
        else if (this.val == ((Test)other).getVal()) {
            return 0;
        }
        else if (this.val > ((Test)other).getVal()) {
            return 1;
        }

        return -10;
    }
}

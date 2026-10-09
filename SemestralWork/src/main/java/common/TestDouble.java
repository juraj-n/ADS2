package common;

public class TestDouble implements Comparable {

    private double val;
    public TestDouble(double val) {
        this.val = val;
    }
    public double getVal() { return this.val; }
    @Override
    public int compare(Comparable other) {
        if (this.val < ((TestDouble)other).getVal()) {
            return -1;
        }
        else if (this.val == ((TestDouble)other).getVal()) {
            return 0;
        }
        else if (this.val > ((TestDouble)other).getVal()) {
            return 1;
        }

        return -10;
    }
}
